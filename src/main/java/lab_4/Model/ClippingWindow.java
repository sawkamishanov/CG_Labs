package lab_4.Model;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public class ClippingWindow extends Rectangle {

    public ClippingWindow() {
        super(100, 100, 200, 200);
        setStrokeWidth(1.5);
        setStroke(Color.BLACK);
        setFill(Color.WHITE);
        getStrokeDashArray().addAll(25.0, 10.0);
        enableDrag();
    }

    private void enableDrag() {
        final Delta delta = new Delta();
        setOnMousePressed(event -> {
            delta.x = getX() - event.getX();
            delta.y = getY() - event.getY();
            getScene().setCursor(Cursor.MOVE);
        });
        setOnMouseReleased(event -> getScene().setCursor(Cursor.HAND));
        setOnMouseDragged(event -> {
            double newx = delta.x + event.getX(), newy = delta.y + event.getY();
            if (newx > 0 && newx < getScene().getWidth())
                setX(newx);
            if (newy > 0 && newy < getScene().getHeight())
                setY(newy);
        });
        setOnMouseEntered(event -> {
            if (!event.isPrimaryButtonDown())
                getScene().setCursor(Cursor.HAND);
        });
        setOnMouseExited(event -> {
            if (!event.isPrimaryButtonDown())
                getScene().setCursor(Cursor.DEFAULT);
        });
    }

    private class Delta { double x, y; }

    public ArrayList<Point2D> getPointsAsList() {
        return new ArrayList<>(Arrays.asList(new Point2D(getX(), getY()), new Point2D(getX() + getWidth(), getY()),
                new Point2D(getX() + getWidth(), getY() + getHeight()), new Point2D(getX(), getY() + getHeight())));
    }
}
