package cn.com.xbed.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class Base64Util {

    private static Logger logger = LoggerFactory.getLogger(Base64Util.class);

    /**
     * Base64编码
     *
     * @param fileName 文件名
     * @return
     */
    public static String encodeBase64(String fileName) {
        InputStream in;
        byte[] data;
        Object retObj = null;
        try {
            in = new FileInputStream(fileName);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            Class<?> clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod = clazz.getMethod("encode", byte[].class);
            mainMethod.setAccessible(true);
            retObj = mainMethod.invoke(null, new Object[]{data});

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return retObj == null ? "" : retObj.toString();
    }

    /**
     * 将字节流转换成Base64字符串
     *
     * @param bytes
     * @return
     */
    public static String encodeStr(byte[] bytes) {
        Base64 base64 = new Base64();
        bytes = base64.encode(bytes);
        String str = new String(bytes);
        return str;
    }

    public static void main(String[] args) {
        String b = encodeBase64("E:\\yuan\\人脸识别\\1475045089256__769.jpg");
        System.out.println(b);
    }
}
