package com.hjg.baseapp.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 随机数工具类
 *
 * @Author Lewis(lgs@yitong.com.cn) 2014年8月13日 上午9:56:45
 * @Class RandomUtils
 * Copyright (c) 2014 Shanghai P&C Information Technology Co.,Ltd. All rights reserved.
 */
public class RandomUtils {

	private static final String TAG = "RandomUtils";

	public static SecureRandom getSecureRandomInstance() {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			Logs.d(TAG, e.getMessage());
		}
		return random;
	}

	/**
	 * 生成指定长度的随机数，纯数字字符串
	 */
	private static final char[] CHAR_RANDOMS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final char[] CHAR_RANDOMS_NONZERO = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static String generateNumberString(int length) {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			Logs.d(TAG, e.getMessage());
		}
		if (random == null) {
			return null;
		}
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < length; i++) {
			ret.append(CHAR_RANDOMS[random.nextInt(CHAR_RANDOMS.length)]);
		}
		return ret.toString();
	}

	/**
	 * 生成指定位数的随机数
	 */
	public static int generateNumber(int length) {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			Logs.d(TAG, e.getMessage());
		}
		if (random == null) {
			return -1;
		}
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				ret.append(CHAR_RANDOMS_NONZERO[random.nextInt(CHAR_RANDOMS_NONZERO.length)]);
			} else {
				ret.append(CHAR_RANDOMS[random.nextInt(CHAR_RANDOMS.length)]);
			}
		}
		random = null;
		String str = ret.toString();

		Integer number = null;
		try {
			number = Integer.valueOf(str);
		} catch (NumberFormatException e) {
			Logs.e(TAG, e.getMessage());
		}

		if (number == null) {
			return -1;
		}
		return number.intValue();
	}

	/**
	 * 生成指定长度的随机字符串，数字字母组合
	 */
	private static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String generateComposeString(int length) {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			Logs.d(TAG, e.getMessage());
		}
		if (random == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS
					.length())));
		}
		return sb.toString();
	}
}
