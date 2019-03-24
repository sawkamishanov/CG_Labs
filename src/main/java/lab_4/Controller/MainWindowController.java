package lab_4.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lab_4.Model.AlgorithmSutherlandHodgman;
import lab_4.Model.ClippingWindow;
import lab_4.Model.ConvexPolygon;

public class MainWindowController {

    private ClippingWindow clippingWindow = new ClippingWindow();
    private ConvexPolygon polygon;
    private ConvexPolygon clippingPolygon;

    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField sizeTextField;

    @FXML
    private void initialize() {
        anchorPane.getChildren().addAll(clippingWindow);
    }

    @FXML
    private void clippingActionButton(ActionEvent actionEvent) {
        if (polygon != null) {
            AlgorithmSutherlandHodgman result = new AlgorithmSutherlandHodgman(polygon.getPointsAsList(), clippingWindow.getPointsAsList());
            if (clippingPolygon != null)
                anchorPane.getChildren().remove(clippingPolygon);

            clippingPolygon = new ConvexPolygon(result.getResult());
            anchorPane.getChildren().add(clippingPolygon);
        }
    }

    @FXML
    private void generateActionButton(ActionEvent actionEvent) {
        clear();
        polygon = new ConvexPolygon();
        polygon.generateConvexPolygon();
        anchorPane.getChildren().add(polygon);
    }

    @FXML
    private void clearActionButton(ActionEvent actionEvent) {
        clear();
    }

    @FXML
    private void setSizeActionButton(ActionEvent actionEvent) {
        String[] text = sizeTextField.getText().split("x");
        clippingWindow.setWidth(Double.parseDouble(text[0]));
        clippingWindow.setHeight(Double.parseDouble(text[1]));
    }

    private void clear() {
        if (polygon != null) {
            anchorPane.getChildren().remove(polygon);
            polygon = null;
        }
        if (clippingPolygon != null) {
            anchorPane.getChildren().remove(clippingPolygon);
            clippingPolygon = null;
        }
    }
}
