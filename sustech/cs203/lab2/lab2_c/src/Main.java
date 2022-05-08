import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws NumberFormatException, IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int times = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < times; i++) {
            st = new StringTokenizer(br.readLine());
            final int lengthOfIndex = Integer.parseInt(st.nextToken());
            final int[] menList = new int[lengthOfIndex];
            final int lengthOfSword = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < lengthOfIndex; j++) {
                menList[j] = Integer.parseInt(st.nextToken());
            }
            int sword = 0;
            for (int j = 0; j < lengthOfIndex; j++) {
                final int lengNeeded = lengthOfSword - menList[j];
                for (int begin = 0, middle = 0, finalNumber = lengthOfIndex - 1; begin <= finalNumber; ) {
                    middle = begin + (finalNumber - begin) / 2;
                    if (menList[middle] > lengNeeded) {
                        finalNumber = middle - 1;
                    } else if (menList[middle] < lengNeeded) {
                        begin = middle + 1;
                    } else if (menList[middle] == lengNeeded) {
                        sword = sword + 1;
                        break;
                    }
                }
            }
            sword = sword / 2;
            if (i + 1 == times) {
                System.out.print(sword);
            } else {
                System.out.println(sword);
            }
        }

    }


}
