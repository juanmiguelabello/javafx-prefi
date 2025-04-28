package aclcbukidnon.com.javafxactivity.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TrafficLightController {

    private enum TrafficLightColor {
        STOP, HOLD, GO
    }

    private TrafficLightColor currentColor = TrafficLightColor.STOP;

    private final Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> switchToNextColor())
    );

    @FXML
    private Circle redLight;

    @FXML
    private Circle yellowLight;

    @FXML
    private Circle greenLight;

    @FXML
    public void initialize() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        updateLights(); // Set initial state
    }

    @FXML
    protected void onStartClick() {
        timeline.play();
    }

    @FXML
    protected void onStopClick() {
        timeline.stop();
        currentColor = TrafficLightColor.STOP;
        updateLights();
    }

    private void switchToNextColor() {
        currentColor = switch (currentColor) {
            case STOP -> TrafficLightColor.HOLD;
            case HOLD -> TrafficLightColor.GO;
            case GO -> TrafficLightColor.STOP;
        };
        updateLights();
    }

    private void updateLights() {
        redLight.setFill(currentColor == TrafficLightColor.STOP ? Color.RED : Color.DARKGRAY);
        yellowLight.setFill(currentColor == TrafficLightColor.HOLD ? Color.YELLOW : Color.DARKGRAY);
        greenLight.setFill(currentColor == TrafficLightColor.GO ? Color.LIMEGREEN : Color.DARKGRAY);
    }
}
