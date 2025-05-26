/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Flight;
import model.Passenger;
import model.Airport;
import model.Plane;
import model.FlightModel;
import util.Response;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FlightController {

    private FlightModel model;  // 💡 Ya no se inicializa aquí
    private AirportController airportController;
    private PlaneController planeController;

    public FlightController(AirportController airportController, PlaneController planeController, FlightModel flightModel) {
        this.airportController = airportController;
        this.planeController = planeController;
        this.model = flightModel;
    }


    public FlightModel getFlightModel() {  // Getter para el modelo (vista)
        return model;
    }

    public Response registerFlight(Flight flight) {

        if (flight.getId() == null || flight.getId().isEmpty() || flight.getPlane() == null
                || flight.getDepartureLocation() == null || flight.getArrivalLocation() == null
                || flight.getDepartureDate() == null) {
            return new Response(400, "Missing required flight data", null);
        }

        if (!flight.getId().matches("^[A-Z]{3}[0-9]{3}$")) {
            return new Response(400, "Invalid Flight ID format: must be 3 uppercase letters + 3 digits (e.g., BOG123)", null);
        }

        for (Flight existing : model.getFlights()) {
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

        model.addFlight(flight);
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
        model.notifyObservers();
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
        model.notifyObservers();
        return new Response(200, "Flight delayed successfully", null);
    }

    public List<Flight> getFlights() {
        return model.getFlights();
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
                String scaleId = obj.optString("scaleLocationId", null);
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
                for (Airport airport : airportController.getAirportModel().getAirports()) {
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
                model.addFlight(flight);
            }

            System.out.println("Flights loaded from JSON.");
        } catch (Exception e) {
            System.out.println("Error loading flights from JSON: " + e.getMessage());
        }
    }
}
