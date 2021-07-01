package com.sbs.exam.util;

import java.text.SimpleDateFormat;

public class Util {
	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format1.format(System.currentTimeMillis());

		return dateStr;
	}

	public static int getRandomInt(int start, int end) {
		int size = end - start + 1;

		return start + (int) (Math.random() * size);
	}

}
