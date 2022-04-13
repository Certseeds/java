import java.util.List;

public interface FileOperateInterfaceV2Impl extends FileOperateInterfaceV2 {

    void addNewStaff(List<StaffModel> list);

    void removeStaffByName(List<StaffModel> list);
}
