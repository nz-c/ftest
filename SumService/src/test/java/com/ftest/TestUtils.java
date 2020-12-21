package com.ftest;

public class TestUtils {

	public static boolean isNumeric(String str) {
		if (str == null)
			return false;

		try {
			Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

}
