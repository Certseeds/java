import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {

    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int times = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < times; i++) {
            st = new StringTokenizer(br.readLine());
            int lengthOfIndex = Integer.parseInt(st.nextToken());
            int DemoAppear = Integer.parseInt(st.nextToken());
            int[] arrayReal = new int[lengthOfIndex];
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < lengthOfIndex; j++) {
                arrayReal[j] = Integer.parseInt(st.nextToken());
            }
            PrintDeemo(TwoFInd(arrayReal, DemoAppear), i, times);
        }

    }

    public static void PrintDeemo(boolean judge, int i, int times) {
        String finalPrint = judge ? "YES" : "NO";

        if (i + 1 == times) {
            System.out.print(finalPrint);
        } else {
            System.out.println(finalPrint);
        }
    }

    public static boolean TwoFInd(int[] array, int x) {
        int start = 0;
        int end = array.length - 1;
        int mid = 0;
        if (x > array[array.length - 1] || x < array[0]) {
            return false;
        }
        while (start <= end) {
            mid = start + ((end - start) / 2);
            if (array[mid] > x) {
                end = mid - 1;
            } else if (array[mid] < x) {
                start = mid + 1;
            } else if (array[mid] == x) {
                return true;
            }
        }
        return false;
    }

}