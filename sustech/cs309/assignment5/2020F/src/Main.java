import java.util.Date;

public class Main {
    public static void main(String[] args) {
        final Customer C1 = new Customer("John");
        final Customer C2 = new Customer("Mary");
        final Customer C3 = new Customer("Manny");
        final Movie M1 = new Movie("Oz The Great and Powerful", Movie.NEW_RELEASE);
        final Movie M2 = new Movie("The Dark Knight", Movie.REGULAR);
        final Movie M3 = new Movie("Wreck-it Ralph", Movie.CHILDRENS);
        final Date start = new Date(2013, 7, 1);
        final Date end1 = new Date(2013, 7, 6);
        final Date end2 = new Date(2013, 7, 4);
        final Date end3 = new Date(2013, 7, 5);
        C1.addRental(new Rental(M1, new DataRange(start, end1)));
        C1.addRental(new Rental(M2, new DataRange(start, end2)));
        C1.addRental(new Rental(M3, new DataRange(start, end3)));
        System.out.println(C1.statement());
        System.out.println();

        C2.addRental(new Rental(M1, new DataRange(new Date(2013, 7, 11), new Date(2013, 7, 12))));
        C2.addRental(new Rental(M3, new DataRange(new Date(2013, 7, 11), new Date(2013, 7, 19))));
        System.out.println(C2.statement());
        System.out.println();
        C3.addRental(new Rental(M2, new DataRange(new Date(2013, 6, 1), new Date(2013, 6, 3))));
        C3.addRental(new Rental(M3, new DataRange(new Date(2013, 6, 1), new Date(2013, 6, 2))));
        System.out.println(C3.statement());
        System.out.println();
    }
}
