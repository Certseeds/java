import quick_read.input_reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

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
        int[] seats = new int[n + 1];
        int count = 0;
        ArrayList<Integer> nones = new ArrayList<>();
        seats[0] = -2;
        for (int i = 1; i <= n; i++) {
            seats[i] = in.nextInt();
            if (seats[i] == -1) {
                count++;
                nones.add(i);
            }
        }
        if (count == 0) {
            out.println(judgement(seats));
        } else if (count == 1) {
            if (n == 1) {
                out.println(0);
            } else if (n == 2) {
                out.println(1);
            } else if (n >= 3) {
                int willoutput = 0;
                for (int i = 1; i <= 100; i++) {
                    seats[nones.get(0)] = i;
                    willoutput += judgement(seats);
                }
                out.println(willoutput);
            }
        } else if (count == 2) {
            if (n == 2) {
                out.println(100);
            } else {
                int willoutput = 0;
                seats[nones.get(0)] = 1;
                seats[nones.get(1)] = 1;
                for (int i = 1; i <= 100; i++) {
                    seats[nones.get(0)] = i;
                    for (int j = 1; j <= 100; j++) {
                        seats[nones.get(1)] = j;
                        willoutput += judgement(seats);
                    }
                }
                out.println(willoutput);
            }

        } else {
            out.println("What the f**k");
        }
    }

    public static long judgement(int[] seats) {
        int templength = seats.length - 1;
        if (templength == 1) {
            return 0;
        } else if (templength == 2) {
            return (seats[1] == seats[2]) ? 1 : 0;
        } else {
            boolean willreturn = (seats[1] <= seats[2]);
            for (int i = 2; i <= templength - 1; i++) {
                willreturn = willreturn && (seats[i] <= Math.max(seats[i - 1], seats[i + 1]));
            }
            willreturn = willreturn && (seats[templength] <= seats[templength - 1]);
            return willreturn ? 1 : 0;
        }
    }

}