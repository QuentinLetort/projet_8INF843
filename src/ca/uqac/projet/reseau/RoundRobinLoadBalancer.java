package ca.uqac.projet.reseau;

import com.sun.javafx.scene.paint.GradientUtils;
import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinLoadBalancer extends LoadBalancer{
    private Queue<Server> servers;

    public RoundRobinLoadBalancer(String name, Point2D position) {
        super(name, position);
        this.servers = new LinkedList<>();
    }

    public RoundRobinLoadBalancer() {
        super();
        this.servers = new LinkedList<>();
    }

    @Override
    protected Server getServeur(Device device) {
        if(servers.isEmpty()) {
            servers.addAll(this.getServeurs());
        }
        Server serverSelected = servers.poll();
        serverSelected.traiteDevice(device);
        this.getEdge(serverSelected).setConnected(true);
        return serverSelected;
    }
}
