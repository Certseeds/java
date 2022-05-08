import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int times = cin.nextInt();
        cin.nextLine();

        for (int i = 0; i < times; i++) {
            String indexOutPairs = cin.nextLine();
            int lengthOfWord = indexOutPairs.length();
            int[] appear = new int[26];
            int realAppear = 0;
            for (int j = 0; j < 26; j++) {
                appear[j] = 0;
            }
            int[] indexInPairs = new int[lengthOfWord];

            for (int j = 0; j < lengthOfWord; j++) {
                char temp = indexOutPairs.charAt(j);
                indexInPairs[j] = ((int) temp) - 96;
            }
            for (int j = 0; j < lengthOfWord; j++) {
                switch (indexInPairs[j]) {
                    case 1:
                    case 5:
                    case 9:
                    case 15:
                    case 21:
                    case 23:
                    case 25: {
                        indexInPairs[j] = 0;
                    }
                    break;
                    case 2: {appear[1] = 1;}
                    break;
                    case 3: {appear[2] = 1;}
                    break;
                    case 4: {appear[3] = 1;}
                    break;
                    case 6: {appear[5] = 1;}
                    break;
                    case 7: {appear[6] = 1;}
                    break;
                    case 8: {appear[7] = 1;}
                    break;
                    case 10: {appear[9] = 1;}
                    break;
                    case 11: {appear[10] = 1;}
                    break;
                    case 12: {appear[11] = 1;}
                    break;
                    case 13: {appear[12] = 1;}
                    break;
                    case 14: {appear[13] = 1;}
                    break;
                    case 16: {appear[15] = 1;}
                    break;
                    case 17: {appear[16] = 1;}
                    break;
                    case 18: {appear[17] = 1;}
                    break;
                    case 19: {appear[18] = 1;}
                    break;
                    case 20: {appear[19] = 1;}
                    break;
                    case 22: {appear[21] = 1;}
                    break;
                    case 24: {appear[23] = 1;}
                    break;
                    case 26: {appear[25] = 1;}
                    break;
                }
            }
            for (int j = 0; j < 26; j++) {
                if (appear[j] == 1) {
                    realAppear++;
                }

            }
            int calcalusfinal = 0;
            int temp3 = (int) Math.pow(2, realAppear);
            int[][] doubleIndex = new int[realAppear][realAppear];
            int[][] doubleIndexsym = new int[realAppear][realAppear];

            for (int k = 0; k < realAppear; k++) {
                for (int j = 0; j < realAppear; j++) {
                    doubleIndex[j][k] = 0;
                }

            }

            for (int k = 0; k < realAppear; k++) {
                for (int j = 0; j < realAppear; j++) {
                    doubleIndexsym[j][k] = 1;
                }

            }
            int[] numberList = new int[realAppear];

            int temp5 = 0;
            ArrayList<Integer> tempCal = new ArrayList<>();
            tempCal.add(-100);
            for (int j = 0; j < lengthOfWord; j++) {

                if (!tempCal.contains(indexInPairs[j]) && indexInPairs[j] != 0) {
                    numberList[temp5] = indexInPairs[j];
                    temp5++;
                    tempCal.add(indexInPairs[j]);
                }
                if (temp5 == realAppear) {break;}
            }
            Arrays.sort(numberList);

            for (int j = 0; j < realAppear; j++) {
                for (int k = 0; k < lengthOfWord; k++) {
                    if (numberList[j] == indexInPairs[k]) {
                        indexInPairs[k] = j + 1;
                    }
                }
            }

            for (int j = 0; j < lengthOfWord - 1; j++) {
                if ((indexInPairs[j] != 0) && (indexInPairs[j + 1] != 0)) {
                    doubleIndex[indexInPairs[j] - 1][indexInPairs[j + 1] - 1] += 1;
                }
            }

            int calcalus = 0;
            for (int j = 0; j < temp3; j++) {
                int tempNumber1 = temp3 - j;
                StringBuilder tempString3 = new StringBuilder(tentoTwo(tempNumber1));
                int tempNumber2 = tempString3.length();
                for (int k = 0; k < realAppear - tempNumber2 + 1; k++) {
                    tempString3.insert(0, "0");
                }
                for (int k = 0; k < realAppear; k++) {
                    if ((int) tempString3.charAt(k) - 48 == 0) {
                        for (int m = 0; m < realAppear; m++) {
                            if (k != m) {
                                calcalus += doubleIndex[k][m] * doubleIndexsym[k][m];
                                calcalus += doubleIndex[m][k] * doubleIndexsym[m][k];
                                doubleIndexsym[k][m] *= -1;
                                doubleIndexsym[m][k] *= -1;
                            }
                        }
                    }
                }
                calcalusfinal = Math.max(calcalusfinal, calcalus);

            }
            System.out.println(calcalusfinal);
        }
    }
    public static String tentoTwo(int a) {
        return Integer.toBinaryString(a);
    }
}