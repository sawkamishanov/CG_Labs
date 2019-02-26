package lab_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lab_1.Controller.MainWindowController;
import lab_1.Model.Object2D;


public class Main extends Application {

    Stage primaryStage;
    VBox rootLayout;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lab_1/MainWindowView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("CG_Labs");
        primaryStage.setScene(scene);

        /**
         * Инициализация модели
         * */
        MainWindowController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.init2DModel(new Object2D());

        primaryStage.show();
    }
}
