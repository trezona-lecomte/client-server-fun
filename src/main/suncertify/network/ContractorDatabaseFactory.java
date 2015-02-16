package main.suncertify.network;

import main.suncertify.db.ContractorDatabase;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by slimfox on 16/02/15.
 */
public interface ContractorDatabaseFactory extends Remote {

    public ContractorDatabaseRemote getClient() throws RemoteException;

}
