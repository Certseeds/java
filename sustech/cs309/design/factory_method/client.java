import java.util.*;

public class client {
    public static void main(String[] args) {
        ITStaffFactoryInterface itsfi;
        final Scanner input = new Scanner(System.in);
        int op = -1;

        final List<ITStaff> staffList = new ArrayList<>();

        System.out.println("Please input an operation number:"
                + "\n1: add an IT manager"
                + "\n2: add an Developer"
                + "\n3: add an Tester"
                + "\n4: add an Art Design"
                + "\n5: print all staff by salary order"
                + "\n6: print all staff by working order"
                + "\n0: Stop\n");
        do {
            try {
                op = input.nextInt();

                switch (op) {
                    case 1 -> {
                        itsfi = new ITManagerFactory();
                        staffList.add(itsfi.createITStaff());
                    }
                    case 2 -> {
                        itsfi = new DeveloperFactory();
                        staffList.add(itsfi.createITStaff());
                    }
                    case 3 -> {
                        itsfi = new TesterFactory();
                        staffList.add(itsfi.createITStaff());
                    }
                    case 4 -> {
                        itsfi = new ArtDesignerFactory();
                        staffList.add(itsfi.createITStaff());
                    }
                    case 5 -> {
                        System.out.println("All information:");
                        staffList.stream().sorted(Comparator.comparing(ITStaff::getSalary)).forEach(System.out::println);
                    }
                    case 6 -> {
                        System.out.println("All name:");
                        staffList.stream().sorted(Comparator.comparing(ITStaff::working)).forEach(System.out::println);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(e.toString());
                input.nextLine();
            }

        } while (op != 0);
        input.close();
    }

}
