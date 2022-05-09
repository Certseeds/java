import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        int caltimes = 0;
        int[][] judge = new int[24][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (i != j && j != k && k != i) {
                        judge[caltimes][0] = i;
                        judge[caltimes][1] = j;
                        judge[caltimes][2] = k;
                        judge[caltimes][3] = 6 - i - j - k;
                        caltimes++;
                    }
                }
            }
        }
        //System.out.print(caltimes);
        for (int t = in.nextInt(); t > 0; t--) {
            int length = in.nextInt();
            int height = in.nextInt();
            int beginx = 0;
            int beginy = 0;
            int times = 0;
            String[][] space = new String[length][height];
            for (int i = 0; i < length; i++) {
                String temp = in.next();
                String[] temp2 = temp.split("");
                for (int j = 0; j < height; j++) {
                    space[i][j] = temp2[j];
                    if (space[i][j].equals("S")) {
                        beginx = i;
                        beginy = j;
                    }
                }
            }
            String temp3 = in.next();
            int lengthOfSe = temp3.length();
            int[] temp5 = new int[lengthOfSe];
            for (int i = 0; i < lengthOfSe; i++) {
                temp5[i] = ((int) temp3.charAt(i) - 48);
            }

            for (int i = 0; i < 24; i++) {
                int x = beginx;
                int y = beginy;
                for (int j = 0; j < lengthOfSe; j++) {
                    if (temp5[j] == judge[i][0]) {x++;} else if (temp5[j] == judge[i][1]) {
                        y++;
                    } else if (temp5[j] == judge[i][2]) {x--;} else if (temp5[j] == judge[i][3]) {y--;}
                    if (x < 0 || y < 0 || x >= length || y >= height) {break;} else if (space[x][y].equals("#")) {
                        break;
                    } else if (space[x][y].equals("E")) {
                        times++;
                        break;
                    }
                }
            }
            out.print(times);
            if (t != 1) {out.println();}
        }
        out.close();
    }
}
