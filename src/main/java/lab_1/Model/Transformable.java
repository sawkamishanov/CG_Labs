package lab_1.Model;

import Jama.Matrix;

public interface Transformable {
    void transformObject(Matrix transformationMatrix);
    void rotateObject(Matrix transformationMatrix);
}
