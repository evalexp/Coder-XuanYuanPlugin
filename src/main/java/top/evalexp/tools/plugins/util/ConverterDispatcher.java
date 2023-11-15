package top.evalexp.tools.plugins.util;

import top.evalexp.tools.plugins.coders.CodingDispatcher;
import top.evalexp.tools.plugins.enums.Format;

import java.lang.reflect.Method;

public class ConverterDispatcher {
    public static byte[] Raw_to_Hex(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(Long.toHexString(data[i]));
        }
        return stringBuilder.toString().getBytes();
    }

    public static byte[] Hex_to_Raw(byte[] data) {
        String hex_string = new String(data);
        if (hex_string.startsWith("0x")) hex_string = hex_string.substring(2);
        byte[] bytes = new byte[hex_string.length() / 2];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte) Integer.parseInt(hex_string.substring(i * 2, i * 2 + 2), 16);
        return bytes;
    }

    public static byte[] Raw_to_Base64(byte[] data) {
        return CodingDispatcher.Base64Encode(data);
    }

    public static byte[] Base64_to_Raw(byte[] data) {
        return CodingDispatcher.Base64Decode(data);
    }

    private static byte[] dispatch(String dispatch_method_name, byte[] data) {
        try {
            Method dispatch_method = ConverterDispatcher.class.getDeclaredMethod(dispatch_method_name, byte[].class);
            return (byte[]) dispatch_method.invoke(null, data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not convert data from " + dispatch_method_name.replace("_to_", " to ") + ".");
        }
    }

    public static byte[] dispatch(Format from, Format to, byte[] data) {
        if (from != Format.Raw)
            data = dispatch(from.name() + "_to_Raw", data);
        if (to != Format.Raw)
            data = dispatch("Raw_to_" + to.name(), data);
        return data;
    }
}
