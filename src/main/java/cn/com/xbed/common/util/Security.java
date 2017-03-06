package cn.com.xbed.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;

public class Security {
	
    private static Logger logger = LoggerFactory.getLogger(Security.class);
	
	private static String key = "A1f@1l*g6EGjfnbf";
	
	public static String encrypt(String input){
		return encrypt(input, key);
	}
	
	public static String encrypt(String input, String key){
        byte[] crypted = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        }catch(Exception e){
        System.out.println(e.toString());
	}
	return new String(Base64.encodeBase64(crypted));
}
 
	
	 public static String decrypt(String input){
		 return decrypt(input, key);
	 }
	
    public static String decrypt(String input, String key){
        byte[] output = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
            }catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(output);
    }

    /**
     * DES加密
     *
     * @param text
     *            加密字符串
     * @param key
     *            密钥
     * @return String
     */
    public static String desEncrypt(String text, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            
            byte [] result = cipher.doFinal(text.getBytes());

            return result == null ? null : new String(Base64.encodeBase64(result));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param text
     *            解密字符串
     * @param key
     *            密钥
     * @return String
     */
    public static String desDecrypt(String text, String key) {
        try {
            byte[] bytes = Base64.decodeBase64(text);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);

            return new String(cipher.doFinal(bytes));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
 
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		System.out.println(Security.decrypt(URLDecoder.decode("oReSk79gLi8kCM97q55znxhl1MCHN%2FCU%2BnR%2BwV%2BMPc6eKQ7rpg6Tj15FDUIu4KTUiYufrSDcrx6L2HEuVX8ovg%2B9KuTCNqhMWq%2F%2Fl%2F1CcOE%3D", "utf-8")));
		System.out.println(Security.decrypt("oReSk79gLi8kCM97q55znxhl1MCHN%2FCU%2BnR%2BwV%2BMPc6eKQ7rpg6Tj15FDUIu4KTUiYufrSDcrx6L2HEuVX8ovg%2B9KuTCNqhMWq%2F%2Fl%2F1CcOE%3D"));
//		System.out.println(Security.encrypt("明文-》des->utf-8"));
		//明文-》des->utf-8
		//明文-》des->utf-8

			
	}	
}
