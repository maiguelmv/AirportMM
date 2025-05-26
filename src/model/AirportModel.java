/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.Observer;
import util.Subject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;

public class AirportModel implements Subject {

    private List<Airport> airports = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addAirport(Airport airport) {
        airports.add(new Airport(airport));  // Defensive copy
        notifyObservers();
    }

    public List<Airport> getAirports() {
        List<Airport> copy = new ArrayList<>();
        for (Airport a : airports) {
            copy.add(new Airport(a));
        }
        return copy;
    }

    public void loadAirportsFromJSON(String filePath) {
        try ( FileInputStream fis = new FileInputStream(filePath)) {
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

            notifyObservers();
            System.out.println("Airports loaded from JSON!");
        } catch (Exception e) {
            System.out.println(" Error loading airports: " + e.getMessage());
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
