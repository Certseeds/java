package dao;

import bean.Computer;

public class SqlServerComputerDao extends Singleton implements ComputerDao {
    private volatile static SqlServerComputerDao singleton;

    private SqlServerComputerDao() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (SqlServerComputerDao.class) {
                if (singleton == null) {
                    try {
                        singleton = SqlServerComputerDao.class.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {

                    }
                }
            }
        }
        return singleton;
    }

    @Override
    public int insertComputer(Computer computer) {
        if (computer == null) {
            System.out.println("computer is null");
            return 0;
        } else {
            System.out.println("insert computer into SqlServer database successfully");
            return 1;
        }
    }

    @Override
    public int updateComputer(int id) {
        System.out.println("update computer in SqlServer database successfully");
        return 1;
    }

    @Override
    public int deleteComputer(int id) {
        System.out.println("delete computer in SqlServer database successfully");
        return 1;
    }
}
