package cn.nudt.goods.utils;

import java.io.IOException;
import java.util.Random;


public class Crypt {
	private static final String CHARSET = "UTF-8";

	private static Random randGen = null;

	private static char[] numberAndLetters = null;

	public static String encrypt(String str, String key) throws IOException {
		return Base64.encode(new AES().encrypt(str.getBytes(CHARSET),
				key.getBytes(CHARSET)));
	}

	public static String decrypt(String str, String key) throws IOException {
		return new String(new AES().decrypt(Base64.decode(str),
				key.getBytes(CHARSET)), CHARSET).trim();
	}

	public static byte[] decrypt(byte[] data, String key) throws IOException {
		return new AES().decrypt(data, key.getBytes(CHARSET));
	}

	public static byte[] encrypt(byte[] data, String key) throws IOException {
		return new AES().encrypt(data, key.getBytes(CHARSET));
	}

	public static String generateKey(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numberAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numberAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}
}