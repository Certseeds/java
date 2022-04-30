import java.util.Scanner;

// A simple Java program to implement Game of Life
public class GameOfLife {
    static int[][] grid;
    static int[][] blockPattern = {
            {0, 0, 0, 0},
            {0, 1, 1, 0}
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
    static int generation = 0;
    static int M = 10;
    static int N = 10;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("""
                choose pattern in
                1. blockPattern:
                2. beePattern:
                3. blinkerPattern:
                4. gliderPattern :
                """);
        int[][] pattern;
        int next = input.nextInt();
        String uesless = input.nextLine();
        switch (next) {
            case 1 -> pattern = blockPattern;
            case 2 -> pattern = beePattern;
            case 3 -> pattern = blinkerPattern;
            case 4 -> pattern = gliderPattern;
            default -> {
                System.out.println("Error, input should between 1-4");
                return;
            }
        }
        init(pattern, M, N);
    }

    public static void init(int[][] pattern, int M, int N) {
        if (0 == pattern.length || 0 == pattern[0].length || M <= 0 || N <= 0) {
            return;
        }
        grid = new int[M][N];
        int left = (M - pattern.length) / 2;
        int down = (N - pattern[0].length) / 2;
        for (int i = 0; i < pattern.length; i++) {
            System.arraycopy(pattern[i], 0, grid[left + i], down, pattern[0].length);
        }
        int generation = 0;
        displayGrid(grid, M, N);
        while (true) {
            System.out.printf("Generation: %d\n", generation);
            generation++;
            grid = nextGeneration(grid, M, N);
            displayGrid(grid, M, N);
            //generate the nextGeneration and display
            String input_str = input.nextLine();
        }
    }

    static void displayGrid(int[][] grid, int M, int N) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Function to print next generation 
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
        return future;
    }
} 