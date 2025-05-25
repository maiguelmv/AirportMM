/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import util.Response;
import model.Passenger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class PassengerController {

    private List<Passenger> passengers;

    public PassengerController() {
        passengers = new ArrayList<>();
    }

    public Response registerPassenger(Passenger p) {

        if (p.getId() < 0 || String.valueOf(p.getId()).length() > 15) {
            return new Response(400, "Invalid ID: must be >=0 and max 15 digits");
        }

        for (Passenger existing : passengers) {
            if (existing.getId() == p.getId()) {
                return new Response(400, "ID already exists");
            }
        }


        if (p.getFirstname().isEmpty() || p.getLastname().isEmpty() || p.getCountry().isEmpty()) {
            return new Response(400, "Fields cannot be empty");
        }


        if (p.getBirthDate() == null || p.getBirthDate().getYear() < 1900 || p.getBirthDate().getYear() > LocalDate.now().getYear()) {
            return new Response(400, "Invalid birthdate");
        }


        if (p.getPhone() < 0 || String.valueOf(p.getPhone()).length() > 11) {
            return new Response(400, "Invalid phone number: must be >=0 and max 11 digits");
        }


        if (p.getCountryPhoneCode() < 0 || String.valueOf(p.getCountryPhoneCode()).length() > 3) {
            return new Response(400, "Invalid phone code: must be >=0 and max 3 digits");
        }

        passengers.add(new Passenger(p));
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
        List<Passenger> copy = new ArrayList<>();
        for (Passenger p : passengers) {
            copy.add(new Passenger(p)); // Usando el constructor copia
        }
        return copy;
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
            passengers.add(passenger);
        }

        System.out.println("Passengers loaded from JSON.");
    } catch (Exception e) {
        System.out.println("Error loading passengers from JSON: " + e.getMessage());
    }
}

}
