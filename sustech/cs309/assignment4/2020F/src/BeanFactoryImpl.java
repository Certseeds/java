import annotations.Inject;
import annotations.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Byte.parseByte;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Short.parseShort;
import static java.util.Arrays.stream;

public class BeanFactoryImpl implements BeanFactory {
    Map<Class<?>, Class<?>> map = new HashMap<>();
    Map<String, String> valueMap = new HashMap<>();

    @Override
    public void loadInjectProperties(File file) {
        var tempMap = new HashMap<String, String>();
        loadSomething(file, tempMap);
        var array = tempMap.keySet().toArray(new String[0]);
        for (var parameter : array) {
            try {
                map.put(Class.forName(parameter), Class.forName(tempMap.get(parameter)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadValueProperties(File file) {
        loadSomething(file, valueMap);
    }

    private void loadSomething(File file, Map<String, String> mapped) {
        var properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (var key : properties.keySet()) {
            final String leftSide = (String) key;
            System.out.printf("The capital of %s is %s .\n", leftSide, properties.getProperty(leftSide));
            mapped.put(leftSide, (String) properties.get(leftSide));
        }
    }

    @Override
    public <T> T createInstance(Class<T> clazz) {
        T will_return = null;
        try {
            will_return = createInstanceWarp(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return will_return;
    }

    private <T> T createInstanceWarp(Class<T> clazz) throws Exception {
        T will_return = null;
        final var instanceClass = (Class<? extends T>) map.getOrDefault(clazz, clazz);
        // deal with Inject on Constructorsar
        final var constructorList = stream(instanceClass.getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class)).toList();
        if (constructorList.isEmpty()) {
            will_return = instanceClass.getDeclaredConstructor().newInstance();
        } else {
            final var constructor = constructorList.get(0);
            System.out.println(constructor);
            final var paramArray = new ArrayList<>();
            for (var parameter : constructor.getParameters()) {
                System.out.println("pamam " + parameter);
                if (parameter.isAnnotationPresent(Value.class)) {
                    System.out.println("Value.class");
                    if (parameter.getType().isArray()) {
                        System.out.println("Is Array");
                        paramArray.add(createInstanceArray(parameter.getType(), parameter.getAnnotation(Value.class)));
                    } else {
                        paramArray.add(createInstanceObject(parameter.getType(), parameter.getAnnotation(Value.class)));
                    }
                } else {
                    System.out.println("No Value.class");
                    paramArray.add(createInstance(parameter.getType()));
                }
            }
            will_return = (T) constructor.newInstance(paramArray.toArray());
        }
        for (var field : instanceClass.getDeclaredFields()) {
            System.out.println("field " + field);
            field.setAccessible(true);
            if (field.isAnnotationPresent(Inject.class)) {
                System.out.println("Field Inject.class");
                field.set(will_return, createInstance(field.getType()));
            } else if (field.isAnnotationPresent(Value.class)) {
                System.out.println("Field Value.class");
                if (field.getType().isArray()) {
                    System.out.println("is Arrray");
                    field.set(will_return, createInstanceArray(field.getType(), field.getAnnotation(Value.class)));
                } else {
                    field.set(will_return, createInstanceObject(field.getType(), field.getAnnotation(Value.class)));
                }
            }
        }
        return will_return;
    }

    private <T> T createInstanceArray(Class<T> clazz, Value value) {
        final var elementType = clazz.getComponentType();
        final var valueStrArray = valueMap.get(value.value()).split(value.delimiter());
        if (elementType == Character.class || elementType == char.class) {
            var wrapper = stream(valueStrArray)
                    .map(parameter -> parameter.charAt(0))
                    .toArray(Character[]::new);
            if (elementType == char.class) {
                var will_return = new char[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == Double.class || elementType == double.class) {
            var wrapper = stream(valueStrArray)
                    .map(Double::parseDouble)
                    .toArray(Double[]::new);
            if (elementType == double.class) {
                var will_return = new double[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == Float.class || elementType == float.class) {
            final var wrapper = stream(valueStrArray)
                    .map(Float::parseFloat)
                    .toArray(Float[]::new);
            if (elementType == float.class) {
                final var will_return = new float[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == Boolean.class || elementType == boolean.class) {
            final var wrapper = stream(valueStrArray)
                    .map(Boolean::parseBoolean)
                    .toArray(Boolean[]::new);
            if (elementType == boolean.class) {
                final var will_return = new boolean[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == Byte.class || elementType == byte.class) {
            final var wrapper = stream(valueStrArray)
                    .map(Byte::parseByte)
                    .toArray(Byte[]::new);
            if (elementType == byte.class) {
                final var will_return = new byte[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == Short.class || elementType == short.class) {
            final var wrapper = stream(valueStrArray)
                    .map(Short::parseShort)
                    .toArray(Short[]::new);
            if (elementType == short.class) {
                final var will_return = new short[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == Integer.class || elementType == int.class) {
            final var wrapper = stream(valueStrArray)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
            if (elementType == int.class) {
                final var will_return = new int[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;

        } else if (elementType == Long.class || elementType == long.class) {
            final var wrapper = stream(valueStrArray).map(Long::parseLong).toArray(Long[]::new);
            if (elementType == long.class) {
                final var will_return = new long[wrapper.length];
                for (var i = 0; i < wrapper.length; i++) {
                    will_return[i] = wrapper[i];
                }
                return (T) will_return;
            }
            return (T) wrapper;
        } else if (elementType == String.class) {
            return (T) valueStrArray;
        }
        return (T) new Object[0];
    }


    public <T> T createInstanceObject(Class<T> clazz, Value value) {
        T will_return = null;
        final var valueStr = valueMap.get(value.value());
        if (clazz == Character.class || clazz == char.class) {
            final var c = valueStr.charAt(0);
            will_return = (T) (Character) c;
        } else if (clazz == Double.class || clazz == double.class) {
            will_return = (T) (Double) parseDouble(valueStr);
        } else if (clazz == Float.class || clazz == float.class) {
            will_return = (T) (Float) parseFloat(valueStr);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            will_return = (T) (Boolean) parseBoolean(valueStr);
        } else if (clazz == Byte.class || clazz == byte.class) {
            will_return = (T) (Byte) parseByte(valueStr);
        } else if (clazz == Short.class || clazz == short.class) {
            will_return = (T) (Short) parseShort(valueStr);
        } else if (clazz == Integer.class || clazz == int.class) {
            will_return = (T) (Integer) parseInt(valueStr);
        } else if (clazz == Long.class || clazz == long.class) {
            will_return = (T) (Long) parseLong(valueStr);
        } else if (clazz == String.class) {
            will_return = (T) valueStr;
        }
        return will_return;
    }
}
