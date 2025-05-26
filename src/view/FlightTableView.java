/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.FlightModel;
import model.Flight;
import util.Observer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FlightTableView implements Observer {

    private JTable table;
    private FlightModel model;

    public FlightTableView(JTable table, FlightModel model) {
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
        for (Flight f : model.getFlights()) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getDepartureLocation().getAirportId(),
                f.getArrivalLocation().getAirportId(),
                f.getScaleLocation() != null ? f.getScaleLocation().getAirportId() : "-",
                f.getDepartureDate(),
                f.calculateArrivalDate(),
                f.getPlane().getId(),
                f.getNumPassengers()
            });
        }
    }
}
