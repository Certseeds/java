import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int dayNumber = Integer.parseInt(st.nextToken());
        long[] arrayDays = new long[dayNumber];
        long[] diffArray = new long[dayNumber + 1];

        int ordersNumber = Integer.parseInt(st.nextToken());
        long[][] OrdersArray = new long[ordersNumber][3];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < dayNumber; i++) {
            arrayDays[i] = Integer.parseInt(st.nextToken());
            //System.out.println(arrayDays[i]);
        }

        diffArray[0] = arrayDays[0];
        diffArray[dayNumber] = 0;
        for (int i = 1; i < dayNumber; i++) {
            diffArray[i] = arrayDays[i] - arrayDays[i - 1];
        }

        for (int i = 0; i < ordersNumber; i++) {
            st = new StringTokenizer(br.readLine());
            OrdersArray[i][0] = Integer.parseInt(st.nextToken());
            OrdersArray[i][1] = Integer.parseInt(st.nextToken());
            OrdersArray[i][2] = Integer.parseInt(st.nextToken());
        }
        //input finish
        int finalNumber = ordersNumber - 1;
        int middle = 0;
        int begin = 0;
        int temp = 0;
        //System.out.println(Judgement(diffArray.length,OrdersArray,middle,arrayDays) + ",");
        while (begin <= finalNumber) {
            middle = begin + (finalNumber - begin) / 2;
            if (Judgement(diffArray.length, OrdersArray, middle, arrayDays) == -1) {
                finalNumber = middle - 1;
            } else {
                begin = middle + 1;
            }
            temp = begin;
        }
        if (finalNumber == ordersNumber - 1) {
            System.out.print("0");
        } else {
            System.out.println("-1");
            System.out.print(temp + 1);
        }

    }

    public static int Judgement(int difflength, long [][]Order, int value, long[] realArray) {
        long[] diffArray2 = new long[difflength];
        for (int i = 0; i < difflength; i++) {
            diffArray2[i] = 0;
        }
        // begin finish
        for (int i = 0; i < value + 1; i++) {
            diffArray2[(int) (Order[i][1] - 1)] += Order[i][0];
            diffArray2[(int) Order[i][2]] -= Order[i][0];
        }

        for (int i = 1; i < difflength; i++) {
            diffArray2[i] = diffArray2[i] + diffArray2[i - 1];

        }
        for (int i = 0; i < difflength - 1; i++) {
            if (realArray[i] < diffArray2[i]) {
                return -1;
            }
        }
        return 1;
    }

}