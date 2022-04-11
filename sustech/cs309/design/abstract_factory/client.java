import bean.Computer;
import bean.Staff;
import dao.ComputerDao;
import dao.MysqlFactory;
import dao.StaffDao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        final var daoFactory = new MysqlFactory();
        final StaffDao staffDao = daoFactory.createStaffDao();
        final ComputerDao computerDao = daoFactory.createComputerDao();

        test(staffDao, computerDao);
    }

    public static void test(StaffDao staffDao, ComputerDao computerDao) {
        final Scanner input = new Scanner(System.in);
        int op = -1;
        do {
            try {
                op = input.nextInt();
                switch (op) {
                    case 1 -> staffDao.insertStaff(new Staff());
                    case 2 -> staffDao.updateStaff(1);
                    case 3 -> staffDao.deleteStaff(1);
                    case 4 -> computerDao.insertComputer(new Computer(1));
                    case 5 -> computerDao.updateComputer(1);
                    case 6 -> computerDao.deleteComputer(1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception " + e);
            }
        } while (op != 0);
    }
}
