/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.AirportModel;
import model.Airport;
import util.Observer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AirportTableView implements Observer {

    private JTable table;
    private AirportModel model;

    public AirportTableView(JTable table, AirportModel model) {
        this.table = table;
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update() {
        refreshTable();
    }

    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for (Airport a : model.getAirports()) {
            tableModel.addRow(new Object[]{
                a.getAirportId(),
                a.getAirportName(),
                a.getAirportCity(),
                a.getAirportCountry()
            });
        }
    }
}
