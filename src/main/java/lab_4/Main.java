package lab_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lab_4/MainWindowView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setTitle("CG_Labs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
