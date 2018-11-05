package com.yonbor.baselib.utils;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by CZ_Ar on 2017/9/26.
 */
public class DesUtil {
    private static byte[] IV_PARAM = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * DES加密
     * @param data      加密数据
     * @param key       加密签名
     * @return  密文
     */
    public static byte[] encode( byte[] data, byte[] key) throws DesException{
        byte[] result;
        try {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV_PARAM);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
           result = cipher.doFinal(data);
        } catch (Throwable t) {
            throw new DesException(t);
        }
        return result;
    }

    /**
     * DES算法，解密
     *
     * @param data
     *            待解密字符串
     * @param key
     *            解密私钥，长度不能够小于8位
     * @return 解密后内容
     */
    public static byte[] decode( byte[] data, byte[] key) throws DesException{
        byte[] result;
        try {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV_PARAM);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            result = cipher.doFinal(data);
        } catch (Throwable t) {
            throw new DesException(t);
        }
        return result;
    }

    public static class DesException extends RuntimeException {
        public DesException() {
            super();
        }

        public DesException(String msg) {
            super(msg);
        }

        public DesException(String msg, Throwable throwable) {
            super(msg, throwable);
        }

        public DesException(Throwable throwable) {
            super(throwable);
        }

        public DesException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    public static void main(String[] args) throws DesException, UnsupportedEncodingException {

//        String sCon = "{\"ageMonth\":228,\"areaCode\":\"\",\"gender\":\"1\",\"latitude\":30.193948,\"longitude\":120.19204,\"tenantId\":\"hcn\"}";
        String sCon = "abcdefgh";
        byte[] content = sCon.getBytes("UTF-8");
        String appkey = "010";
        String timeStr = "1506396657911";
//        String key = appkey+"&"+timeStr;
        String key =  "abcdefgh";
        System.out.println("加密前：" + new String(content,"UTF-8"));

        byte[] resultBytes = encode(content, key.getBytes("UTF-8"));
        System.out.println(Base64.encodeToString(resultBytes, Base64.NO_WRAP));
        String resultStr = URLEncoder.encode(Base64.encodeToString(resultBytes, Base64.NO_WRAP),"UTF-8");
        System.out.println("加密后：" + resultStr);

        // 解密
        byte[] deResult = Base64.decode(URLDecoder.decode(resultStr,"UTF-8").getBytes("UTF-8"), Base64.NO_WRAP);
        byte[] result = decode(deResult, key.getBytes("UTF-8"));

        System.out.println("\n解密后：" + new String(result,"UTF-8"));

    }
}
