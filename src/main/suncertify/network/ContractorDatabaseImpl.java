package main.suncertify.network;

import main.suncertify.db.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/** A ContractorDatabaseImpl object is an RMI implementation of the ContractorDatabaseRemote
 * interface, which is used as a wrapper for the database.
 *
 * Created by slimfox on 16/02/15.
 */
public class ContractorDatabaseImpl extends UnicastRemoteObject
        implements ContractorDatabaseRemote {

    private static Logger log = Logger.getLogger("main.suncertify.network");

    /**
     * A handle to the database.
     */
    private DB db = null;

    /**
     *
     * @param dbLocation
     * @throws RemoteException
     */
    public ContractorDatabaseImpl(String dbLocation) throws RemoteException {
        try {
            db = new ContractorDatabase(dbLocation);
        }
        catch (DatabaseAccessException e) {
            throw new RemoteException(e.getMessage(), e);
        }
        catch (FileNotFoundException e) {
            throw new RemoteException(e.getMessage(), e);
        }
        catch (IOException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param recordNumber
     * @return
     * @throws RemoteException
     * @throws RecordNotFoundException
     * @throws IOException
     */
    public String[] read(int recordNumber) throws RecordNotFoundException {
        return db.read(recordNumber);
    }


    /**
     * Modifies the fields of a record. The new value for field n
     * appears in data[n]. Throws SecurityException
     * if the record is locked with a cookie other than lockCookie.
     * @param recNo The record number.
     * @param data The updated record.
     * @param lockCookie The cookie this record is locked with.
     */
    public void update(int recNo, String[] data, long lockCookie)
            throws RecordNotFoundException, SecurityException {

    }

    /**
     * Deletes a record, making the record number and associated disk
     * storage available for reuse.
     * Throws SecurityException if the record is locked with a cookie
     * other than lockCookie.
     * @param recNo The record number.
     * @param lockCookie The cookie this record is locked with.
     */
    public void delete(int recNo, long lockCookie)
            throws RecordNotFoundException, SecurityException {

    }

    /**
     * Returns an array of record numbers that match the specified
     * criteria. Field n in the database file is described by
     * criteria[n]. A null value in criteria[n] matches any field
     * value. A non-null value in crFiteria[n] matches any field
     * value that begins with criteria[n]. (For example, "Fred"
     * matches "Fred" or "Freddy".)
     * @param criteria The criteria against which to match records.
     */
    public int[] find(String[] criteria) {
        int[] recordNumbers = {};
        return recordNumbers;
    }

    /**
     * Creates a new record in the database (possibly reusing a
     * deleted entry). Inserts the given data, and returns the record
     * number of the new record.
     * @param data The data of the record to create.
     */
    public int create(String[] data) throws DuplicateKeyException {
        int recordNumber = 0;
        return recordNumber;
    }

    /**
     * Locks a record so that it can only be updated or deleted by this client.
     * Returned value is a cookie that must be used when the record is unlocked,
     * updated, or deleted. If the specified record is already locked by a different
     * client, the current thread gives up the CPU and consumes no CPU cycles until
     * the record is unlocked.
     * @param recNo The record number.
     */
    public long lock(int recNo) throws RecordNotFoundException {
        long cookie = 0;
        return cookie;
    }

    /**
     * Releases the lock on a record. Cookie must be the cookie
     * returned when the record was locked; otherwise throws SecurityException.
     * @param recNo The record number.
     * @param cookie The cookie of the locked cookie.
     */
    public void unlock(int recNo, long cookie) throws RecordNotFoundException, SecurityException {

    }

}
