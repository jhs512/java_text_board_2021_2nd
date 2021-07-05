package com.sbs.exam.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

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

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}

	public static Map<String, Object> mapOf(Object... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("인자를 짝수개 입력해주세요.");
		}

		int size = args.length / 2;

		Map<String, Object> map = new LinkedHashMap<>();

		for (int i = 0; i < size; i++) {
			int keyIndex = i * 2;
			int valueIndex = keyIndex + 1;

			String key;
			Object value;

			try {
				key = (String) args[keyIndex];
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("키는 String으로 입력해야 합니다. " + e.getMessage());
			}

			value = args[valueIndex];

			map.put(key, value);
		}

		return map;
	}

	public static String getKeywordsStrFromStr(String str) {
		String keywordsStr = "";

		List<String> keywords = getKeywordsFromStr(str);

		for (String keyword : keywords) {
			keywordsStr += " #" + keyword;
		}

		keywordsStr = keywordsStr.trim();

		return keywordsStr;
	}

	public static List<String> getKeywordsFromStr(String str) {
		List<String> keywords = new ArrayList<>();

		Komoran komoran = new Komoran(DEFAULT_MODEL.EXPERIMENT);

		KomoranResult analyzeResultList = komoran.analyze(str);

		List<Token> tokenList = analyzeResultList.getTokenList();
		for (Token token : tokenList) {
			String keyword = token.getMorph();
			String pos = token.getPos();

			switch (keyword) {
			case "어제":
			case "동시":
			case "이":
				continue;
			}

			switch (pos) {
			case "NNP":
			case "NNG":
				keywords.add(keyword);
				break;
			}
		}

		return keywords;
	}
}
