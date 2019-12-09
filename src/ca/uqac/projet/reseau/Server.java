package ca.uqac.projet.reseau;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Server extends Node {
    private Queue<Device> devices;
    int weight;

    public Server(String name, Point2D position) {
        super(name, position);
        this.devices = new LinkedList<>();
        this.weight = 1;
    }

    public Server(String name, Point2D position, int weight) {
        super(name, position);
        this.devices = new LinkedList<>();
        this.weight = weight;
    }

    public Server(Server server) {
        super(server.getName(), server.getPosition());
        this.devices = new LinkedList<>();
        this.weight = server.getWeight();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void traiteDevice(Device device) {
        devices.offer(device);
    }

    @Override
    public void draw(GraphicsContext gctx) {
        super.draw(gctx);

        gctx.setFont(Font.font(FONT_POLICY, 12));
        gctx.setFill(Color.WHITE);
        gctx.fillText(String.valueOf(weight), getPosition().getX() - 4, getPosition().getY() + 4);

        if (devices.size() > 0) {
            List<String> namesList = devices.stream().map(p -> p.getName()).collect(Collectors.toList());
            gctx.setFill(Color.BLACK);
            String nbDevices = namesList.size() + " device";
            nbDevices += namesList.size() > 1 ? "s: ":": ";
            gctx.fillText(nbDevices + namesList.toString(), getPosition().getX() - RADIUS, getPosition().getY() + 2 * RADIUS);
        }
    }
}
