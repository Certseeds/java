// SPDX-License-Identifier: AGPL-3.0-or-later
package week08;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainUIOfLifeControl {
    static int[][] blockPattern = {
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0},
    };
    static int[][] beePattern = {
            {0, 1, 1, 0},
            {1, 0, 0, 1},
            {0, 1, 1, 0}
    };
    static int[][] blinkerPattern = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
    };
    static int[][] gliderPattern = {
            {0, 0, 1},
            {1, 0, 1},
            {0, 1, 1}
    };
    private static grid_n_m_gene gnmg = new grid_n_m_gene();
    //    static int[][] grid;
//    static int generation = 0;
//    static int M = 10;
//    static int N = 10;
    @FXML
    TextField gene_number;
    @FXML
    TextArea grid_text_area;
    @FXML
    TextArea test2;
    @FXML
    Button play_button;
    @FXML
    Button BlockButton;
    @FXML
    Button GliderButton;
    @FXML
    Button BlinkerButton;
    private MainApp mainApp;
    @FXML
    private Stage primaryStage;
    @FXML
    private BorderPane rootLayout;

    public static grid_n_m_gene getG_n_m_g() {
        return gnmg;
    }

    public static void setG_n_m_g(grid_n_m_gene g) {
        gnmg = g;
    }

    @FXML
    public static void init(int[][] pattern, int M, int N) {
        if (0 == pattern.length ||
                0 == pattern[0].length ||
                M <= 0 || N <= 0) {
            return;
        }
        gnmg = new grid_n_m_gene(10, 10, 0);
        final int left = (M - pattern.length) / 2;
        final int down = (N - pattern[0].length) / 2;
        for (int i = 0; i < pattern.length; i++) {
            System.arraycopy(pattern[i], 0, MainUIOfLifeControl.gnmg.getGrid()[left + i], down, pattern[0].length);
        }
        int generation = 0;
        //displayGrid(grid, M, N);

        System.out.printf("Generation: %d\n", generation);
        // grid = nextGeneration(grid, M, N);
        //displayGrid(grid, M, N);
        //generate the nextGeneration and display

    }

    static String grid_to_string() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MainUIOfLifeControl.gnmg.getM(); i++) {
            for (int j = 0; j < MainUIOfLifeControl.gnmg.getN(); j++) {
                if (MainUIOfLifeControl.gnmg.getGrid()[i][j] == 0) {
                    sb.append(".");
                } else {
                    sb.append("*");
                }
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    static int[][] nextGeneration(int[][] grid, int M, int N) {
        int[][] future = new int[M][N];

        // Loop through every cell
        for (int l = 1; l < M - 1; l++) {
            for (int m = 1; m < N - 1; m++) {
                // finding no Of Neighbours that are alive
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        aliveNeighbours += grid[l + i][m + j];
                    }
                }
                // The cell needs to be subtracted from
                // its neighbours as it was counted before
                aliveNeighbours -= grid[l][m];

                // Implementing the Rules of Life
                // Cell is lonely and dies
                if ((grid[l][m] == 1) && (aliveNeighbours < 2)) {
                    future[l][m] = 0;
                }
                // Cell dies due to over population
                else if ((grid[l][m] == 1) && (aliveNeighbours > 3)) {
                    future[l][m] = 0;
                }
                // A new cell is born
                else if ((grid[l][m] == 0) && (aliveNeighbours == 3)) {
                    future[l][m] = 1;
                }
                // Remains the same
                else {
                    future[l][m] = grid[l][m];
                }
            }
        }
        MainUIOfLifeControl.gnmg.setGeneration(MainUIOfLifeControl.gnmg.getGeneration() + 1);
        return future;
    }

    @FXML
    public void open(File file) {}

    @FXML
    public void play_click() {
        System.out.printf("Click Play,Generatio is %d\n", MainUIOfLifeControl.gnmg.getGeneration());
        MainUIOfLifeControl.gnmg.setGrid(nextGeneration(MainUIOfLifeControl.gnmg.getGrid(),
                MainUIOfLifeControl.gnmg.getM(), MainUIOfLifeControl.gnmg.getN()));
        gene_number.setText(Integer.toString(MainUIOfLifeControl.gnmg.getGeneration()));
        grid_text_area.setText(grid_to_string());
    }

    @FXML
    private void initialize() {
        blockPattern_press();
    }

    @FXML
    private void blockPattern_press() {
        System.out.println("initial with blockPattern");
        initialize_public(blockPattern);
    }

    @FXML
    private void blinkerPattern_press() {
        System.out.println("initial with blinkerPattern");
        initialize_public(blinkerPattern);
    }

    @FXML
    private void gliderPattern_press() {
        System.out.println("initial with gliderPattern");
        initialize_public(gliderPattern);
    }

    @FXML
    public void initialize_public(int[][] Pattern) {
        MainUIOfLifeControl.gnmg.setGeneration(0);
        init(Pattern, MainUIOfLifeControl.gnmg.getM(), MainUIOfLifeControl.gnmg.getN());
        gene_number.setText(Integer.toString(MainUIOfLifeControl.gnmg.getGeneration()));
        grid_text_area.setText(grid_to_string());
    }

    @FXML
    public void update() {
        gene_number.setText(Integer.toString(MainUIOfLifeControl.gnmg.getGeneration()));
        grid_text_area.setText(grid_to_string());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleOpen() {
        System.out.println("open");
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadGnmgDataFromFile(file);
        }
        System.out.println(MainUIOfLifeControl.gnmg.getGeneration());
        play_click();
    }

    @FXML
    private void handleSave() {
        File personFile = mainApp.getgnmgFilePath();
        if (personFile != null) {
            mainApp.saveGnmgDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveGnmgDataToFile(file);
        }
    }
}
