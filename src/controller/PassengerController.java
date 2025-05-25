/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import util.Response;
import model.Passenger;
import java.util.ArrayList;
import java.util.List;

public class PassengerController {

    private List<Passenger> passengers;

    public PassengerController() {
        passengers = new ArrayList<>();
    }

    public Response registerPassenger(Passenger p) {
        // Validar ID: único, >=0, max 15 dígitos
        if (p.getId() < 0 || String.valueOf(p.getId()).length() > 15) {
            return new Response(400, "Invalid ID: must be >=0 and max 15 digits");
        }

        for (Passenger existing : passengers) {
            if (existing.getId() == p.getId()) {
                return new Response(400, "ID already exists");
            }
        }

        // Validar campos no vacíos
        if (p.getFirstname().isEmpty() || p.getLastname().isEmpty() || p.getCountry().isEmpty()) {
            return new Response(400, "Fields cannot be empty");
        }

        // Validar fecha de nacimiento (ya es LocalDate)
        if (p.getBirthDate() == null) {
            return new Response(400, "Invalid birthdate");
        }

        // Validar teléfono
        if (p.getPhone() < 0 || String.valueOf(p.getPhone()).length() > 11) {
            return new Response(400, "Invalid phone number: must be >=0 and max 11 digits");
        }

        // Validar código telefónico
        if (p.getCountryPhoneCode() < 0 || String.valueOf(p.getCountryPhoneCode()).length() > 3) {
            return new Response(400, "Invalid phone code: must be >=0 and max 3 digits");
        }

        // Si todo está bien, agregar a la lista
        passengers.add(p);
        return new Response(200, "Passenger registered successfully");
    }

    public Response updatePassenger(Passenger p) {
        for (Passenger existing : passengers) {
            if (existing.getId() == p.getId()) {
                existing.setFirstname(p.getFirstname());
                existing.setLastname(p.getLastname());
                existing.setBirthDate(p.getBirthDate());
                existing.setCountryPhoneCode(p.getCountryPhoneCode());
                existing.setPhone(p.getPhone());
                existing.setCountry(p.getCountry());
                return new Response(200, "Passenger updated successfully");
            }
        }
        return new Response(404, "Passenger not found");
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
}
