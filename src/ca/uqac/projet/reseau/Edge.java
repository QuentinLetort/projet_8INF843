package ca.uqac.projet.reseau;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Edge {
    private String label;
    private Node startNode;
    private Node endNode;
    private boolean connected;

    public Edge(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.connected = false;
    }

    public Edge(String label, Node startNode, Node endNode) {
        this.label = label;
        this.startNode = startNode;
        this.endNode = endNode;
        this.connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public Node otherSide(Node node) {
        return node == startNode ? endNode : startNode;
    }

    public void draw(GraphicsContext gctx) {
        Color color = connected ? Color.GREEN : Color.RED;
        gctx.setStroke(color);
        gctx.strokeLine(
                startNode.getPosition().getX(),
                startNode.getPosition().getY(),
                endNode.getPosition().getX(),
                endNode.getPosition().getY()
        );
    }

    public String toString() {
        return (startNode.toString() + " --> " + endNode.toString());
    }
}
