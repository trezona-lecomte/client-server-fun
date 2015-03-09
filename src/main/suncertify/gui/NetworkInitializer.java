package main.suncertify.gui;

import main.suncertify.network.RegContractorDatabase;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by slimfox on 9/03/15.
 */
public class NetworkInitializer {

    /**
     * An error code to be passed back to the operating system itself if the
     * port number provided is invalid.
     */
    public static final int ERR_CODE_INVALID_PORT_NUMBER = -1;
    /**
     * An error code to be passed back to the operating system itself if the
     * registry cannot be started.
     */
    private static final int ERR_CODE_CANT_START_REGISTRY = -1;

    /*
     * Strings that appear in log messages and in the status bar.
     */
    private static final String CANT_START_REGISTRY
            = "Unable to start the RMI registry, which is required in order to "
            + "run the server.\nPerhaps the port is already in use?";
    private static final String INVALID_PORT_NUMBER = "Invalid port number ";
    private static final String SERVER_STARTING = "Starting RMI Registry and Registring Server";
    private static final String SERVER_RUNNING = "Server running.";

    private Logger log = Logger.getLogger("sampleproject.gui");

    /**
     * Creates a new instance of NetworkStarterRmi.
     *
     * @param dbLocation the location of the data file on a local hard drive.
     * @param status a label on the server GUI we can update with our status.
     */
    public NetworkInitializer(String dbLocation, JLabel status) {
        try {
            int port = java.rmi.registry.Registry.REGISTRY_PORT;
            log.info("Starting RMI registry on port " + port);
            status.setText(SERVER_STARTING);
            RegContractorDatabase.register(dbLocation, port);

            log.info("Server started.");
            status.setText(SERVER_RUNNING);

        } catch (NumberFormatException e) {
            // this should never happen, since we are taking pains to ensure
            // that only numbers can be entered into the text field. But
            // just in case ...
            log.log(Level.SEVERE, INVALID_PORT_NUMBER, e);
            System.exit(ERR_CODE_INVALID_PORT_NUMBER);
        } catch (RemoteException e) {
            // We cannot start the registry. Since we have not defined our
            // classpath, we cannot _easily_ attach to an already running
            // registry - just notify the user, and exit.
            log.log(Level.SEVERE, CANT_START_REGISTRY, e);
            System.exit(ERR_CODE_CANT_START_REGISTRY);
        }
    }
}
