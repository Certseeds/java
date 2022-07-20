import annotation.Inject;

import java.lang.reflect.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerImpl implements Container {
    final Map<Class<?>, Class<?>> map = new ConcurrentHashMap<>();

    public <T> void register(Class<T> serviceType) {
        final boolean judgeNull = (serviceType == null);
        if (judgeNull) {
            throw new IllegalArgumentException();
        }
        final boolean judgeCan = (Modifier.isAbstract(serviceType.getModifiers())) ||
                (Modifier.isInterface(serviceType.getModifiers())) ||
                (serviceType.getDeclaredConstructors().length > 1);
        if (judgeCan) {
            throw new IllegalArgumentException();
        }

        //Class<T> temp = serviceType.newInstance();
        map.put(serviceType, serviceType);
    }

    public <T> void register(Class<T> serviceType, Class<? extends T> implementationType) {
        final boolean judgeNull = (serviceType == null) || (implementationType == null);
        if (judgeNull) {
            throw new IllegalArgumentException();
        }
        final boolean judgeserviceType = !(Modifier.isInterface(serviceType.getModifiers()) ||
                Modifier.isAbstract(serviceType.getModifiers()));
        final boolean judgeimPlementation = (Modifier.isAbstract(implementationType.getModifiers())) ||
                (Modifier.isInterface(implementationType.getModifiers())) ||
                (implementationType.getDeclaredConstructors().length > 1);
        if (judgeserviceType || judgeimPlementation) {
            throw new IllegalArgumentException();
        }
        map.put(serviceType, implementationType);
    }

    public <T> T resolve(Class<T> serviceType) {
        final boolean judgeNull = (serviceType == null);
        if (judgeNull) {
            throw new IllegalArgumentException();
        }
        final boolean judge2 = !map.containsKey(serviceType);
        if (judge2) {
            throw new ServiceNotFoundException();
        }


        T willreturn = null;
        try {
            T[] temp1 = (T[]) map.get(serviceType).getConstructors();
            Object[] objects_array = new Object[map.get(serviceType).getConstructors()[0].getParameters().length];
            for (Constructor<?> con : map.get(serviceType).getConstructors()) {
                int count = 0;
                for (Parameter p : con.getParameters()) {
                    objects_array[count] = resolve(p.getType());
                    count++;
                }
            }
            willreturn = (T) map.get(serviceType).getDeclaredConstructors()[0].newInstance(objects_array);
            Field[] fields = serviceType.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Inject.class)) {
                    field.set(willreturn, resolve(field.getType()));
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return willreturn;
    }
}
