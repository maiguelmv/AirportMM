/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Flight;
import model.Passenger;
import util.Response;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import model.Airport;
import model.Plane;

public class FlightController {
    
    private ArrayList<Flight> flights = new ArrayList<>();
    private AirportController airportController;
    private PlaneController planeController;
    
    public FlightController(AirportController airportController, PlaneController planeController) {
        this.airportController = airportController;
        this.planeController = planeController;
    }



    public Response registerFlight(Flight flight) {
        
        if (flight.getId() == null || flight.getId().isEmpty() || flight.getPlane() == null
                || flight.getDepartureLocation() == null || flight.getArrivalLocation() == null
                || flight.getDepartureDate() == null) {
            return new Response(400, "Missing required flight data", null);
        }
        if (!flight.getId().matches("^[A-Z]{3}\\d{3}$")) {
            return new Response(400, "Invalid ID: Must be 3 letters + 3 digits (e.g., ABC123)");
        }

        
        for (Flight existing : flights) {
            if (existing.getId().equals(flight.getId())) {
                return new Response(400, "Flight ID already exists", null);
            }
        }
        if (flight.getPlane().getMaxCapacity() <= 0) {
            return new Response(400, "Plane max capacity must be greater than 0", null);
        }
        if (flight.getHoursDurationArrival() <= 0 && flight.getMinutesDurationArrival() <= 0) {
            return new Response(400, "Arrival duration must be greater than 00:00", null);
        }
     
        if (flight.getScaleLocation() != null) {
            if (flight.getHoursDurationScale() <= 0 && flight.getMinutesDurationScale() <= 0) {
                return new Response(400, "Scale duration must be greater than 00:00", null);
            }
        }
        
        flights.add(new Flight(flight));
        return new Response(200, "Flight registered successfully", flight);
        
    }
    
    public Response addPassengerToFlight(Flight flight, Passenger passenger) {
        if (flight == null) {
            return new Response(404, "Flight not found", null);
        }
        if (passenger == null) {
            return new Response(404, "Passenger not found", null);
        }

        if (flight.getPassengers().size() >= flight.getPlane().getMaxCapacity()) {
            return new Response(400, "Flight is full", null);
        }

        flight.addPassenger(passenger);
        passenger.addFlight(flight);
        return new Response(200, "Passenger added to flight successfully", null);
    }
    
    public Response delayFlight(Flight flight, int hours, int minutes) {
        if (flight == null) {
            return new Response(404, "Flight not found", null);
        }
        if (hours < 0 || minutes < 0 || (hours == 0 && minutes == 0)) {
            return new Response(400, "Delay time must be greater than 00:00", null);
        }

        flight.delayFlight(hours, minutes);
        return new Response(200, "Flight delayed successfully", null);
    }



 
    public List<Flight> getFlights() {
        List<Flight> copyFlight = new ArrayList<>();
        for (Flight flight : flights) {
            copyFlight.add(new Flight(flight)); // Usando el constructor copia
        }
        return copyFlight;
    }
    
    public void loadFlightsFromJSON(String filePath) {
    try {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray flightsArray = new JSONArray(content);

        for (int i = 0; i < flightsArray.length(); i++) {
            JSONObject obj = flightsArray.getJSONObject(i);

            String id = obj.getString("id");
            String planeId = obj.getString("planeId");
            String departureId = obj.getString("departureLocationId");
            String arrivalId = obj.getString("arrivalLocationId");
            String scaleId = obj.getString("scaleLocationId");
            String departureDateStr = obj.getString("departureDate");
            
            

            int hoursArrival = obj.getInt("hoursDurationsArrival");
            int minutesArrival = obj.getInt("minutesDurationsArrival");
            int hoursScale = obj.getInt("hoursDurationsScale");
            int minutesScale = obj.getInt("minutesDurationsScale");


            Plane plane = null;
            for (Plane p : planeController.getPlanes()) {
                if (p.getId().equals(planeId)) {
                    plane = p;
                }
            }

            Airport departure = null, arrival = null, scale = null;
            for (Airport airport : airportController.getAirports()) {
                if (airport.getAirportId().equals(departureId)) {
                    departure = airport;
                }
                if (airport.getAirportId().equals(arrivalId)) {
                    arrival = airport;
                }
                if (airport.getAirportId().equals(scaleId)) {
                    scale = airport;
                }
            }

            LocalDateTime departureDate = LocalDateTime.parse(departureDateStr);

            Flight flight = new Flight(id, plane, departure, scale, arrival, departureDate, hoursArrival, minutesArrival, hoursScale, minutesScale);
            flights.add(flight);
        }

        System.out.println("Flights loaded from JSON.");
    } catch (Exception e) {
        System.out.println("Error loading flights from JSON: " + e.getMessage());
    }
}

}

