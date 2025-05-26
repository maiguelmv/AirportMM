/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import util.Response;
import model.Passenger;
import model.PassengerModel;
import java.time.LocalDate;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PassengerController {

    private PassengerModel model;

    public PassengerController(PassengerModel model) {
        this.model = model;
    }

    public PassengerModel getPassengerModel() {
        return model;
    }

    public Response registerPassenger(Passenger p) {
        if (p.getId() < 0 || String.valueOf(p.getId()).length() > 15) {
            return new Response(400, "Invalid ID: must be >=0 and max 15 digits");
        }

        for (Passenger existing : model.getPassengers()) {
            if (existing.getId() == p.getId()) {
                return new Response(400, "ID already exists");
            }
        }

        if (!p.getFirstname().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return new Response(400, "Firstname must contain only letters");
        }
        if (!p.getLastname().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return new Response(400, "Lastname must contain only letters");
        }

        if (!p.getCountry().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return new Response(400, "Country must contain only letters and spaces");
        }

        if (p.getFirstname().isEmpty() || p.getLastname().isEmpty() || p.getCountry().isEmpty()) {
            return new Response(400, "Fields cannot be empty");
        }

        if (p.getBirthDate() == null || p.getBirthDate().getYear() < 1920 || p.getBirthDate().getYear() > LocalDate.now().getYear()) {
            return new Response(400, "Invalid birthdate");
        }

        if (p.getPhone() < 0 || String.valueOf(p.getPhone()).length() > 11) {
            return new Response(400, "Invalid phone number: must be >=0 and max 11 digits");
        }

        if (p.getCountryPhoneCode() < 0 || String.valueOf(p.getCountryPhoneCode()).length() > 3) {
            return new Response(400, "Invalid phone code: must be >=0 and max 3 digits");
        }

        model.addPassenger(p);
        return new Response(200, "Passenger registered successfully");
    }

    public Response updatePassenger(Passenger p) {
        model.updatePassenger(p);
        return new Response(200, "Passenger updated successfully");
    }

    public List<Passenger> getPassengers() {
        return model.getPassengers();
    }

    public void loadPassengersFromJSON(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray passengersArray = new JSONArray(content);

            for (int i = 0; i < passengersArray.length(); i++) {
                JSONObject obj = passengersArray.getJSONObject(i);

                long id = obj.getLong("id");
                String firstname = obj.getString("firstname");
                String lastname = obj.getString("lastname");
                int year = obj.getInt("birthYear");
                int month = obj.getInt("birthMonth");
                int day = obj.getInt("birthDay");
                LocalDate birthDate = LocalDate.of(year, month, day);
                int phoneCode = obj.getInt("countryPhoneCode");
                long phone = obj.getLong("phone");
                String country = obj.getString("country");

                Passenger passenger = new Passenger(id, firstname, lastname, birthDate, phoneCode, phone, country);
                model.addPassenger(passenger);
            }

            model.notifyObservers();
            System.out.println("Passengers loaded from JSON.");
        } catch (Exception e) {
            System.out.println("Error loading passengers from JSON: " + e.getMessage());
        }
    }
}
