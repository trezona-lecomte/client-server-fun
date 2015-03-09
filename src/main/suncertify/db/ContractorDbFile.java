package main.suncertify.db;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * An Adapter class for performing the low-level file IO operations to create, read, update and
 * delete Contractor records from the db-2x2.db Contractor Database File.
 * <br/>
 * This class is to be used only by the ContractorDatabase facade, as such it has default
 * visibility.
 * <br/>
 * Should a different database solution be introduced to the system, for example a SQL database,
 * then a new Adapter class should be created to perform the CRUD operations on this db. That
 * class will also need to be accessible by the network and gui layers only via the
 * ContractorDatabase facade, to ensure that the storage layer remains de-coupled.
 *
 * Created by Kieran Trezona-le Comte on 28/01/15.
 */
class ContractorDbFile {

    private static final String DATABASE_NAME = "db-2x2.db";
    private Logger log = Logger.getLogger("suncertify.db");
    private static RandomAccessFile databaseFile = null;
    private static Map<String, Long> recordNumbers = new HashMap<String, Long>();
    private static ReadWriteLock recordNumberLock = new ReentrantReadWriteLock();
    private static String emptyRecordString = null;
    private static String dbPath = null;

    // Ensure that we have a String of the correct length with which to create a StringBuilder
    static {
        emptyRecordString = new String(new byte[Contractor.RECORD_LENGTH]);
    }

    public ContractorDbFile(String suppliedDbFilePath) throws FileNotFoundException, IOException {
        log.entering("ContractorDbFile", "ContractorDbFile", suppliedDbFilePath);
        if (dbPath == null) {
            databaseFile = new RandomAccessFile(suppliedDbFilePath + File.separator + DATABASE_NAME, "rw");
            getContractorList(true);
            dbPath = suppliedDbFilePath;
            log.fine("Database opened and file location table populated");
        }
        else if (dbPath != suppliedDbFilePath) {
            log.warning("Only one database location can be specified. "
                        + "Current location: " + dbPath + " "
                        + "Ignoring specified path: " + suppliedDbFilePath);
        }
        log.exiting("ContractorDbFile", "ContractorDbFile");
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public List<Contractor> getContractors() throws IOException {
        return getContractorList(false);
    }

    /**
     * Finds a Contractor by name.
     * @param name The name of the contractor to locate.
     * @return The Contractor found or null if not found.
     * @throws IOException
     */
    public Contractor getContractor(String name) throws IOException {
        log.entering("ContractorDbFile", "getContractor", name);

        // Ask for the lock to ensure that no other threads are writing to the record map.
        recordNumberLock.readLock().lock();
        try {
            Long positionInFile = recordNumbers.get(name);
            return (positionInFile != null) ? retrieveContractor(positionInFile) : null;
        }
        finally {
            recordNumberLock.readLock().unlock();
            log.exiting("ContractorDbFile", "getContractor");
        }
    }

    /**
     * Retrieves a Contractor using the location of their record in the db file. Instantiates a
     * new Contractor object from this record and returns it to the caller.
     * <br/>
     * This method is a helper method to the getContractors() method,
     * @param positionInFile The location in the file at which their record begins.
     * @return  The Contractor found or null if not found.
     * @throws IOException
     */
    private Contractor retrieveContractor(Long positionInFile)
            throws IOException {
        log.entering("ContractorDbFile", "retrieveContractor", positionInFile);
        final byte[] recordTemplateByteArray = new byte[Contractor.RECORD_LENGTH];
        Contractor contractor;

        // Exclusively locate & read the record from the database file in an atomic operation.
        synchronized (databaseFile) {
            databaseFile.seek(positionInFile);
            databaseFile.readFully(recordTemplateByteArray);
        }
        // We're no longer blocking access to the database file, so other threads can perform
        // operations while we split the record into it's fields and construct a Contractor object.
        /**
         * Helper class to convert the field (byte[]) to a Contractor object based on the
         * fixed-width fields that comprise each record.
         */
        class FieldReader {
            private int recordCursor = 0;

            /**
             * converts the required number of bytes into a String.
             *
             * @param length the length to be converted from current offset.
             * @return the converted String
             * @throws UnsupportedEncodingException if "UTF-8" not known.
             */
            String read(int length) throws UnsupportedEncodingException {
                String field = new String(recordTemplateByteArray, recordCursor, length, "US-ASCII");
                recordCursor += length;
                return field.trim();
            }
        }

        FieldReader fieldReader = new FieldReader();
        String name = fieldReader.read(Contractor.NAME_LENGTH);
        String location = fieldReader.read(Contractor.LOCATION_LENGTH);
        String[] specialties = fieldReader.read(Contractor.SPECIALTIES_LENGTH).split(",");
        Double size = Double.parseDouble(fieldReader.read(Contractor.SIZE_LENGTH));
        Double rate = Double.parseDouble(fieldReader.read(Contractor.RATE_LENGTH));
        int owner = Integer.parseInt(fieldReader.read(Contractor.OWNER_LENGTH));

        if ("DELETED".equals(name)) {
            return null;
        } else
        {
            contractor = new Contractor(name, location, specialties, size, rate, owner);
        }

        log.exiting("ContractorDbFile", "retrieveContractor", contractor);
        return contractor;
    }

    /**
     * Returns all matching contractors for the specified regular expression.
     *
     * @param query search criteria in the form of a regular expression.
     * @return The list of Contractors (possibly empty) that match the query.
     * @throws IOException If the database cannot be accessed.
     * @throws PatternSyntaxException If the syntax of the specified regex is invalid.
     */
    public Collection<Contractor> find(String query) throws IOException, PatternSyntaxException {
        log.entering("ContractorDbFile", "find", query);
        Collection<Contractor> contractors = new ArrayList<Contractor>();
        Pattern pattern = Pattern.compile(query);

        for (Contractor contractor : getContractors()) {
            Matcher matcher = pattern.matcher(contractor.toString());
            if (matcher.find()) {
                contractors.add(contractor);
            }
        }

        log.exiting("ContractorDbFile", "find", contractors);
        return contractors;
    }

    /**
     * Adds a new contractor to the database, via the persistContractor method.
     * @param contractor
     * @return true if the contractor was added successfully, false otherwise.
     */
    public boolean addContractor(Contractor contractor) throws IOException {
        return persistContractor(contractor, true);
    }

    /**
     * Modifies existing information for the specified contractor, except for
     * the contractor name which uniquely identifies the contractor so
     * cannot be changed.
     *
     * @param Contractor The contractor to modify.
     * @return Returns true if the Contractor was successfully modified.
     * @throws IOException If there was a problem accessing the contractor record.
     */
    public boolean modifyContractor(Contractor Contractor) throws IOException {
        return persistContractor(Contractor, false);
    }

    /**
     *
     * @param contractor
     * @param creating
     * @return
     * @throws IOException
     */
    private boolean persistContractor(Contractor contractor, boolean creating) throws IOException {
        log.entering("ContractorDbFile", "persistContractor", contractor);

        Long cursor = 0L;
        recordNumberLock.writeLock().lock();
        try {
            cursor = recordNumbers.get(contractor.getName());

            if (creating == true && cursor == null) {       // If adding the record can't exist
                log.info("creating contractor record " + contractor.getName());
                cursor = databaseFile.length();
                recordNumbers.put(contractor.getName(), cursor);
            }
            else if (creating == false && cursor != null) { // If updating the record must exist
                log.info("updating contractor record " + contractor.getName());
            }
            else {
                return false;
            }
        }
        finally {
            recordNumberLock.writeLock().unlock();
        }

        final StringBuilder out = new StringBuilder(emptyRecordString);

        class FieldWriter {
            int cursor = 0;

            void write(String data, int length) {
                out.replace(cursor, cursor + data.length(), data);
                cursor += length;
            }
        }
        FieldWriter recordWriter = new FieldWriter();

        recordWriter.write(contractor.getName(), Contractor.NAME_LENGTH);
        recordWriter.write(contractor.getLocation(), Contractor.LOCATION_LENGTH);
        recordWriter.write(contractor.getSpecialties().toString(), Contractor.SPECIALTIES_LENGTH);
        recordWriter.write(contractor.getSize().toString(), Contractor.SIZE_LENGTH);
        recordWriter.write(contractor.getRate().toString(), Contractor.RATE_LENGTH);
        recordWriter.write(Integer.toString(contractor.getOwner()), Contractor.OWNER_LENGTH);

        synchronized(databaseFile) {
            databaseFile.seek(cursor);
            databaseFile.write(out.toString().getBytes());
        }

        log.exiting("ContractorDbFile", "persistContractor", true);
        return true;
    }

    /**
     * Retrieves a List of the Contractors in the db file. This method acts as a helper method to
     * the public getContractors() method.
     * <br/>
     * The rationale behind isolating this functionality is that the population of the
     * recordNumber map, which is required by the getContractor method so it can
     * access records based on their name without reading the entire file, needs to be
     * re-populated each time the file is read.
     * <br/>
     * The getContractors method provides the trigger
     * for this to occur, however it is public and thus may be overridden. To prevent any
     * overridden implementation stopping the recordNumber map from being correctly populated,
     * this logic has been isolated to this private method, which getContractors invokes.
     *
     * @param buildRecordNumbers Indicates whether we are re-mapping the record numbers.
     * @return A list of Contractor objects from the database file.
     * @throws IOException
     */
    private List<Contractor> getContractorList(boolean buildRecordNumbers) throws IOException {
        log.entering("ContractorDbFile", "getContractorList", buildRecordNumbers);
        List<Contractor> contractors = new ArrayList<Contractor>();

        if (buildRecordNumbers) {
            // If re-mapping the records to their record numbers then use a re-entrant read/write
            // lock to ensure no other threads can write to the record number map during this time.
            recordNumberLock.writeLock().lock();
        }

        try {
            for (long cursor = 0;
                 cursor < databaseFile.length();
                 // Increment by 1 record per iteration of the loop
                 cursor += Contractor.RECORD_LENGTH)
            {
                Contractor contractor = retrieveContractor(cursor);
                log.fine("Retrieving record at " + cursor);

                if (contractor == null) {
                    log.fine("Found deleted record");
                }
                else {
                    log.fine("Found record: " + contractor.getName());
                    // The lock is held at this stage so it is safe to re-map the record numbers.
                    if (buildRecordNumbers) {
                        recordNumbers.put(contractor.getName(), cursor);
                    }
                }
            }
        }
        finally {
            if (buildRecordNumbers) {
                recordNumberLock.writeLock().unlock();
            }
        }
        log.exiting("ContractorDbFile", "getContractorList");
        return contractors;
    }
}
