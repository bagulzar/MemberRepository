package com.fr.memberservice.util;

import java.util.List;

public class CommonUtils {

	public static boolean isNullOrEmpty(String string) {
		return (((string == null) || (string.trim().length() == 0)) ? true : false);
	}

	public static boolean isNullOrEmpty(List<?> list) {
		return (((list == null) || (list.size() == 0)) ? true : false);
	}
}
