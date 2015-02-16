package main.suncertify.network;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * RegContractorDatabase launches the RMI registry on the client machine and registers the
 * ContractorDatabase object with the RMI naming service.
 *
 * Created by slimfox on 16/02/15.
 */
public class RegContractorDatabase {

    /**
     * This private constructor is to prevent the creation of unnecessary instances of the
     * RegContractorDatabase class - this is a utility class with only static methods.
     */
    private RegContractorDatabase() { }

    public static void register() throws RemoteException {

        register();

    }

    public static void register(String dbLocation, int rmiPort) throws RemoteException {
        Registry registry = java.rmi.registry.LocateRegistry.createRegistry(rmiPort);

        // Create a ContractorDatabase instance on a random port number, then register the
        // service name & port number in the RMI registry.
        registry.rebind("ContractorMediator", new ContractorDatabaseFactoryImpl(dbLocation));
    }

    /**
     * Entry point for starting the RMI Server manually, to be used for testing and development.
     * @param args
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        register();
    }

}
