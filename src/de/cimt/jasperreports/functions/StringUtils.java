package de.cimt.jasperreports.functions;

import net.sf.jasperreports.functions.annotations.Function;
import net.sf.jasperreports.functions.annotations.FunctionCategories;
import net.sf.jasperreports.functions.annotations.FunctionParameter;
import net.sf.jasperreports.functions.annotations.FunctionParameters;

@FunctionCategories({ de.cimt.jasperreports.functions.StringUtilsCategory.class })
public class StringUtils {

	@Function("IS_EMPTY")
	@FunctionParameters({ @FunctionParameter("string") })
	public static boolean IS_EMPTY(String string) {
		if (string == null) {
			return true;
		}
		if (string.trim().isEmpty()) {
			return true;
		}
		if (string.trim().equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}

	@Function("STRIP_MULTIPLE_SPACES")
	@FunctionParameters({ @FunctionParameter("string") })
	public static String STRIP_MULTIPLE_SPACES(String string) {
		string = string.trim();
		int pos = 0;
		while (pos != -1) {
			string = string.replace("  ", " ");
			pos = string.indexOf("  ");
		}
		return string;
	}

	@Function("FILL_RIGHT")
	@FunctionParameters({ @FunctionParameter("string"),
		@FunctionParameter("length"),
		@FunctionParameter("charToFillWith")
	})
	public static String FILL_RIGHT(String string, int length, String charToFillWith) {
		if (string == null) {
			string = "";
		}
		if (string.length() > length) {
			throw new IllegalArgumentException("String input length > "
					+ length);
		}
		StringBuilder sb = new StringBuilder(length);
		sb.append(string);
		for (int i = sb.length(); i < length; i++) {
			sb.append(charToFillWith);
		}
		return sb.toString();
	}

	@Function("FILL_LEFT")
	@FunctionParameters({ @FunctionParameter("string"),
		@FunctionParameter("length"),
		@FunctionParameter("charToFillWith")
	})
	public static String FILL_LEFT(String string, int length, String charToFillWith) {
		if (string == null) {
			string = "";
		}
		if (string.length() > length) {
			return string;
		}
		StringBuilder sb = new StringBuilder(length);
		sb.append(string);
		for (int i = sb.length(); i < length; i++) {
			sb.insert(0, charToFillWith);
		}
		return sb.toString();
	}
	
}