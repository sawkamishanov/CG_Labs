package lab_1.Model;

import Jama.Matrix;
import javafx.scene.shape.Shape;

public abstract class Object {
    /** Матрица */
    protected Matrix matrix;
    /** Фигура для построения */
    protected Shape shape;
    /** Фигура на прошлом шаге*/
    protected Shape lastShape;
    public abstract void generateObject();
    public abstract void plotObject();
}
