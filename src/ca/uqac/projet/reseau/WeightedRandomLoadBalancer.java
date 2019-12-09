package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandomLoadBalancer extends LoadBalancer{

    public WeightedRandomLoadBalancer() {
        super();
    }

    public WeightedRandomLoadBalancer(String name, Point2D position) {
        super(name, position);
    }

    @Override
    protected Server getServeur(Device device) {
        List<Server> servers = new ArrayList<>();
        for (Server server : this.getServeurs()) {
            for (int i = 0; i < server.getWeight(); i++) {
                servers.add((server));
            }
        }
        int random = new Random().nextInt(servers.size());
        Server serverSelected = servers.get(random);
        serverSelected.traiteDevice(device);
        this.getEdge(serverSelected).setConnected(true);
        return serverSelected;
    }
}
