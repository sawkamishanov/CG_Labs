package lab_2.Controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import lab_2.Model.BezierCurve;
import java.util.Stack;

public class MainWindowController {

    private Stage primaryStage;
    private Stack<Circle> controlPoints = new Stack<>();
    private Stack<BoundLine> boundLines = new Stack<>();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void initialize() {
        initContextMenu();
    }

    private void initContextMenu() {
        MenuItem itemToAdd = new MenuItem("Добавить узел"), itemToDelete = new MenuItem("Удалить узел");
        ContextMenu contextMenuToAdd = new ContextMenu(itemToAdd, itemToDelete);
        anchorPane.setOnContextMenuRequested(contextMenuEvent -> {
            contextMenuToAdd.show(anchorPane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            itemToAdd.setOnAction(event -> {
                Anchor anchor = new Anchor(new SimpleDoubleProperty(contextMenuEvent.getX()), new SimpleDoubleProperty(contextMenuEvent.getY()));
                if (!controlPoints.isEmpty()) {
                    BoundLine line = new BoundLine(anchor.centerXProperty(), anchor.centerYProperty(), controlPoints.peek().centerXProperty(), controlPoints.peek().centerYProperty());
                    boundLines.add(line);
                    anchorPane.getChildren().add(line);
                }
                controlPoints.add(anchor);
                anchorPane.getChildren().add(anchor);
                drawCurve();
            });
            itemToDelete.setOnAction(event -> {
                if (!controlPoints.isEmpty()) {
                    if (controlPoints.peek().contains(contextMenuEvent.getX(), contextMenuEvent.getY())) {
                        anchorPane.getChildren().remove(controlPoints.pop());
                        if (!boundLines.isEmpty())
                            anchorPane.getChildren().remove(boundLines.pop());
                        drawCurve();
                    }
                }
            });
        });
    }

    private Group group;
    private void drawCurve() {
        Point2D[] coordinates = new Point2D[controlPoints.size()];
        for (int i = 0; i < controlPoints.size(); ++i)
            coordinates[i] = new Point2D(controlPoints.get(i).getCenterX(), controlPoints.get(i).getCenterY());
        Point2D[] points = new BezierCurve().getBezierCurve(coordinates);
        if (group != null)
            anchorPane.getChildren().remove(group);
        group = new Group();
        for (int i = 0; i < points.length; ++i) {
                    /*anchorPane.getChildren().add(new Path(new MoveTo(points[i].getX(), points[i].getY()),
                        new LineTo(points[i + 1].getX(), points[i + 1].getY())));*/
            if (i != points.length - 1)
                group.getChildren().add(new Line(points[i].getX(), points[i].getY(), points[i + 1].getX(), points[i + 1].getY()));
        }
        anchorPane.getChildren().add(group);
    }

    class BoundLine extends Line {
        BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStroke(Color.GRAY);
            getStrokeDashArray().addAll(25.0, 10.0);
        }
    }

    class Anchor extends Circle {
        Anchor(DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 7.0);
            x.bind(centerXProperty());
            y.bind(centerYProperty());
            setFill(Color.YELLOWGREEN);
            setStroke(Color.GREEN);
            setStrokeType(StrokeType.OUTSIDE);
            setStrokeWidth(2.0);
            enableDrag();
        }

        private void enableDrag() {
            final Delta delta = new Delta();
            setOnMousePressed(event -> {
                delta.x = getCenterX() - event.getX();
                delta.y = getCenterY() - event.getY();
                getScene().setCursor(Cursor.MOVE);
            });
            setOnMouseReleased(event -> getScene().setCursor(Cursor.HAND));
            setOnMouseDragged(event -> {
                double newx = delta.x + event.getX(), newy = delta.y + event.getY();
                setCenterX(newx); setCenterY(newy);
                drawCurve();
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

        private class Delta {
            double x, y;
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
