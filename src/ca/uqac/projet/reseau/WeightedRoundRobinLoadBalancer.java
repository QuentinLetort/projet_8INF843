package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WeightedRoundRobinLoadBalancer extends LoadBalancer {
    private Queue<Server> servers;

    public WeightedRoundRobinLoadBalancer(String name, Point2D position) {
        super(name, position);
        this.servers = new LinkedList<>();
    }

    public WeightedRoundRobinLoadBalancer() {
        super();
        this.servers = new LinkedList<>();
    }

    @Override
    protected Server getServeur(Device device) {
        if (servers.isEmpty()) {
            for (Server server : this.getServeurs()) {
                for (int i = 0; i < server.getWeight(); i++) {
                    servers.add((server));
                }
            }
        }
        Server serverSelected = servers.poll();
        serverSelected.traiteDevice(device);
        this.getEdge(serverSelected).setConnected(true);

        return serverSelected;
    }

}
