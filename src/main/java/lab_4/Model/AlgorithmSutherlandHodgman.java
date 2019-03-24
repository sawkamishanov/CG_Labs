package lab_4.Model;

import javafx.geometry.Point2D;
import java.util.ArrayList;


public class AlgorithmSutherlandHodgman {

    private ArrayList<Point2D> clipper, result;

    public AlgorithmSutherlandHodgman(ArrayList<Point2D> polygon, ArrayList<Point2D> clipper) {

        result  = new ArrayList<>(polygon);
        this.clipper = clipper;
        clipPolygon();
    }

    private void clipPolygon() {
        int len = clipper.size();
        for (int i = 0; i < len; i++) {

            int len2 = result.size();
            ArrayList<Point2D> input = result;
            result = new ArrayList<>(len2);

            Point2D A = clipper.get((i + len - 1) % len);
            Point2D B = clipper.get(i);

            for (int j = 0; j < len2; j++) {
                Point2D P = input.get((j + len2 - 1) % len2);
                Point2D Q = input.get(j);
                if (isInside(A, B, Q)) {
                    if (!isInside(A, B, P))
                        result.add(intersection(A, B, P, Q));
                    result.add(Q);
                } else if (isInside(A, B, P))
                    result.add(intersection(A, B, P, Q));
            }
        }
    }

    private boolean isInside(Point2D A, Point2D B, Point2D C) {
        return (A.getX() - C.getX()) * (B.getY() - C.getY()) > (A.getY() - C.getY()) * (B.getX() - C.getX());
    }

    private Point2D intersection(Point2D A, Point2D B, Point2D P, Point2D Q) {
        double A1 = B.getY() - A.getY();
        double B1 = A.getX() - B.getX();
        double C1 = A1 * A.getX() + B1 * A.getY();

        double A2 = Q.getY() - P.getY();
        double B2 = P.getX() - Q.getX();
        double C2 = A2 * P.getX() + B2 * P.getY();

        double det = A1 * B2 - A2 * B1;
        double x = (B2 * C1 - B1 * C2) / det;
        double y = (A1 * C2 - A2 * C1) / det;

        return new Point2D(x, y);
    }

    public ArrayList<Point2D> getResult() {
        return result;
    }
}
