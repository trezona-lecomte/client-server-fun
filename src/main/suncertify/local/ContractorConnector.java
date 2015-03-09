package main.suncertify.local;

import main.suncertify.db.DB;
import main.suncertify.db.ContractorDatabase;
import main.suncertify.db.DatabaseAccessException;

import java.io.IOException;

/**
 * Created by Kieran Trezona-le Comte on 9/03/15.
 */
public class ContractorConnector {

    private ContractorConnector() {
    }

    public static DB getLocalDb(String dbFilepath)
            throws IOException, DatabaseAccessException {
        return new ContractorDatabase(dbFilepath);

    }

}