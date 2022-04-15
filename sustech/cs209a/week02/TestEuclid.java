import org.junit.jupiter.api.Test;

public class TestEuclid {
    // recursive implementation
    public static int gcd_rec(int p, int q) {
        if (q == 0) {
            return p;
        } else {
            return gcd_rec(q, p % q);
        }
    }

    // non-recursive implementation
    public static int gcd_iter(int p, int q) {
        while (q != 0) {
            int temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }

    @Test
    public void testGcd() {
        gcd_iter(114, 514);
        gcd_rec(114, 514);
    }
}