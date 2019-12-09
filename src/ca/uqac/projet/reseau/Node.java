package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Node {
    public static int RADIUS = 15;
    public static String FONT_POLICY = "Arial";

    private String name;
    private Point2D position;
    private ArrayList<Edge> edges;

    public Node(String name, Point2D position) {
        this.name = name;
        this.position = position;
        this.edges = new ArrayList<>();
    }

    public Node(String name) {
        this.name = name;
        this.position = new Point2D(0, 0);
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Edge getEdge(Node otherSideNode) {
        Edge result = null;
        for (Edge edge : edges) {
            if (edge.otherSide(this).equals(otherSideNode)) {
                result = edge;
            }
        }
        return result;
    }

    public void addEdge(Edge newEdge) {
        edges.add(newEdge);
    }

    public ArrayList<Node> neighbours() {
        ArrayList<Node> result = new ArrayList<>();
        for (Edge edge : edges) {
            result.add(edge.otherSide(this));
        }
        return result;
    }

    public void draw(GraphicsContext gctx) {
        gctx.setFill(Color.color(0.6, 0.6, 1.0));
        gctx.fillOval(position.getX() - RADIUS, position.getY() - RADIUS, RADIUS * 2, RADIUS * 2);

        gctx.setStroke(Color.BLACK);
        gctx.strokeOval(position.getX() - RADIUS, position.getY() - RADIUS, RADIUS * 2, RADIUS * 2);

        gctx.setFont(Font.font(FONT_POLICY, 14));
        gctx.setFill(Color.BLACK);
        gctx.fillText(name, position.getX() + RADIUS, position.getY() - RADIUS);
    }

    public String toString() {
        return (name + "(" + position.getX() + "," + position.getY() + ")");
    }
}
