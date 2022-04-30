/* Certseeds/week08
   Copyright (C) 2020  nanoseeds

   Certseeds/week08 is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as
   published by the Free Software Foundation, either version 3 of the
   License, or (at your option) any later version.

   Certseeds/week08 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.
   */
/**
 * @Github: https://github.com/Certseeds/CS209A_JAVA2/week08
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-04-12 19:19:53
 * @LastEditors : nanoseeds
 */
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
