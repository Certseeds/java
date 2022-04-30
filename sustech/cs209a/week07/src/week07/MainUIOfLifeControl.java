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

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainUIOfLifeControl {
    static int[][] grid;
    private final static int[][] blockPattern = {
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0},
    };
    private final static int[][] blinkerPattern = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
    };
    private final static int[][] gliderPattern = {
            {0, 0, 1},
            {1, 0, 1},
            {0, 1, 1}
    };
    static int generation = 0;
    private final static int M = 10;
    private final static int N = 10;
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

    @FXML
    public static void init(int[][] pattern, int M, int N) {
        if (0 == pattern.length || 0 == pattern[0].length || M <= 0 || N <= 0) {
            return;
        }
        grid = new int[M][N];
        final int left = (M - pattern.length) / 2;
        final int down = (N - pattern[0].length) / 2;
        for (int i = 0; i < pattern.length; i++) {
            System.arraycopy(pattern[i], 0, grid[left + i], down, pattern[0].length);
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
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
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
        generation++;
        return future;
    }

    @FXML
    public void play_click() {
        System.out.printf("Click Play,Generatio is %d\n", generation);
        gene_number.setText(Integer.toString(generation));
        grid_text_area.setText(grid_to_string());
        grid = nextGeneration(grid, M, N);
    }

    @FXML
    private void initialize() {
        initialize_public(blockPattern);
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
        generation = 0;
        init(Pattern, M, N);
        gene_number.setText(Integer.toString(generation));
        grid_text_area.setText(grid_to_string());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
