// SPDX-License-Identifier: AGPL-3.0-or-later
package week08;

public class grid_n_m_gene {


    private int[][] grid;
    private int N;
    private int M;
    private int generation;

    public grid_n_m_gene() {
        this.grid = new int[10][10];
        this.N = 10;
        this.M = 10;
        this.generation = 0;
    }

    public grid_n_m_gene(int n, int m, int generation) {
        this.grid = new int[n][m];
        this.N = n;
        this.M = m;
        this.generation = generation;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.grid[i][j] = 0;
            }
        }

    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getN() {
        return this.N;
    }

    public void setN(int n) {
        this.N = n;
    }

    public int getM() {
        return this.M;
    }

    public void setM(int m) {
        this.M = m;
    }

    public int getGeneration() {
        return this.generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
