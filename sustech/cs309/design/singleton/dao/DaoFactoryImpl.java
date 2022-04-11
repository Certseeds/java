package dao;

import java.lang.reflect.Method;

public class DaoFactoryImpl extends Singleton {
    private volatile static DaoFactoryImpl singleton;
    static String name;

    public DaoFactoryImpl() {
    }

    public static Singleton getSingleton(String nameofClass) {
        if (singleton == null) {
            synchronized (DaoFactoryImpl.class) {
                if (singleton == null) {
                    try {
                        name = nameofClass;
                        singleton = DaoFactoryImpl.class.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return singleton;
    }

    public ComputerDao createComputerDao() throws Exception {
        final Class<?> clz = Class.forName(String.format("%s%s%s", "dao.", name, "ComputerDao"));
        final var method = clz.getMethod("getSingleton");
        method.setAccessible(true);
        return (ComputerDao) method.invoke(null);
    }

    public StaffDao createStaffDao() throws Exception {
        final Class<?> clz = Class.forName(String.format("%s%s%s", "dao.", name, "StaffDao"));
        final Method method = clz.getMethod("getSingleton");
        method.setAccessible(true);
        return (StaffDao) method.invoke(null);
    }

    private static class MysqlFactory extends DaoFactoryImpl {

        public MysqlFactory() {
            super();
        }

        @Override
        public ComputerDao createComputerDao() {
            return (ComputerDao) MysqlComputerDao.getSingleton();
        }

        @Override
        public StaffDao createStaffDao() {
            return (StaffDao) MysqlStaffDao.getSingleton();
        }
    }

    private static class SqlServerFactory extends DaoFactoryImpl {
        public SqlServerFactory() {
            super();
        }

        @Override
        public ComputerDao createComputerDao() {
            return (ComputerDao) SqlServerComputerDao.getSingleton();
        }

        @Override
        public StaffDao createStaffDao() {
            return (StaffDao) SqlServerStaffDao.getSingleton();
        }
    }

}

