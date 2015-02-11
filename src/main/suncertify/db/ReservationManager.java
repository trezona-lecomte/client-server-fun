package main.suncertify.db;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Kieran Trezona-le Comte on 28/01/15.
 *
 * ReservationManager handles the logical locking of Contractor records, ensuring that a record
 * cannot have conflicting updates made should two CSRs attempt to access the same contractor
 * concurrently.
 */
class ReservationManager {

    private static final int TIMEOUT_DURATION = 5000;

    private static Map<String, ContractorDatabase> reservations
            = new HashMap<String, ContractorDatabase>();

    private static Lock lock = new ReentrantLock();

    /**
     * Used to notify threads waiting for the lock that it is available.
     */
    private static Condition lockReleased = lock.newCondition();

    private Logger log = Logger.getLogger("suncertify.db");

    boolean reverseContractor(String contractorName, ContractorDatabase database)
            throws InterruptedException
    {
        log.entering("ReservationManager", "reserverContractor",
                     new Object[]{contractorName, database});
        lock.lock();
        try {
            long endOfTimeout = System.currentTimeMillis() + TIMEOUT_DURATION;
            while (reservations.containsKey(contractorName)) {
                long timeLeft = endOfTimeout - System.currentTimeMillis();
                if (!lockReleased.await(timeLeft, TimeUnit.MILLISECONDS)) {
                    log.fine(database + " timeout period reached, failed to obtain lock for: "
                            + contractorName);
                    return false;
                }
            }
            reservations.put(contractorName, database);
            log.fine(database + " obtained lock for: " + contractorName);
            log.fine("The number of locked records is: " + reservations.size());
            log.exiting("ReservationManager", "reserverContractor", true);
            return true;
        }
        finally {
            lock.unlock();  //Ensure that lock is released if exception is thrown within try block
        }
    }

    void releaseContractor(String contractorName, ContractorDatabase database) {
        log.entering("ReservationManager", "releaseContractor",
                new Object[]{contractorName, database});
        lock.lock();
        if (reservations.get(contractorName) == database) {
            reservations.remove(contractorName);
            log.fine(database + " released lock on " + contractorName);
            lockReleased.signal();
        }
        else {
            log.warning(database + " can't release lock on " + contractorName + ": not the owner");
        }
        lock.unlock();
        log.exiting("ReservationManager", "releaseContractor");
    }

}
