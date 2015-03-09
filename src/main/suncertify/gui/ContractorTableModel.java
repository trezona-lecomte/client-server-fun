package main.suncertify.gui;

import main.suncertify.db.Contractor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Kieran Trezona-le Comte on 9/03/15.
 */
public class ContractorTableModel extends AbstractTableModel {

    private Logger log = Logger.getLogger("suncertify.gui");

    private String[] headerNames = {"Name", "Location", "Specialties",
                                    "Size", "Rate", "Owner"};

    private ArrayList<String[]> contractorRecords = new ArrayList<String[]>(5);

    public int getColumnCount() {
        return this.headerNames.length;
    }

    public int getRowCount() {
        return this.contractorRecords.size();
    }

    public Object getValueAt(int row, int column) {
        String[] rowValues = this.contractorRecords.get(row);
        return rowValues[column];
    }

    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.contractorRecords.get(row);
        rowValues[column] = obj;
    }

    public String getColumnName(int column) {
        return headerNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addContractorRecord(String name, String location, String[] specialties,
                                    Double size, Double rate, int owner) {
        String[] contractor = {name, location, specialties.toString(), size.toString(),
                               rate.toString(), Integer.toString(owner)};
        this.contractorRecords.add(contractor);
    }

    public void addContractorRecord(Contractor contractor) {
        addContractorRecord(contractor.getName(), contractor.getLocation(),
                            contractor.getSpecialties(), contractor.getSize(),
                            contractor.getRate(), contractor.getOwner());
    }



}
