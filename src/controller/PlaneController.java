/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Plane;
import util.Response;
import java.util.ArrayList;
import java.util.List;
import model.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PlaneController {
    private ArrayList<Plane> planes = new ArrayList<>();

    public Response registerPlane(Plane plane) {

        if (plane.getId() == null || plane.getId().isEmpty()) {
            return new Response(400,"Plane ID cannot be empty", null);
        }
        if (!plane.getId().matches("^[A-Z]{2}[0-9]{5}$")) {
            return new Response(400, "Invalid Plane ID format: must be 2 uppercase letters + 5 digits (e.g., AB12345)", null);
        }

        if (plane.getMaxCapacity() <= 0) {
            return new Response(400,"Capacity must be positive",  null);
        }
        if (plane.getBrand().isEmpty() || plane.getModel().isEmpty() || plane.getAirline().isEmpty()) {
            return new Response(400, "Fields cannot be empty", null);
        }
        for (Plane existing : planes) {
            if (existing.getId().equals(plane.getId())) {
                return new Response(400, "Plane ID already exists", null);
            }
        }

        planes.add(new Plane(plane)); 
        return new Response(200, "Plane registered successfully", plane);
    }

    public List<Plane> getPlanes() {
        List<Plane> copyPlane = new ArrayList<>();
        for (Plane plane : planes) {
            copyPlane.add(new Plane(plane)); // Usando el constructor copia
        }
        return copyPlane;
    }

    public void loadPlanesFromJSON(String filePath) {
    try {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray planesArray = new JSONArray(content);

        for (int i = 0; i < planesArray.length(); i++) {
            JSONObject obj = planesArray.getJSONObject(i);

            String id = obj.getString("id");
            String brand = obj.getString("brand");
            String model = obj.getString("model");
            int maxCapacity = obj.getInt("maxCapacity");
            String airline = obj.getString("airline");

            Plane plane = new Plane(id, brand, model, maxCapacity, airline);
            planes.add(plane);
        }

        System.out.println("Planes loaded from JSON.");
    } catch (Exception e) {
        System.out.println("Error loading planes from JSON: " + e.getMessage());
    }
}
    
}
