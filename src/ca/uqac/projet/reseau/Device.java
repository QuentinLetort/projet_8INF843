package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;

public class Device extends Node {
    public Device(String name, Point2D position) {
        super(name, position);
    }

    public void accessRessource(LoadBalancer balancer) {
        if (this.neighbours().contains(balancer) && !this.getEdge(balancer).isConnected()) {
            balancer.getRessource(this);
            this.getEdge(balancer).setConnected(true);
        }
    }
}
