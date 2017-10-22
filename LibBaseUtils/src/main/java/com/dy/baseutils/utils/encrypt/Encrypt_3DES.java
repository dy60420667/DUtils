package com.dy.baseutils.utils.encrypt;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt_3DES {

    /**
     * 解密
     *
     * @param secretData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decryption(String secretData, String secretKey) {
        try {
            return new String(decryptMode(GetKeyBytes(secretKey), Base64.decode(secretData, Base64.DEFAULT)));
        } catch (Exception e) {
            return "";

        }
    }

    /**
     * 加密
     *
     * @param plainData 待加密字段
     * @param secretKey 秘钥
     * @throws Exception
     */

    public static String encryption(String plainData, String secretKey) {
        try {
            return byte2Base64(encryptMode(GetKeyBytes(secretKey), plainData.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    //计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位

    private static byte[] GetKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        alg.update(strKey.getBytes());
        byte[] bkey = alg.digest();
        System.out.println("md5key.length=" + bkey.length);
        System.out.println("md5key=" + byte2hex(bkey));
        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++) {
            bkey24[i] = bkey[i];
        }
        for (int i = start; i < 24; i++) {//为了与.net16位key兼容
            bkey24[i] = bkey[i - start];
        }
        return bkey24;
    }


    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish

    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }

        return null;

    }


    //keybyte为加密密钥，长度为24字节

    //src为加密后的缓冲区

    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


    //转换成base64编码

    private static String byte2Base64(byte[] b) {
        return Base64.encodeToString(b, Base64.DEFAULT);

    }


    //转换成十六进制字符串

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }
}
