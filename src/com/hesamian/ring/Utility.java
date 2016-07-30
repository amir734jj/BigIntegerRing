package com.hesamian.ring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	public static int indexOf(String pattern, String str) {

		return indexOf(Pattern.compile(pattern), str);
	}

	public static int indexOf(Pattern pattern, String str) {
		Matcher matcher = pattern.matcher(str);
		return matcher.find() ? matcher.start() : -1;
	}
}
