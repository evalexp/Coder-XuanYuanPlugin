package top.evalexp.tools.plugins.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnumUtil {
    public static  <V extends Enum<V>> Map<String, V> convertToPluginEnumerates(Enum<V>[] array) {
        HashMap<String, V> enumerates = new HashMap<>();
        for (Enum<V> v : array) {
            enumerates.put(v.name(), (V) v);
        }
        return enumerates;
    }
}
