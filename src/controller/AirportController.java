/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Airport;
import model.AirportModel;
import util.Response;

public class AirportController {

    private final AirportModel model;

    public AirportController(AirportModel model) {
        this.model = model;
    }

    public Response registerAirport(Airport airport) {
        if (airport.getAirportId() == null || airport.getAirportId().isEmpty()) {
            return new Response(400, "Airport ID cannot be empty", null);
        }

        if (!airport.getAirportId().matches("^[A-Z]{3}$")) {
            return new Response(400, "Invalid Airport ID format: must be 3 uppercase letters (e.g., BOG)", null);
        }

        for (Airport existing : model.getAirports()) {
            if (existing.getAirportId().equals(airport.getAirportId())) {
                return new Response(400, "Airport ID already exists", null);
            }
        }

        if (airport.getAirportName().isEmpty() || airport.getAirportCity().isEmpty() || airport.getAirportCountry().isEmpty()) {
            return new Response(400, "Fields cannot be empty", null);
        }

        if (airport.getAirportLatitude() < -90 || airport.getAirportLatitude() > 90) {
            return new Response(400, "Invalid latitude: must be between -90 and 90", null);
        }

        if (airport.getAirportLongitude() < -180 || airport.getAirportLongitude() > 180) {
            return new Response(400, "Invalid longitude: must be between -180 and 180", null);
        }

        String latStr = String.valueOf(airport.getAirportLatitude());
        String longStr = String.valueOf(airport.getAirportLongitude());

        if (latStr.contains(".") && latStr.split("\\.")[1].length() > 4) {
            return new Response(400, "Latitude must have a maximum of 4 decimal places", null);
        }
        if (longStr.contains(".") && longStr.split("\\.")[1].length() > 4) {
            return new Response(400, "Longitude must have a maximum of 4 decimal places", null);
        }

        model.addAirport(airport);
        model.notifyObservers();
        return new Response(200, "Airport registered successfully", airport);
    }

    public AirportModel getAirportModel() {
        return model;
    }

    public void loadAirportsFromJSON(String filePath) {
        try {
            model.loadAirportsFromJSON(filePath);
            model.notifyObservers();
        } catch (Exception e) {
            System.out.println("❌ Error loading airports: " + e.getMessage());
        }
    }
}
