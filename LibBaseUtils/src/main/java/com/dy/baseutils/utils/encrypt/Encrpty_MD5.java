package com.dy.baseutils.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrpty_MD5 {
	public static String getMd5(String text) {
		StringBuilder hex = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte hash[] = md.digest(text.getBytes());

			hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10)
					hex.append("0");
				hex.append(Integer.toHexString(b & 0xFF));
			}
			return hex.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hex.toString();
	}
}
