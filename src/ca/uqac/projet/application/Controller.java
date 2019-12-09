package ca.uqac.projet.application;

import ca.uqac.projet.reseau.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    private static final int NB_DEVICES = 15;
    private static final int NB_SERVERS = 3;
    private static final int MAX_WEIGHT = 3;
    private static final String ROUND_ROBIN = "Round robin";
    private static final String WEIGHTED_ROUND_ROBIN = "Weighted round robin";
    private static final String RANDOM = "Random";
    private static final String WEIGHTED_RANDOM = "Weighted random";

    @FXML
    private ToggleGroup choiceAlgorithm;

    @FXML
    private Canvas canvas;

    @FXML
    private Slider devicesSlider;

    @FXML
    private Label devicesValue;

    @FXML
    private Slider serverSlider;

    @FXML
    private Label serversValue;

    @FXML
    private Slider weightSlider;

    @FXML
    private Label weightValue;

    @FXML
    private Button changeButton;

    private Network network;
    private GraphicsContext gc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSliders();
        initializeToggleAlgorithm();
        network = Network.dataSample(new RoundRobinLoadBalancer(), NB_DEVICES, NB_SERVERS, MAX_WEIGHT, canvas.getHeight(), canvas.getWidth());
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        network.draw(gc);
    }

    public void initializeSliders() {
        devicesSlider.setValue(NB_DEVICES);
        devicesValue.setText(String.valueOf(NB_DEVICES));
        serverSlider.setValue(NB_SERVERS);
        serversValue.setText(String.valueOf(NB_SERVERS));
        weightSlider.setValue(MAX_WEIGHT);
        weightValue.setText(String.valueOf(MAX_WEIGHT));
    }
    public void initializeToggleAlgorithm() {
        choiceAlgorithm.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                changeButton.setDisable(false);
            }
        });
    }


    private void update() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        network.draw(gc);
        changeButton.setDisable(true);
    }

    @FXML
    private void updateDevicesSliderValue() {
        int value = (int) devicesSlider.getValue();
        devicesValue.setText(String.valueOf(value));
        changeButton.setDisable(false);
    }

    @FXML
    private void updateServersSliderValue() {
        int value = (int) serverSlider.getValue();
        serversValue.setText(String.valueOf(value));
        changeButton.setDisable(false);
    }

    @FXML
    private void updateWeightSliderValue() {
        int value = (int) weightSlider.getValue();
        weightValue.setText(String.valueOf(value));
        changeButton.setDisable(false);
    }

    @FXML
    public void applyChanges() {
        LoadBalancer balancer = null;
        switch (((RadioButton) choiceAlgorithm.getSelectedToggle()).getText()) {
            case ROUND_ROBIN:
                balancer = new RoundRobinLoadBalancer();
                break;
            case WEIGHTED_ROUND_ROBIN:
                balancer = new WeightedRoundRobinLoadBalancer();
                break;
            case RANDOM:
                balancer = new RandomLoadBalancer();
                break;
            case WEIGHTED_RANDOM:
                balancer = new WeightedRandomLoadBalancer();
                break;
        }
        int nbDevices = (int) devicesSlider.getValue();
        int nbServers = (int) serverSlider.getValue();
        int maxWeight = (int) weightSlider.getValue();
        network = Network.dataSample(balancer, nbDevices, nbServers, maxWeight, canvas.getHeight(), canvas.getWidth());
        update();
    }

    @FXML
    public void reset() {
        choiceAlgorithm.getToggles().get(0).setSelected(true);
        devicesSlider.setValue(NB_DEVICES);
        updateDevicesSliderValue();
        serverSlider.setValue(NB_SERVERS);
        updateServersSliderValue();
        weightSlider.setValue(MAX_WEIGHT);
        updateWeightSliderValue();
        network = Network.dataSample(new RoundRobinLoadBalancer(), NB_DEVICES, NB_SERVERS, MAX_WEIGHT, canvas.getHeight(), canvas.getWidth());
        update();

    }

    @FXML
    public void connectAll() {
        for (Device dev : network.getDevices()) {
            dev.accessRessource(network.getBalancer());
        }
        update();
    }

    public void modifyCanvas(MouseEvent mouseEvent) {
        Node node = network.nodeAt(mouseEvent.getX(), mouseEvent.getY());
        if (node != null && node instanceof Device) {
            ((Device) node).accessRessource(network.getBalancer());
            update();
        }
    }
}


