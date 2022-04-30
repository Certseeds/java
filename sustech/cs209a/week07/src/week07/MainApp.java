/* CS209A_JAVA2/week07
   Copyright (C) 2020  nanoseeds

   CS209A_JAVA2/week07 is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as
   published by the Free Software Foundation, either version 3 of the
   License, or (at your option) any later version.

   CS209A_JAVA2/week07 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.
   */
/**
 * @Github: https://github.com/Certseeds/CS209A_JAVA2/week07
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-04-06 19:10:49
 * @LastEditors : nanoseeds
 */
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
