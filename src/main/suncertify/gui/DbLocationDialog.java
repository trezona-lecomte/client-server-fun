package main.suncertify.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by slimfox on 9/03/15.
 */
public class DbLocationDialog extends WindowAdapter
        implements ActionListener, Observer {

    private static final String TITLE = "Please enter the location of the Database";
    private static final String CONNECT = "Connect";
    private static final String EXIT = "Exit";

    /*
     * Some values for possible port ranges so we can determine what sort of port the user has
     * specified.
     */
    private static final int LOWEST_PORT = 0;
    private static final int HIGHEST_PORT = 65535;
    private static final int SYSTEM_PORT_BOUNDARY = 1024;
    private static final int IANA_PORT_BOUNDARY = 49151;

    private JOptionPane optionsPane = null;
    private JDialog dialog = null;
    private JButton connectButton = new JButton(CONNECT);
    private JButton exitButton = new JButton(EXIT);

    private boolean validDb = false;
    private boolean validPort = false;
    private boolean validCnx = false;

    private String location = null;
    private String port = null;



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
