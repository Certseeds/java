import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        final int number = cin.nextInt();
        cin.nextLine();
        for (int i = 0; i < number; i++) {
            final int list = cin.nextInt();
            final int row = cin.nextInt();
            final boolean judge = row + list > 2;
            Print(judge);
        }
        cin.close();
    }

    public static void Print(boolean judge) {
        final String temp = judge ? "Alice" : "Bob";
        System.out.println(temp);
    }
}