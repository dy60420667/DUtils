package com.dy.baseutils.utils.encrypt;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Encrypt_RAS {

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	private String publicKey;
	private String privateKey;

	private static volatile Encrypt_RAS instance;

	private Encrypt_RAS() {
	}

	public static Encrypt_RAS getInstance() {
		if (instance == null) {
			if(instance==null){
				synchronized (Encrypt_RAS.class){
					instance = new Encrypt_RAS();
				}
			}
		}
		return instance;
	}

	public void init(String publicKey,String privateKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}


	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @return
	 * @throws Exception
	 */
	public String encryptByPublicKeyNew(String encryptData) throws Exception {
		byte[] data = encryptData.getBytes("utf-8");
		// 对公钥解密
		byte[] keyBytes = Base64.decode(publicKey, Base64.DEFAULT);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);// RSA加密

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		String baseB = Base64.encodeToString(encryptedData, Base64.DEFAULT);
		String result = URLEncoder.encode(baseB, "UTF-8");// URLEncoder编码
		return result;
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String decryptByPrivateKeyNew(String data) throws Exception {
		data = URLDecoder.decode(data, "UTF-8");
		byte[] encryptedData = data.getBytes("utf-8");
		encryptedData = Base64.decode(encryptedData, Base64.DEFAULT);
		// 对密钥解密
		byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		String result = new String(decryptedData);
		result = URLDecoder.decode(result, "UTF-8");
		return result;
	}

}