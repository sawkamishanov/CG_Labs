package lab_1.Model;
import Jama.Matrix;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Object2D extends Object implements Transformable {

    /** Параметры объекта */
    public static Color COLOR = Color.ORANGE;
    public static final double OPACITY = 0.6;
    /** Координатная плоскость */
    private CoordinatePlane coordinatePlane;
    /** Размерность пространства */
    private final int dim = 2;
    /** Количество вершин */
    private int vertices;
    /** Матрица преобразования */
    private Matrix transformationMatrix;

    public Object2D() {
        vertices = 0;
    }

    public double[][] getMatrixToArray() { return matrix.getArray(); }

    public double[] getColumnMatrix(int j) {
        if (matrix != null) {
            double[] column = new double[vertices];
            for (int i = 0; i < column.length; ++i)
                column[i] = getMatrixToArray()[i][j];
            return column;
        }
        return null;
    }

    /** Построение 2D-объекта */
    @Override
    public void plotObject() {
        switch (vertices) {
            case 1:
                shape = new Circle(coordinatePlane.mapX(matrix.get(0, 0)), coordinatePlane.mapY(matrix.get(0, 1)), 2);
                break;
            case 2:
                shape = new Line(coordinatePlane.mapX(matrix.get(0,0)), coordinatePlane.mapY(matrix.get(0,1)),
                                coordinatePlane.mapX(matrix.get(1,0)), coordinatePlane.mapY(matrix.get(1,1)));
                break;
            default:
                shape = new Polygon();
                for (int i = 0; i < vertices; ++i) {
                    for (int j = 0; j < dim; ++j) {
                        if (j % 2 == 0)
                            ((Polygon) shape).getPoints().add(coordinatePlane.mapX(matrix.get(i, j)));
                        else
                            ((Polygon) shape).getPoints().add(coordinatePlane.mapY(matrix.get(i, j)));
                    }
                }
                break;
        }
        shape.setFill(COLOR);
        shape.setOpacity(OPACITY);
    }

    /** Генерация случайного 2D-объекта */
    @Override
    public void generateObject() {
        double[][] object = new double[vertices][dim + 1];
        for (int i = 0; i < vertices; ++i) {
            for (int j = 0; j < dim + 1; ++j) {
                if (j == 0)
                    object[i][j] = 50 + Math.random()*150; /* [50; 200) */
                else
                    if (j == 1)
                        object[i][j] = -50 + Math.random()*100; /* [-50; 50) */
                    else
                        object[i][j] = 1;
            }
        }
        matrix = new Matrix(object);
    }

    @Override
    public void transformObject(Matrix transformationMatrix) {
        if (matrix == null) throw new NullPointerException();
        else
            matrix = matrix.times(transformationMatrix);
    }

    public void reflectObject(Line line) {
        if (matrix == null) throw new NullPointerException();
        else {

            Matrix firstOperation = new Matrix(new double[][] {{1, 0, 0}, {0, 1, 0}, {-line.getStartX(), -line.getStartY(), 1}});
            double slope = (line.getEndY() - line.getStartY())/(line.getEndX() - line.getStartX());
            double alpha = Math.atan(slope);
            Matrix secondOperation = new Matrix(new double[][] {{Math.cos(-alpha), Math.sin(-alpha), 0},
                                                             {-Math.sin(-alpha), Math.cos(-alpha), 0},
                                                             {0, 0, 1}});
            Matrix reflectMatrix = new Matrix(new double[][] {{1, 0, 0}, {0, -1, 0}, {0, 0, 1}});
            Matrix returnFirstOperation = new Matrix(new double[][] {{Math.cos(alpha), Math.sin(alpha), 0},
                                                                   {-Math.sin(alpha), Math.cos(alpha), 0},
                                                                   {0, 0, 1}});
            Matrix returnSecondOperation = new Matrix(new double[][] {{1, 0, 0}, {0, 1, 0}, {line.getStartX(), line.getStartY(), 1}});
            matrix = matrix.times(firstOperation).times(secondOperation).times(reflectMatrix).times(returnFirstOperation).times(returnSecondOperation);
        }
    }

    @Override
    public void rotateObject(Matrix transformationMatrix) {
        if (matrix == null) throw new NullPointerException();
        else {
            matrix = matrix.times(transformationMatrix);
            matrix = matrix.times(this.transformationMatrix);
            transformationMatrix.set(2, 0, -1*transformationMatrix.get(2, 0));
            transformationMatrix.set(2, 1, -1*transformationMatrix.get(2, 1));
            matrix = matrix.times(transformationMatrix);
        }
    }

    /**
     *  Геттеры и сеттеры
     * */

    public void setCoordinatePlane(CoordinatePlane coordinatePlane) {
        this.coordinatePlane = coordinatePlane;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public Shape getLastShape() { return lastShape; }

    public Shape getShape() {
        return shape;
    }

    public void setLastShape(Shape lastShape) {
        this.lastShape = lastShape;
    }
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public void setTransformationMatrix(Matrix transformationMatrix) {
        this.transformationMatrix = transformationMatrix;
    }
}
