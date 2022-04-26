import org.junit.jupiter.api.Test;

public class TestTowersOfHanoi {
    // print out instructions for moving n discs to
    // the left (if left is true) or right (if left is false)
    public static void moves(int n, boolean left) {
        if (n == 0) return;
        moves(n - 1, !left);
        if (left) {
            System.out.println(n + " left");
        } else {
            System.out.println(n + " right");
        }
        moves(n - 1, !left);
    }

    @Test
    public void towersOfHanoi() {
        moves(9, true);
    }
}
