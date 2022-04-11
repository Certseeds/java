import bean.Computer;
import bean.Staff;
import dao.ComputerDao;
import dao.DaoFactoryImpl;
import dao.StaffDao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("resources/resource.properties"));) {
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final var method = DaoFactoryImpl.class.getMethod("getSingleton", String.class);
        method.setAccessible(true);
        final var dfi = (DaoFactoryImpl) method.invoke(null, prop.getProperty("classname"));
        final var staffDao = dfi.createStaffDao();
        final var computerDao = dfi.createComputerDao();

        test(staffDao, computerDao);
    }

    public static void test(StaffDao staffDao, ComputerDao computerDao) {
        Scanner input = new Scanner(System.in);
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
