package lab_4.Model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class ConvexPolygon extends Polygon {

    private final Color COLOR_CLIPPING_POLYGON = Color.VIOLET;
    private final Color COLOR_GENERATE_POLYGON = Color.GREEN;
    private final double OPACITY = 0.5;

    private ArrayList<Point2D> points = new ArrayList<>();

    public ConvexPolygon() {}

    public ConvexPolygon(ArrayList<Point2D> points) {
        this.points = points;
        if (points != null) {
            for (Point2D p : points) {
                getPoints().add(p.getX());
                getPoints().add(p.getY());
            }
        }
        setFill(COLOR_CLIPPING_POLYGON);
        setOpacity(OPACITY);
    }

    public void generateConvexPolygon() {
        int vertices = 3 + new Random().nextInt(5);

        for (int i = 0; i < vertices; ++i) {
            points.add(new Point2D(Math.random()*700 + 100, Math.random()*400 + 100));
        }

        for (Point2D p : points) {
            getPoints().add(p.getX());
            getPoints().add(p.getY());
        }
        setFill(COLOR_GENERATE_POLYGON);
        setOpacity(OPACITY);
    }

    public ArrayList<Point2D> getPointsAsList() {
        return points;
    }
}
