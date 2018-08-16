package de.cimt.jasperreports.functions;

import java.lang.management.ManagementFactory;

import net.sf.jasperreports.functions.annotations.Function;
import net.sf.jasperreports.functions.annotations.FunctionCategories;
import net.sf.jasperreports.functions.annotations.FunctionParameter;
import net.sf.jasperreports.functions.annotations.FunctionParameters;

@FunctionCategories({ de.cimt.jasperreports.functions.EnvironmentUtilsCategory.class })
public class EnvironmentUtils {

	@Function("RUNS_ON_SERVER")
	public static boolean RUNS_ON_SERVER() {
		try {
			Class.forName("com.jaspersoft.jasperserver.api.engine.jasperreports.util.RepositoryUtil");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Function("SERVER_HOST")
	public static String SERVER_HOST() {
		try {
			String info = ManagementFactory.getRuntimeMXBean().getName();
			int p = info.indexOf('@');
			if (p > 0) {
				return info.substring(p + 1);
			} else {
				return info;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Function("GET_ENV_VAR")
	@FunctionParameters({ @FunctionParameter("string") })
	public static String GET_ENV_VAR(String name) {
		try {
			return System.getenv(name);
		} catch (Exception e) {
			return null;
		}
	}

}
