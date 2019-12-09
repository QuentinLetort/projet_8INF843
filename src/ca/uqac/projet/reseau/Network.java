package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Network {
    private String name;
    private ArrayList<Node> nodes;

    public Network(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Node n : nodes) {
            for (Edge e : n.getEdges()) {
                if (!edges.contains(e)) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public Node nodeNamed(String name) {
        Node result = null;
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                result = node;
            }
        }
        return result;
    }

    public ArrayList<Device> getDevices() {
        ArrayList<Device> devices = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof Device) {
                devices.add((Device) node);
            }
        }
        return devices;
    }

    public LoadBalancer getBalancer() {
        LoadBalancer balancer = null;
        for (Node node : nodes) {
            if (node instanceof LoadBalancer) {
                balancer = (LoadBalancer) node;
            }
        }
        return balancer;
    }

    public void addEdge(Node startNode, Node endNode) {
        Edge edge = new Edge(startNode, endNode);
        startNode.addEdge(edge);
        endNode.addEdge(edge);
    }

    public void addEdge(String startName, String endName) {
        Node startNode = nodeNamed(startName);
        Node endNode = nodeNamed(endName);
        if ((startNode != null) && (endNode != null)) {
            addEdge(startNode, endNode);
        }
    }

    public void deleteEdge(Edge edge) {
        edge.getStartNode().getEdges().remove(edge);
        edge.getEndNode().getEdges().remove(edge);
    }

    public void deleteNode(Node node) {
        for (Edge edge : node.getEdges()) {
            edge.otherSide(node).getEdges().remove(edge);
        }
        nodes.remove(node);
    }

    public Node nodeAt(double x, double y) {
        for (Node n : nodes) {
            Point2D c = n.getPosition();
            double d = (x - c.getX()) * (x - c.getX()) + (y - c.getY()) * (y - c.getY());
            if (d <= (Node.RADIUS * Node.RADIUS)) return n;
        }
        return null;
    }

    public String toString() {
        return (name + "(" + nodes.size() + " nodes, " + getEdges().size() + " edges)");
    }

    public void draw(GraphicsContext gctx) {
        ArrayList<Edge> edges = getEdges();
        for (Edge e : edges)
            e.draw(gctx);
        for (Node n : nodes)
            n.draw(gctx);
    }

    public static Network dataSample(LoadBalancer balancer, int nbDevice, int nbServer, int maxWeight, double maxHeight, double maxWidth) {
        Network network = new Network("Network");
        // Generate device nodes and links with load balancer
        for (int i = 0; i < nbDevice; i++) {
            double x = maxWidth / 4;
            double y = (maxHeight / (nbDevice + 1)) * (i + 1);
            Device temp = new Device(String.valueOf((char) (i + 'A')), new Point2D(x, y));
            network.addNode(temp);
            network.addEdge(temp, balancer);
        }
        // Generate server nodes and links with load balancer
        for (int i = 0; i < nbServer; i++) {
            double x = maxWidth * 3 / 4;
            double y = (maxHeight / (nbServer + 1)) * (i + 1);
            int weight = new Random().nextInt(maxWeight) + 1;
            Server temp = new Server("Serveur " + String.valueOf((char) (i + 'A')), new Point2D(x, y), weight);
            network.addNode(temp);
            network.addEdge(temp, balancer);
        }
        balancer.setPosition(new Point2D(maxWidth / 2, maxHeight / 2));
        network.addNode(balancer);
        return network;
    }
}
