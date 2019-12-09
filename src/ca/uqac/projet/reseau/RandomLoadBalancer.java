package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer {
    public RandomLoadBalancer() {
        super();
    }

    public RandomLoadBalancer(String name, Point2D position) {
        super(name, position);
    }

    @Override
    protected Server getServeur(Device device) {
        List <Server> servers = getServeurs();
        int random = new Random().nextInt(servers.size());
        Server serverSelected = servers.get(random);
        serverSelected.traiteDevice(device);
        this.getEdge(serverSelected).setConnected(true);
        return serverSelected;
    }
}
