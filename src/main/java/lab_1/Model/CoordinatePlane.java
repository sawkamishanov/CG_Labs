package lab_1.Model;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;


public class CoordinatePlane extends Pane {

    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public CoordinatePlane(int width, int height,
            double xLow, double xHi, double xTickUnit,
            double yLow, double yHi, double yTickUnit) {
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        xAxis = new NumberAxis(xLow, xHi, xTickUnit);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(width);
        xAxis.setLayoutY(height / 2);

        yAxis = new NumberAxis(yLow, yHi, yTickUnit);
        yAxis.setSide(Side.LEFT);
        yAxis.setMinorTickVisible(false);
        yAxis.setPrefHeight(height);
        yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));
        getChildren().setAll(xAxis, yAxis);
    }

    public double mapX(double x) {
        double tx = getPrefWidth() / 2;
        double sx = getPrefWidth() / (xAxis.getUpperBound() - xAxis.getLowerBound());
        return x * sx + tx;
    }

    public double mapY(double y) {
        double ty = getPrefHeight() / 2;
        double sy = getPrefHeight() / (yAxis.getUpperBound() - yAxis.getLowerBound());
        return -y * sy + ty;
    }
}

