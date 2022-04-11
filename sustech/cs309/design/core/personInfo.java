import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class personInfo {
    private static int number = 1;
    private final String userID;
    private final String name;
    private final String password;
    private int StartingSalary;

    public personInfo() {
        userID = String.valueOf(10000 + number);
        number++;
        name = "";
        password = "";
    }

    public personInfo(String name, String password) {
        this.userID = String.valueOf(10000 + number);
        this.name = userID + " " + name;
        this.password = password;
        number++;
    }
}
