import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        long times = cin.nextLong();
        for (long i = 0; i < times; i++) {
            final long inputNumber = cin.nextLong();
            final long temp = inputNumber / 19;
            final long temp2 = inputNumber - (temp) * 19;
            final long middle = (long) Math.pow(3, 19);
            final long cal = (long) (Math.pow(10, 9) + 7);
            final long moduleNumber = middle % cal;
            long numberLongheFor = 1;
            if (temp != 0) {
                for (long j = 0; j < temp; j++) {
                    numberLongheFor = (numberLongheFor * moduleNumber) % cal;
                }
                for (long j = 0; j < temp2; j++) {
                    numberLongheFor = numberLongheFor * 3;
                    if (numberLongheFor > cal) {
                        numberLongheFor = numberLongheFor % cal;
                    }
                }
            } else {
                long numberSmall = 1;
                for (long j = 0; j < temp2; j++) {
                    numberSmall = numberSmall * 3;
                }
                numberLongheFor = numberSmall;
            }
            int number = (int) (numberLongheFor);
            System.out.println(number - 1);


        }


    }
}