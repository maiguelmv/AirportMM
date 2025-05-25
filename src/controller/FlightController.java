/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Flight;
import model.Passenger;
import util.Response;
import java.util.ArrayList;

public class FlightController {
    private ArrayList<Flight> flights = new ArrayList<>();

    public Response registerFlight(Flight flight) {
        if (flight.getId() == null || flight.getId().isEmpty()) {
            return new Response(400, "Flight ID cannot be empty", null);
        }

        flights.add(flight);
        return new Response(200, "Flight registered successfully", flight);
    }

    public Response addPassengerToFlight(Flight flight, Passenger passenger) {
        flight.addPassenger(passenger);
        passenger.addFlight(flight);
        return new Response(200, "Passenger added to flight", null);
    }

    public Response delayFlight(Flight flight, int hours, int minutes) {
        flight.delay(hours, minutes);
        return new Response(200, "Flight delayed successfully", null);
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }
}

