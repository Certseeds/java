import quick_read.input_reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        input_reader in = new input_reader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int u = 121;
        solve(in, out);
        out.close();
    }

    static void solve(input_reader in, PrintWriter out) {
        int n = in.nextInt();
        int peopleNumber = 0;
        int maxvolume = 0;
        int[] now = new int[n + 1];
        int[] max = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            now[i] = in.nextInt();
            max[i] = in.nextInt();
            peopleNumber += now[i];
            maxvolume = Math.max(maxvolume, max[i]);
        }
        int maxNumber = peopleNumber + maxvolume + 2;
        int[] temp = Arrays.copyOfRange(max, 1, n + 1);
        Arrays.sort(temp);
        int maxClass_room = 0;
        int count = 0;
        for (int i = n - 1; i >= 0; i--) {
            count += temp[i];
            maxClass_room++;
            if (count >= peopleNumber) {
                break;
            }
        }
        //System.out.println(maxClass_room + "?");
        int[][][] DP = new int[2][maxClass_room + 1][maxNumber + 1]; //
        DP[0][0][0] = 0;
        for (int i = 1; i < maxClass_room + 1; i++) {
            DP[0][i][0] = Integer.MIN_VALUE / 4;
        }
        for (int i = 1; i < maxNumber + 1; i++) {
            for (int j = 0; j < maxClass_room + 1; j++) {
                DP[0][j][i] = Integer.MIN_VALUE / 4;
            }
        }
        System.out.println(n + " " + maxNumber + " " + maxClass_room);
        System.out.println(peopleNumber + " " + maxNumber);
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= maxClass_room; j++) {
                for (int k = 0; k <= maxNumber; k++) {
                    // System.out.println(i + " " + j + " " + k);
                    if (j == 0 || k < max[i]) {
                        DP[1][j][k] = DP[0][j][k];
                    } else if (k >= max[i]) {
                        DP[1][j][k] = Math.max(
                                DP[0][j][k]
                                ,
                                DP[0][j - 1][k - max[i]] + now[i]
                        );
                    }
                }
            }
            for (int j = 0; j <= maxClass_room; j++) {
                if (maxNumber + 1 >= 0) {System.arraycopy(DP[1][j], 0, DP[0][j], 0, maxNumber + 1);}
            }
        }
        int output = 0;
        for (int i = peopleNumber; i <= maxNumber; i++) {
            output = Math.max(output, DP[1][maxClass_room][i]);
        }
        int willoutput = peopleNumber - output;
        out.println(willoutput);
    }


}