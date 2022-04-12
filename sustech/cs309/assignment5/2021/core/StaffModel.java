import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffModel {
    private String name;
    private String title;
    private String email;
    private String room;
    private String link;
}
