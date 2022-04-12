import java.util.List;

public interface FileOperateInterfaceV2Impl extends FileOperateInterfaceV2 {
    void writeByPeopleId(List<StaffModel> list);

    void addNewStaff(List<StaffModel> list);

    void removeStaffByMaxPeopleId(List<StaffModel> list);
}
