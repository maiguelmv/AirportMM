/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Airport;
import util.Response;
import java.util.ArrayList;

public class AirportController {
    private ArrayList<Airport> airports = new ArrayList<>();

    public Response registerAirport(Airport airport) {
        if (airport.getAirportId() == null || airport.getAirportId().isEmpty()) {
            return new Response(400,"Airport ID cannot be empty",  null);
        }
        if (airport.getAirportName() == null || airport.getAirportName().isEmpty()) {
            return new Response( 400, "Airport name cannot be empty",null);
        }

        airports.add(airport);
        return new Response(200, "Airport registered successfully", airport);
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }
}
