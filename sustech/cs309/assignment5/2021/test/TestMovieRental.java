import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;


public class TestMovieRental {
    private static Customer C1;
    private static Customer C2;
    private static Customer C3;

    private static Movie M1;
    private static Movie M2;
    private static Movie M3;

    @BeforeAll
    public static void setUp() {
        C1 = new Customer("John");
        C2 = new Customer("Mary");
        C3 = new Customer("Manny");
        M1 = new Movie("Oz The Great and Powerful", Movie.NEW_RELEASE);
        M2 = new Movie("The Dark Knight", Movie.REGULAR);
        M3 = new Movie("Wreck-it Ralph", Movie.CHILDRENS);
    }

    @Test
    public void test1() {
        final Date start = new Date(2013, java.util.Calendar.AUGUST, 1);
        final Date end1 = new Date(2013, java.util.Calendar.AUGUST, 6);
        final Date end2 = new Date(2013, java.util.Calendar.AUGUST, 4);
        final Date end3 = new Date(2013, java.util.Calendar.AUGUST, 5);

        C1.addRental(new Rental(M1, new DataRange(start, end1)));
        C1.addRental(new Rental(M2, new DataRange(start, end2)));
        C1.addRental(new Rental(M3, new DataRange(start, end3)));
        final StringBuilder ans1 = new StringBuilder("Rental Record for John\n");
        ans1.append("\tOz The Great and Powerful\t15.0\n");
        ans1.append("\tThe Dark Knight\t3.5\n");
        ans1.append("\tWreck-it Ralph\t3.0\n");
        ans1.append("Amount owed is 21.5\n");
        ans1.append("You earned 4 frequent renter points");
        final String ans = ans1.toString();
        System.out.println(ans + " " + C1.statement());
        Assertions.assertEquals(ans, C1.statement());
    }

    @Test
    public void test2() {
        final Date start = new Date(2013, java.util.Calendar.AUGUST, 11);
        final Date end1 = new Date(2013, java.util.Calendar.AUGUST, 12);
        final Date end2 = new Date(2013, java.util.Calendar.AUGUST, 19);
        C2.addRental(new Rental(M1, new DataRange(start, end1)));
        C2.addRental(new Rental(M3, new DataRange(start, end2)));
        final StringBuilder ans1 = new StringBuilder("Rental Record for Mary\n");
        ans1.append("\tOz The Great and Powerful\t3.0\n");
        ans1.append("\tWreck-it Ralph\t9.0\n");
        ans1.append("Amount owed is 12.0\n");
        ans1.append("You earned 2 frequent renter points");
        final String ans = ans1.toString();
        Assertions.assertEquals(ans, C2.statement());
    }

    @Test
    public void test3() {
        final Date start = new Date(2013, java.util.Calendar.JULY, 1);
        final Date end1 = new Date(2013, java.util.Calendar.JULY, 3);
        final Date end2 = new Date(2013, java.util.Calendar.JULY, 2);
        C3.addRental(new Rental(M2, new DataRange(start, end1)));
        C3.addRental(new Rental(M3, new DataRange(start, end2)));
        final StringBuilder ans1 = new StringBuilder("Rental Record for Manny\n");
        ans1.append("\tThe Dark Knight\t2.0\n");
        ans1.append("\tWreck-it Ralph\t1.5\n");
        ans1.append("Amount owed is 3.5\n");
        ans1.append("You earned 2 frequent renter points");
        final String ans = ans1.toString();
        Assertions.assertEquals(ans, C3.statement());
    }
}
