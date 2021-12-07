package com.autocluck.tweet_scheduler;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AutoCluckUtil {

	private static final String ALGORITHM = "AES";

	public static String decrypt(String encryptedKey, String secretKey) throws Exception {
		try {
			byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedKey)));
		} catch (Exception ex) {
			throw ex;
		}
	}
}
