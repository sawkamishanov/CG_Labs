package lab_2.Model;

import javafx.geometry.Point2D;


public class BezierCurve {

    private Point2D[] curve;

    private int factorial(int n) { return n <= 1 ? 1: n*factorial(n - 1); }

    private double BezierBasis(int i, int n, double t) {
        return (factorial(n)/(factorial(i)*factorial(n - i)))*Math.pow(t, i)*Math.pow(1 - t, n - i);
    }

    public Point2D[] getBezierCurve(Point2D[] controlPoints) {
        final double step = 0.01;
        final int points = (int)(1/step);
        curve = new Point2D[points + 1];

        int j = 0;
        for (double t = 0; t < 1 + step; t += step) {
            if (t > 1) t = 1;
            double xTemp = 0, yTemp = 0;
            for (int i = 0; i < controlPoints.length; ++i) {
                double B = BezierBasis(i, controlPoints.length - 1, t);
                xTemp += controlPoints[i].getX()*B;
                yTemp += controlPoints[i].getY()*B;
            }
            curve[j] = new Point2D(xTemp, yTemp);
            ++j;
        }
        return curve;
    }
}
