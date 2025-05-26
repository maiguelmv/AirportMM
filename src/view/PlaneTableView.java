/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.PlaneModel;
import model.Plane;
import util.Observer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PlaneTableView implements Observer {

    private JTable table;
    private PlaneModel model;

    public PlaneTableView(JTable table, PlaneModel model) {
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
        for (Plane p : model.getPlanes()) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getBrand(),
                p.getModel(),
                p.getMaxCapacity(),
                p.getAirline(),
                p.getNumFlights()
            });
        }
    }
}
