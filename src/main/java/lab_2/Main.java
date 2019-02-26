package lab_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab_2.Controller.MainWindowController;

public class Main extends Application {

    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lab_2/MainWindowView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 600);
        MainWindowController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle("CG_Labs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



