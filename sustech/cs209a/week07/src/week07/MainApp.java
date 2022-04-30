// SPDX-License-Identifier: AGPL-3.0-or-later
package week07;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        initial_MainUI();
    }

    public void initial_MainUI() {
        try {
            final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/MainUIOfLife.fxml"));
            final BorderPane rootLayout = loader.load();
            // Show the scene containing the root layout.
            final Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            MainUIOfLifeControl controller = loader.getController();

            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
