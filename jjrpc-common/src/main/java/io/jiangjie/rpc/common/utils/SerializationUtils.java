package io.jiangjie.rpc.common.utils;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.stream.IntStream;

public class SerializationUtils {
    private static final String PADDING_STRING = "0";


    /**
     * 约定序列化类型最大长度为16
     */
    public static final int MAX_SERIALIZATION_TYPE_COUNR = 16;

    public static String paddingString(String str) {
        str = transNullToEmpty(str);
        if (str.length() >= MAX_SERIALIZATION_TYPE_COUNR)
            return str;
        int paddingCount = MAX_SERIALIZATION_TYPE_COUNR - str.length();
        StringBuffer paddingString = new StringBuffer(str);
        IntStream.range(0, paddingCount).forEach(i -> paddingString.append(PADDING_STRING));
        return paddingString.toString();
    }

    /**
     * 字符串去0操作
     *
     * @param str
     * @return
     */
    public static String subString(String str) {
        str = transNullToEmpty(str);
        return str.replace(PADDING_STRING, "");
    }

    public static String transNullToEmpty(String str) {
        return str == null ? "" : str;
    }
}
