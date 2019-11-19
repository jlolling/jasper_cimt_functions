package de.cimt.jasperreports.functions;

import java.util.Collection;

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
	@FunctionParameters({ 
		@FunctionParameter("string"),
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
	@FunctionParameters({ 
		@FunctionParameter("string"),
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
	
	@Function("COLLECTION_TO_STRING")
	@FunctionParameters({ 
		@FunctionParameter("list"),
		@FunctionParameter("delimiter"),
		@FunctionParameter("quoteVarchar")
	})
	public static String COLLECTION_TO_STRING(Collection<?> list, String delimiter, boolean quoteVarchar) {
		if (list == null) {
			return null;
		}
		if (delimiter == null) {
			delimiter = ",";
		}
		StringBuilder sb = new StringBuilder();
		boolean firstLoop = true;
		for (Object value : list) {
			 if (firstLoop) {
				 firstLoop = false;
			 } else if (delimiter != null) {
				 sb.append(delimiter);
			 }
			 if (quoteVarchar) {
				 sb.append("'");
			 }
			 sb.append(String.valueOf(value));
			 if (quoteVarchar) {
				 sb.append("'");
			 }
		}
		return sb.toString();
	}

}