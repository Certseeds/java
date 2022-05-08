import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        final int times = cin.nextInt();
        cin.nextLine();
        for (int i = 0; i < times; i++) {
            final int lengthOfIndex = cin.nextInt();
            cin.nextLine();
            final int[] IndexIntheCir = new int[lengthOfIndex];
            for (int j = 0; j < lengthOfIndex; j++) {
                IndexIntheCir[j] = cin.nextInt();
                //cin.nextLine();
            }
            final int a1 = IndexIntheCir[0];
            final int a2 = IndexIntheCir[1];
            int Max = (a1 - a2) >= 0 ? a1 : a2;
            int MaxDiff = a1 - a2;
            int Maxtemp = 0;
            for (int j = 2; j < lengthOfIndex; j++) {
                Maxtemp = Max - IndexIntheCir[j];
                if (Maxtemp < 0) {
                    Max = IndexIntheCir[j];
                }
                if (Maxtemp > MaxDiff) {
                    MaxDiff = Maxtemp;
                }
            }
            System.out.println(MaxDiff);
        }
    }
}
