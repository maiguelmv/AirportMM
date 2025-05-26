
package model;

import util.Observer;
import util.Subject;
import java.util.ArrayList;
import java.util.List;

public class FlightModel implements Subject {

    private List<Flight> flights = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addFlight(Flight flight) {
        flights.add(new Flight(flight));
        notifyObservers();
    }

    public List<Flight> getFlights() {
        List<Flight> copy = new ArrayList<>();
        for (Flight f : flights) {
            copy.add(new Flight(f));
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
