/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.Observer;
import util.Subject;
import java.util.ArrayList;
import java.util.List;

public class PassengerModel implements Subject {

    private List<Passenger> passengers = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addPassenger(Passenger p) {
        passengers.add(new Passenger(p)); // Defensive copy
        notifyObservers();
    }

    public void updatePassenger(Passenger p) {
        for (Passenger existing : passengers) {
            if (existing.getId() == p.getId()) {
                existing.setFirstname(p.getFirstname());
                existing.setLastname(p.getLastname());
                existing.setBirthDate(p.getBirthDate());
                existing.setCountryPhoneCode(p.getCountryPhoneCode());
                existing.setPhone(p.getPhone());
                existing.setCountry(p.getCountry());
            }
        }
        notifyObservers();
    }

    public List<Passenger> getPassengers() {
        List<Passenger> copy = new ArrayList<>();
        for (Passenger p : passengers) {
            copy.add(new Passenger(p));
        }
        return copy;
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
