/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.Observer;
import util.Subject;
import java.util.ArrayList;
import java.util.List;

public class PlaneModel implements Subject {

    private List<Plane> planes = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addPlane(Plane plane) {
        planes.add(new Plane(plane));
        notifyObservers();
    }

    public List<Plane> getPlanes() {
        List<Plane> copy = new ArrayList<>();
        for (Plane p : planes) {
            copy.add(new Plane(p));
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
