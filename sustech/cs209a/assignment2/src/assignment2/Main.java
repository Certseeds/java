package assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final String root_path = "./config.yml";
    Controller controller;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        initial_UI();
    }

    public void initial_UI() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/sample.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controller = fxmlLoader.getController();
            controller.setStage(primaryStage);
            controller.init(root_path);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Visualisation of file statistics");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
