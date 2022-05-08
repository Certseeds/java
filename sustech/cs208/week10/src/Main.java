import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int[] array1 = new int[101];
        final int[] array2 = new int[101];
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            count += 1;
            array2[i] = count;
            count += 1;
            array1[i] = count;
        }
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        //答案应该是101

        System.out.println(getmin(array1,array2,1,100,1,100));
    }

    public static int getmin(int[] a, int[] b, int a_min, int a_max, int b_min, int b_max) {
        if (a_min == a_max) {
            return a[a_min];
        } else if (b_min == b_max){
            return b[b_min];
        }else {
            int a_mid = (a_max - a_min) / 2 + a_min;
            int b_mid = (b_max - b_min) / 2 + b_min;
            if (a[a_mid] > b[b_mid]) {
                a_max = a_mid;
                b_min = b_mid+1;
            } else if (a[a_mid] < b[b_mid]) {
                b_max = b_mid;
                a_min = a_mid+1;
            } else {
                return a[a_mid];
            }
            return getmin(a, b, a_min, a_max, b_min, b_max);
        }
    }
}
