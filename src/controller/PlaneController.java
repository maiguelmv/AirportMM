/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Plane;
import util.Response;
import java.util.ArrayList;

public class PlaneController {
    private ArrayList<Plane> planes = new ArrayList<>();

    public Response registerPlane(Plane plane) {
        // Validaciones básicas
        if (plane.getId() == null || plane.getId().isEmpty()) {
            return new Response(400,"Plane ID cannot be empty", null);
        }
        if (plane.getMaxCapacity() <= 0) {
            return new Response(400,"Invalid max capacity",  null);
        }

        planes.add(plane);
        return new Response(200, "Plane registered successfully", plane);
    }

    public ArrayList<Plane> getPlanes() {
        return planes;
    }
}
