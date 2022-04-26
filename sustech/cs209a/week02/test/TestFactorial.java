import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFactorial {

    // return n!
    // precondition: n >= 0 and n <= 20
    public static long factorial(long n) {
        if (n < 0) {
            throw new RuntimeException("Underflow error in factorial");
        } else if (n > 20) {
            throw new RuntimeException("Overflow error in factorial");
        } else if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    @Test
    public void factTest1() {
        System.out.println(factorial(20));
    }

    @Test
    public void factTest2() {
        Assertions.assertThrows(RuntimeException.class, () -> factorial(-1));
        Assertions.assertThrows(RuntimeException.class, () -> factorial(21));
    }


}