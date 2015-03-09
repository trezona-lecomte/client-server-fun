package main.suncertify.gui;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Created by slimfox on 9/03/15.
 */
public class ApplicationWindow extends JFrame {

    private GuiController guiController;
    private JFrame mainFrame;
    private JTable mainTable = new JTable();
    private JTextField nameSearchField = new JTextField(32);
    private JTextField locationSearchField = new JTextField(64);
    private ContractorTableModel contractorTable;
    private String previousSearchString = "";

    private Logger log = Logger.getLogger("suncertify.gui");

    public ApplicationWindow(String[] args) {
        super("All-About Improvement Ltd");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        ApplicationMode applicationMode;
        if (args.length == 0) {
            applicationMode = ApplicationMode.NETWORK;
        }
        else if (args[0].equalsIgnoreCase("network")) {
            applicationMode = ApplicationMode.NETWORK;
        }
        else {
            applicationMode = ApplicationMode.LOCAL;
        }
    }

}
