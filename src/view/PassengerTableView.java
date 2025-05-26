/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.PassengerModel;
import model.Passenger;
import util.Observer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PassengerTableView implements Observer {

    private JTable table;
    private PassengerModel model;

    public PassengerTableView(JTable table, PassengerModel model) {
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

        for (Passenger p : model.getPassengers()) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getFullname(),
                p.getBirthDate(),
                p.calculateAge(),
                p.generateFullPhone(),
                p.getCountry(),
                p.getNumFlights()
            });
        }
    }
}
