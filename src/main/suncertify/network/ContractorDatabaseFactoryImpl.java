package main.suncertify.network;

import main.suncertify.db.ContractorDatabase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by slimfox on 16/02/15.
 */
public class ContractorDatabaseFactoryImpl extends UnicastRemoteObject
        implements ContractorDatabaseFactory {

    private static String dbFilepath = null;

    public ContractorDatabaseFactoryImpl(String dbFilepath) throws RemoteException {
        this.dbFilepath = dbFilepath;
    }

    public ContractorDatabaseRemote getClient() throws RemoteException {
        return new ContractorDatabaseImpl(dbFilepath);
    }

}
