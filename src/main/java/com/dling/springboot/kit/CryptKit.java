package com.dling.springboot.kit;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.AssertFalse;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @description 加解密工具类
 * @author dling
 * @date 2019-06-11 00:09:25
 * @since jdk8
 */
public class CryptKit {

    public static final String CIPHER_TYPE = "AES/CBC/PkCs5Padding";
    public static final String AES = "AES";

    public static final String UTF8 = "utf-8";

    public static final String ENCRYPT_MODE = "";
    public static final String DECRYPT_MODE = "";


    /**
     * 加解密对象初始化
     * @param mode 模式
     * @param secretKey 密钥
     * @param secretIv 偏移量
     * @return Cipher
     */
    private static Cipher getCipher(Integer mode, String secretKey, String secretIv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        if (mode == null) throw new RuntimeException("mode 不能为 null");
        if (secretKey == null || "".equals(secretKey.trim())) throw new RuntimeException("secretKey 不能为空");
        if (secretIv == null || "".equals(secretIv.trim())) throw new RuntimeException("secretIv 不能为空");
        // 实例化加解对象，指定加解密模式
        Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
        // 创建密钥对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), AES);
        // 初始化加解密对象
        cipher.init(mode, secretKeySpec, new IvParameterSpec(secretIv.getBytes()));
        return cipher;
    }

    /**
     * aes加密
     * @param secretKey 密钥
     * @param secretIv 偏移量
     * @param content 明文
     * @return byte[] 密文
     */
    public static byte[] aesEncrypt(String secretKey, String secretIv, byte[] content) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        if (content.length<=0) throw new RuntimeException("明文不能为空");
        return getCipher(Cipher.ENCRYPT_MODE, secretKey, secretIv).doFinal(content);
    }

    /**
     * aes解密
     * @param secretKey 密钥
     * @param secretIv 偏移量
     * @param content 密文
     * @return byte[] 明文
     */
    public static byte[] aesDecrypt(String secretKey, String secretIv, byte[] content) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        if (content.length<=0) throw new RuntimeException("密文不能为空");
        return getCipher(Cipher.DECRYPT_MODE, secretKey, secretIv).doFinal(content);
    }

    /**
     * base64加密
     * @param secretKey 密钥
     * @param secretIv 偏移量
     * @param content 明文
     * @return String 密文
     */
    public static String base64Encrypt(String secretKey,  String secretIv, String content) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return Base64.getEncoder().encodeToString(aesEncrypt(secretKey, secretIv, content.getBytes()));
    }

    /**
     * base64解密
     * @param secretKey 密钥
     * @param secretIv 偏移量
     * @param content 密文
     * @return String 明文
     */
    public static String base64Decrypt(String secretKey,  String secretIv, String content) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return new String(aesDecrypt(secretKey, secretIv, Base64.getDecoder().decode(content.getBytes())));
    }


    // 测试
    public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String secretKey = "key0123456789123"; //密钥
        String secretIv = "iv01234567891234";  //偏移量
        String content = "rNuS/gM3PkO2pm4RXje9N7zJ9GQ21i+cvwFbO344Fjn1bLriE7oJMWRKsgRGvmQWuTOTMvImugxr2ncbhhm4+g==";
        String decryptStr = base64Decrypt(secretKey, secretIv, content);
        System.out.println(decryptStr);
        System.out.println(content);
        System.out.println(base64Encrypt(secretKey, secretIv, decryptStr));

        String str0 = "12ab我好";
        byte[] bts = str0.getBytes();
        System.out.println(bts.length);
        for (int i=0; i<bts.length; i++) {
            System.out.print(bts[i] + "\t");
        }

    }

}
