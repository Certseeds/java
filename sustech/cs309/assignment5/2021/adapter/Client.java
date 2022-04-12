import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        List<StaffModel> list = new ArrayList<>();
        final FileOperateInterfaceV1 fileOperator1 = new FileOperate();
        final ManageStaffInterface manageStaff = new ManageStaff();
        final FileOperateInterfaceV2Impl fileOperator2 = new Adapter(fileOperator1, manageStaff);
        final Scanner input = new Scanner(System.in);
        System.out.println("Please select operatio+ \n1.readFile \n2.listFile \n3.writeFileByName \n4.writeFileByPeopleId \n5.addStaff \n6.removeStaff");
        int op = 0;
        do {
            try {
                op = input.nextInt();
                switch (op) {
                    case 1 -> list = fileOperator2.readAllStaff();
                    case 2 -> fileOperator2.listAllStaff(list);
                    case 3 -> fileOperator2.writeByName(list);
                    case 4 -> fileOperator2.writeByPeopleId(list);
                    case 5 -> fileOperator2.addNewStaff(list);
                    case 6 -> fileOperator2.removeStaffByMaxPeopleId(list);
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception:" + e);
                input.nextLine();
            }
        } while (op != 0);
        input.close();
    }
}
