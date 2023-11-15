package top.evalexp.tools.plugins.coders;


import top.evalexp.tools.plugins.enums.Algorithm;
import top.evalexp.tools.plugins.enums.Mode;

import java.lang.reflect.Method;
import java.util.Base64;

public class CodingDispatcher {
    public static byte[] Base64Decode(byte[] data) {
        return Base64.getDecoder().decode(new String(data));
    }

    public static byte[] Base64Encode(byte[] data) {
        return Base64.getEncoder().encode(data);
    }

    public static byte[] dispatch(Mode mode, Algorithm algorithm, byte[] input) {
        String dispatch_method_name = algorithm.name() + mode.name();
        try {
            Method dispatch_method = CodingDispatcher.class.getDeclaredMethod(dispatch_method_name, byte[].class);
            return (byte[]) dispatch_method.invoke(null, input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Could not dispatch with %s%s", algorithm.name(), mode.name()));
        }
    }
}
