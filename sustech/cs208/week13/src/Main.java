import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        // int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] now = new int[n + 1];
        int[] max = new int[n + 1];
        int peopleNumber = 0;
        int maxClass_room = 0;
        for (int i = 1; i <= n; i++) {
            now[i] = input.nextInt();
            max[i] = input.nextInt();
            peopleNumber += now[i];
        }
        int[] temp_2 = Arrays.copyOfRange(max, 1, n + 1);
        Arrays.sort(temp_2);
        int count = 0;
        for (int i = n - 1; i >= 0; i--) {
            count += temp_2[i];
            maxClass_room++;
            if (count >= peopleNumber) {
                break;
            }
        }
        int m = maxClass_room;
        int[] out = new int[m];
        ArrayList<int[]> temp = new ArrayList<>();
        Combination(temp, m, arr, n, m, out, m);
        for (int[] ints : temp) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println(temp.size());
        int output = 0;
        for (int i = 1; i < temp.size(); i++) {
            int[] array1 = temp.get(i);
            int number1 = 0;
            int number2 = 0;
            for (int j = 0; j < m; j++) {
                number1 += max[array1[j]];
                number2 += now[array1[j]];
            }
            if (number1 >= peopleNumber && output <= number2) {
                output = number2;
                System.out.println(Arrays.toString(array1));
                for (int k = 0; k < m; k++) {
                    System.out.println(now[array1[k]] + " " + max[array1[k]]);
                }
                System.out.println(output + "?");
            }

        }
        System.out.println(peopleNumber - output);
    }

    public static ArrayList<int[]> Combination(ArrayList<int[]> temp, int max_class, int arr[], int nLen, int m, int out[], int outLen) {
        if (m == 0) {
            temp.add(Arrays.copyOfRange(out, 0, max_class));
            //System.out.println(Arrays.toString(out));
            return null;
        }

        for (int i = nLen; i >= m; --i)    //从后往前依次选定一个
        {
            out[m - 1] = arr[i - 1]; //选定一个后
            Combination(temp, max_class, arr, i - 1, m - 1, out, outLen); // 从前i-1个里面选取m-1个进行递归
        }
        return temp;
    }
}
