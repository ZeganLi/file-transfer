package tran.common.utils;

import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

public class AesUtil {

	public static String randomKey = RandomStringUtils.randomAlphanumeric(16);
	public static final String KEY = "KWR8qVEVyxpNTV0R";
	public static String NewKey = null;
	public static Map<Long, String> clientKey = new HashMap<>();
	/**
	 * @param data 要加密的数据
	 * @param key 加密的密码，必须是16位。否则会报错。
	 * @return
	 * @throws Exception
	 */
	public static byte[] aesEncrypt(byte[] data, String key) throws Exception {
		if (data == null || key == null) return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = cipher.doFinal(data);
		return bytes;

	}

	/**
	 *
	 * @param Data 要解密的数据
	 * @param key 输入密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] aesDecrypt(byte[] Data, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		//byte[] bytes = new BASE64Decoder().decodeBuffer(Data);
		byte[] bytes = cipher.doFinal(Data);
		return bytes;
	}
}