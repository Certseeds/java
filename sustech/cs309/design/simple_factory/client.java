import java.util.*;

public class client {
    public static void main(String[] args) {
        final var itsf = new ITStaffFactory();
        Scanner input = new Scanner(System.in);
        int op = -1;

        List<ITStaff> staffList = new ArrayList<>();

        System.out.println("Please input an operation number:"
                + "\n1: add an IT manager"
                + "\n2: add an Developer"
                + "\n3: add an Tester"
                + "\n4: print all staff by salary order"
                + "\n5: print all staff by working order"
                + "\n0: Stop\n");
        do {
            try {
                op = input.nextInt();

                switch (op) {
                    case 1 -> staffList.add(itsf.createITStaff(ITStaffEnum.ITManager));
                    case 2 -> staffList.add(itsf.createITStaff(ITStaffEnum.Developer));
                    case 3 -> staffList.add(itsf.createITStaff(ITStaffEnum.Tester));
                    case 4 -> {
                        System.out.println("All information:");
                        staffList.stream().sorted(Comparator.comparing(ITStaff::getSalary)).forEach(System.out::println);
                    }
                    case 5 -> {
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
