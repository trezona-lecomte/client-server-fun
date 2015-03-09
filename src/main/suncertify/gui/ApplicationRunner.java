package main.suncertify.gui;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Created by Kieran Trezona-le Comte on 9/03/15.
 */
public class ApplicationRunner {

    private Logger log = Logger.getLogger("suncertify.gui");

    public static void main(String[] args) {
        ApplicationRunner app = new ApplicationRunner(args);
    }

    public ApplicationRunner(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException uex) {
            log.warning("Unsupported look and feel specified");
        }
        catch (ClassNotFoundException cex) {
            log.warning("Look and feel could not be located");
        }
        catch (InstantiationException iex) {
            log.warning("Look and feel could not be instantiated");
        }
        catch (IllegalAccessException iaex) {
            log.warning("Look and feel cannot be used on this platform");
        }

        if (args.length == 0
            || "network".equalsIgnoreCase(args[0])
            || "local".equalsIgnoreCase(args[0])) {
            new ApplicationWindow(args);
        }
        else {
            log.info("Invalid argument passed during start up: " + args[0]);
            System.err.println("Command line options may be one of: ");
            System.err.println("\"network\" - starts the networked client");
            System.err.println("\"local\"   - starts the local (non-networked) client");
            System.err.println("\"\"        - (no argument given): starts networked client");
        }
    }

    public static void handleException(String msg) {
        JOptionPane error = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE,
                                                       JOptionPane.DEFAULT_OPTION);
        JDialog errorDialog = error.createDialog(null, "Error");

        // Centre window on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)((d.getWidth() - errorDialog.getWidth()) / 2);
        int y = (int)((d.getHeight() -  errorDialog.getHeight()) / 2);
        errorDialog.setLocation(x, y);

        errorDialog.setVisible(true);
    }

}
