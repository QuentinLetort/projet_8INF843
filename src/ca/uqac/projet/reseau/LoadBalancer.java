package ca.uqac.projet.reseau;

import com.sun.prism.Image;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class LoadBalancer extends Node {
    public LoadBalancer(String name, Point2D position) {
        super(name, position);
    }


    public LoadBalancer() {
        super("Load balancer");
    }

    protected ArrayList<Server> getServeurs() {
        ArrayList<Server> resultat = new ArrayList<>();
        for (Node n : this.neighbours()) {
            if (n instanceof Server) {
                resultat.add((Server) n);
            }
        }
        return resultat;
    }

    public void getRessource(Device device) {
        System.out.println(device.getName() + " get the service from " + this.getServeur(device).getName());
    }

    protected abstract Server getServeur(Device device);
}
