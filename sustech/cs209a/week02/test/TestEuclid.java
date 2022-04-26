import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(3, gcd_rec(12, 9));
        Assertions.assertEquals(7, gcd_rec(14, 21));
        Assertions.assertEquals(11, gcd_rec(121, 132));
        Assertions.assertEquals(61, gcd_rec(122, 61));
        Assertions.assertEquals(13, gcd_rec(169, 13));
    }

    @Test
    public void Normal_test_iter_Euclid() {
        Assertions.assertEquals(3, gcd_iter(12, 9));
        Assertions.assertEquals(7, gcd_iter(14, 21));
        Assertions.assertEquals(11, gcd_iter(121, 132));
        Assertions.assertEquals(61, gcd_iter(122, 61));
        Assertions.assertEquals(13, gcd_iter(169, 13));
    }
}