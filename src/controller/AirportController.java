/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Airport;
import util.Response;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;

public class AirportController {
    private ArrayList<Airport> airports = new ArrayList<>();
    
   

    public Response registerAirport(Airport airport) {
        if (airport.getAirportId() == null || airport.getAirportId().isEmpty()) {
            return new Response(400,"Airport ID cannot be empty",  null);
        }
        if (!airport.getAirportId().matches("[A-Z]{3}")) {
            return new Response(400, "Airport ID must be exactly 3 uppercase letters (A-Z).");
        }


        for (Airport existing : airports) {
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


        if (String.valueOf(airport.getAirportLatitude()).split("\\.").length > 1
                && String.valueOf(airport.getAirportLatitude()).split("\\.")[1].length() > 4) {
            return new Response(400, "Latitude must have max 4 decimal places.");
        }


        if (airport.getAirportLongitude() < -180 || airport.getAirportLongitude() > 180) {
            return new Response(400, "Invalid longitude: must be between -180 and 180", null);
        }


        if (String.valueOf(airport.getAirportLongitude()).split("\\.").length > 1
                && String.valueOf(airport.getAirportLongitude()).split("\\.")[1].length() > 4) {
            return new Response(400, "Longitude must have max 4 decimal places.");
        }


        airports.add(new Airport(airport)); 
        return new Response(200, "Airport registered successfully", airport);
    }
    public List<Airport> getAirports() {
        List<Airport> copyAirport = new ArrayList<>();
        for (Airport airport : airports) {
            copyAirport.add(new Airport(airport)); // Usando el constructor copia
        }
        return copyAirport;
    }
    
    public void loadAirportsFromJSON(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            JSONTokener tokener = new JSONTokener(fis);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String id = obj.getString("id");
                String name = obj.getString("name");
                String city = obj.getString("city");
                String country = obj.getString("country");
                double latitude = obj.getDouble("latitude");
                double longitude = obj.getDouble("longitude");

                Airport airport = new Airport(id, name, city, country, latitude, longitude);
                airports.add(airport);
            }

            System.out.println("✅ Airports loaded from JSON!");

        } catch (Exception e) {
            System.out.println("❌ Error loading airports: " + e.getMessage());
        }
    }

   
}

