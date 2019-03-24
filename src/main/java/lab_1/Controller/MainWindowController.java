package lab_1.Controller;

import Jama.Matrix;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab_1.Model.CoordinatePlane;
import lab_1.Model.Object2D;

public class MainWindowController {

    /** Параметры координатной плоскости */
    static final int WIDTH = 1600;
    static final int HEIGHT = 800;

    /** Ссылка на главное окно */
    private Stage primaryStage;

    /** Объекты графического интерфейса */
    @FXML
    private ScrollPane scroll2DPane;
    @FXML
    private Button transformObjectButton, transformLineObjectButton, rotateObjectButton;
    @FXML
    private TextField verticesTextField;
    @FXML
    private TextField coordinatesLineBeginTextField, coordinatesLineEndTextField;
    @FXML
    private TextField coordinatesPointTextField;
    @FXML
    private Slider xScaleSlider, yScaleSlider;
    @FXML
    private Slider degreesSlider;
    @FXML
    private Tooltip sliderTooltip;
    @FXML
    private CheckBox xAxisCheckBox;
    @FXML
    private CheckBox yAxisCheckBox;
    @FXML
    private ColorPicker objectColorPicker;

    private CoordinatePlane coordinatePlane;
    private Object2D object2D;

    @FXML
    private void initialize() {
        build2DScene();
        setSettingsPanel(false);
    }

    public void init2DModel(Object2D object2D) {
        if (object2D == null) throw new NullPointerException();
        else {
            this.object2D = object2D;
            object2D.setCoordinatePlane(coordinatePlane);
        }
    }

    private void build2DScene() {
        coordinatePlane = new CoordinatePlane(WIDTH, HEIGHT, -800, 800, 80, -400, 400, 80);
        scroll2DPane.contentProperty().set(coordinatePlane);
    }

    private void setSettingsPanel(boolean flag) {
        objectColorPicker.setValue(Object2D.COLOR);
    }

    @FXML
    private void setColorAction(ActionEvent actionEvent) {
        Object2D.COLOR = objectColorPicker.getValue();
    }

    @FXML
    private void generateObjectActionButton(ActionEvent actionEvent) {
        try {
            if (object2D.getShape() != null)
                coordinatePlane.getChildren().remove(object2D.getShape());
            if (line != null)
                coordinatePlane.getChildren().remove(line);
            object2D.setVertices(Integer.parseInt(verticesTextField.getText()));
            if (object2D.getLastShape() != null)
                coordinatePlane.getChildren().remove(object2D.getLastShape());
            object2D.generateObject();
            object2D.plotObject();
            coordinatePlane.getChildren().add(object2D.getShape());
        } catch (NumberFormatException e) {
            showErrorDialog("Некорректное преобразование строки в число");
        }
    }

    @FXML
    private void transformObjectActionButton(ActionEvent actionEvent) {
        try {
            updateCoordinatePlane();
            int xAxis = xAxisCheckBox.isSelected() ? -1 : 1;
            int yAxis = yAxisCheckBox.isSelected() ? -1 : 1;
            object2D.transformObject(new Matrix(new double[][]{{yAxis*xScaleSlider.getValue(), 0, 0},
                                                                {0, xAxis*yScaleSlider.getValue(), 0},
                                                                {0, 0, 1}}));
            object2D.plotObject();
            coordinatePlane.getChildren().add(object2D.getShape());
        } catch (NumberFormatException e) {
            showErrorDialog("Некорректное преобразование строки в число");
        }
    }

    private Line line;
    @FXML
    private void transformLineObjectActionButton(ActionEvent actionEvent) {
        try {
            updateCoordinatePlane();
            line = new Line(getPointFromTextField(coordinatesLineBeginTextField)[0], getPointFromTextField(coordinatesLineBeginTextField)[1],
                    getPointFromTextField(coordinatesLineEndTextField)[0], getPointFromTextField(coordinatesLineEndTextField)[1]);
            object2D.reflectObject(line);
            object2D.plotObject();
            line.setStartX(coordinatePlane.mapX(line.getStartX())); line.setEndX(coordinatePlane.mapX(line.getEndX()));
            line.setStartY(coordinatePlane.mapY(line.getStartY())); line.setEndY(coordinatePlane.mapY(line.getEndY()));
            coordinatePlane.getChildren().add(line);
            coordinatePlane.getChildren().add(object2D.getShape());
        } catch (NumberFormatException e) {
            showErrorDialog("Некорректное преобразование строки в число");
        }
    }

    @FXML
    private void rotateObjectActionButton(ActionEvent actionEvent) {
        final double degreeToRad = Math.PI/180;
        try {
            updateCoordinatePlane();
            object2D.setTransformationMatrix(new Matrix(new double[][]{
                    {Math.cos(degreesSlider.getValue()*degreeToRad), Math.sin(degreesSlider.getValue()*degreeToRad), 0},
                    {-1*Math.sin(degreesSlider.getValue()*degreeToRad), Math.cos(degreesSlider.getValue()*degreeToRad), 0},
                    {0, 0, 1}}));
            object2D.rotateObject(new Matrix(new double[][]{
                    {1, 0, 0},
                    {0, 1, 0},
                    {-1*getPointFromTextField(coordinatesPointTextField)[0], -1*getPointFromTextField(coordinatesPointTextField)[1], 1}}));
            object2D.plotObject();
            coordinatePlane.getChildren().add(object2D.getShape());
        } catch (NumberFormatException e) {
            showErrorDialog("Некорректное преобразование строки в число. Формат ввода: \"X, Y\".");
        }
    }

    @FXML
    private void sliderOnShowingTooltip(Event event) {
        sliderTooltip.setText((int)degreesSlider.getValue() + "\u00B0");
    }

    private double[] getPointFromTextField(TextField textField) {
        String[] temp = textField.getText().split(", ");
        return new double[] {Double.parseDouble(temp[0]), Double.parseDouble(temp[1])};
    }

    private void updateCoordinatePlane() {
        if (object2D.getShape() != null)
            coordinatePlane.getChildren().remove(object2D.getShape());
        if (line != null)
            coordinatePlane.getChildren().remove(line);
        if (object2D.getLastShape() != null)
            coordinatePlane.getChildren().remove(object2D.getLastShape());
        object2D.setLastShape(object2D.getShape());
        object2D.getLastShape().setOpacity(0.3);
        coordinatePlane.getChildren().add(object2D.getLastShape());
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(message);
        alert.initOwner(primaryStage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
