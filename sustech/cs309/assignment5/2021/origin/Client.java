import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        List<StaffModel> list = new ArrayList<>();
        final FileOperateInterfaceV1 fileOperator = new FileOperate();
        try (final Scanner input = new Scanner(System.in)) {
            System.out.println("Please select operation: 1.readFile 2.listFile 3. writeFile:");
            int op = 0;
            do {
                try {
                    op = input.nextInt();
                    switch (op) {
                        case 1 -> list = fileOperator.readStaffFile();
                        case 2 -> fileOperator.printStaffFile(list);
                        case 3 -> fileOperator.writeStaffFile(list);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Exception:" + e);
                    input.nextLine();
                }
            } while (op != 0);
        }
    }
}
