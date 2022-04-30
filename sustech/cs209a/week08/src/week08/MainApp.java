// SPDX-License-Identifier: AGPL-3.0-or-later
package week08;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/MainUIOfLife.fxml"));
            BorderPane rootLayout = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            MainUIOfLifeControl controller = loader.getController();

            controller.setMainApp(this);
            primaryStage.show();
            File file = getgnmgFilePath();
            if (null != file) {
                loadGnmgDataFromFile(file);
                controller.update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getgnmgFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);

        if (filePath != null) {
            File will_return = new File(filePath);
            if (will_return.exists()) {
                return will_return;
            }
            return null;
        } else {
            return null;
        }
    }

    public void setGnmgFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            // Update the stage title.
            primaryStage.setTitle("CS209A_Week08 - " + file.getName());
        } else {
            prefs.remove("filePath");
            // Update the stage title.
            primaryStage.setTitle("CS209A_Week08");
        }
    }

    public void loadGnmgDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(grid_n_m_gene_warpper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            grid_n_m_gene_warpper wrapper = (grid_n_m_gene_warpper) um.unmarshal(file);
            MainUIOfLifeControl.setG_n_m_g(wrapper.getGrid_n_m_gene());
//            personData.clear();
//            personData.addAll(wrapper.getPersons());
            // Save the file path to the registry.
            setGnmgFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
            setGnmgFilePath(null);
        }
    }

    public void saveGnmgDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(grid_n_m_gene_warpper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            grid_n_m_gene_warpper wrapper = new grid_n_m_gene_warpper();
            wrapper.setGrid_n_m_gene(MainUIOfLifeControl.getG_n_m_g());

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);
            // Save the file path to the registry.
            setGnmgFilePath(file);
        } catch (Exception e) { // catches ANY exception
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
