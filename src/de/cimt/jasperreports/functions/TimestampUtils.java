package de.cimt.jasperreports.functions;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.functions.annotations.Function;
import net.sf.jasperreports.functions.annotations.FunctionCategories;
import net.sf.jasperreports.functions.annotations.FunctionParameter;
import net.sf.jasperreports.functions.annotations.FunctionParameters;

@FunctionCategories({ de.cimt.jasperreports.functions.TimestampUtilsCategory.class })
public class TimestampUtils {
	
	private static Map<String, Symbols> symbolLocaleMap = new HashMap<String, Symbols>();
	
	private static Symbols getSymbols(String locale) {
		Symbols symbols = symbolLocaleMap.get(locale);
		if (symbols == null) {
			symbols = new Symbols(locale);
			symbolLocaleMap.put(locale, symbols);
		}
		return symbols;
	}
	
	public static class Symbols {
		private String[] monthNames = null;
		private String[] monthShortNames = null;
		private String[] weekDayNames = null;
		private String[] weekDayShortNames = null;
		private DateFormatSymbols symbols;
		
		public Symbols(String locale) {
			symbols = new DateFormatSymbols(createLocale(locale));
			monthNames = symbols.getMonths();
			monthShortNames = symbols.getShortMonths();
			weekDayNames = symbols.getWeekdays();
			weekDayShortNames = symbols.getShortWeekdays();
		}
		
		public String getMonthName(int index) {
			return monthNames[index];
		}
		
		public String getWeekDayName(int index) {
			return weekDayNames[index];
		}
		
		public String getMonthShortName(int index) {
			return monthShortNames[index];
		}
		
		public String getWeekDayShortName(int index) {
			return weekDayShortNames[index];
		}

	}
	
    private static Locale createLocale(String localeName) {
        if (localeName == null || localeName.trim().length() < 2) {
        	return Locale.getDefault();
        } else {
        	localeName = localeName.trim();
            Locale locale = null;
            int pos = localeName.indexOf('_');
            if (pos > 1) {
                String language = localeName.substring(0, pos);
                String country = localeName.substring(pos + 1);
                locale = new Locale(language, country);
            } else {
                locale = new Locale(localeName);
            }
            return locale;
        }
    }

	@Function("TRUNCATE_TO_DATE")
	@FunctionParameters({ @FunctionParameter("timestamp") })
    public static Date TRUNCATE_TO_DATE(Date timestamp) {
    	if (timestamp == null) return null;
    	Calendar c = Calendar.getInstance();
    	c.setTime(timestamp);
    	// cut time
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	return c.getTime();
    }

	@Function("TODAY_MORNING")
	public static Date TODAY_MORNING() {
		return TRUNCATE_TO_DATE(new Date());
    }

	@Function("YESTERDAY_MORNING")
	public static Date YESTERDAY_MORNING() {
		return TRUNCATE_TO_DATE(ADD_DAYS(new Date(), -1));
    }

	@Function("ADD_DAYS")
	@FunctionParameters({ @FunctionParameter("anyDate"), @FunctionParameter("daysToAdd") })
    public static Date ADD_DAYS(Date anyDate, int daysToAdd) {
    	if (anyDate != null) {
        	Calendar c = Calendar.getInstance();
        	c.setTime(anyDate);
        	c.add(Calendar.DAY_OF_YEAR, daysToAdd);
    		return c.getTime();
    	} else {
    		return null;
    	}
    }
	
	@Function("ADD_MONTHS")
	@FunctionParameters({ @FunctionParameter("anyDate"), @FunctionParameter("monthsToAdd") })
    public static Date ADD_MONTHS(Date anyDate, int monthsToAdd) {
    	if (anyDate != null) {
        	Calendar c = Calendar.getInstance();
        	c.setTime(anyDate);
        	c.add(Calendar.MONTH, monthsToAdd);
    		return c.getTime();
    	} else {
    		return null;
    	}
    }
	
	@Function("DATE_AS_INT")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Integer DATE_AS_INT(Date anyDate) {
    	if (anyDate != null) {
        	Calendar c = Calendar.getInstance();
        	c.setTime(anyDate);
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int id = c.get(Calendar.YEAR) * 10000;
        	id = id + (c.get(Calendar.MONTH) + 1) * 100;
        	id = id + c.get(Calendar.DAY_OF_MONTH);
        	return id;
    	} else {
    		return null;
    	}
    }

	@Function("DATE_AS_WEEK_INT")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Integer DATE_AS_WEEK_INT(Date anyDate) {
    	if (anyDate != null) {
        	Calendar c = Calendar.getInstance();
    		c.setMinimalDaysInFirstWeek(4);
        	c.setTime(anyDate);
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int id = c.get(Calendar.YEAR) * 100;
        	id = id + c.get(Calendar.WEEK_OF_YEAR);
        	return id;
    	} else {
    		return null;
    	}
    }

	@Function("DATE_AS_MONTH_INT")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Integer DATE_AS_MONTH_INT(Date anyDate) {
    	if (anyDate != null) {
        	Calendar c = Calendar.getInstance();
        	c.setTime(anyDate);
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int id = c.get(Calendar.YEAR) * 100;
        	id = id + c.get(Calendar.MONTH) + 1;
        	return id;
    	} else {
    		return null;
    	}
    }

	@Function("INT_AS_DATE")
	@FunctionParameters({ @FunctionParameter("anyDateAsInt") })
    public static Date INT_AS_DATE(Integer anyDateAsInt) {
    	if (anyDateAsInt != null) {
        	Calendar c = Calendar.getInstance();
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int year = anyDateAsInt / 10000;
        	int monthDay = (anyDateAsInt - (year * 10000));
        	int month = monthDay / 100;
        	int day = monthDay % 100;
        	c.set(Calendar.YEAR, year);
        	c.set(Calendar.MONTH, month - 1);
        	c.set(Calendar.DAY_OF_MONTH, day);
        	return c.getTime();
    	} else {
    		return null;
    	}
    }

	@Function("WEEK_INT_AS_DATE_DE")
	@FunctionParameters({ @FunctionParameter("weekInt") })
    public static Date WEEK_INT_AS_DATE_DE(Integer weekInt) {
    	if (weekInt != null) {
        	Calendar c = Calendar.getInstance(Locale.GERMAN);
    		c.setMinimalDaysInFirstWeek(4);
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int year = weekInt / 100;
        	int week = (weekInt - (year * 100));
        	c.set(Calendar.YEAR, year);
        	c.set(Calendar.WEEK_OF_YEAR, week);
        	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        	return c.getTime();
    	} else {
    		return null;
    	}
    }

	@Function("WEEK_INT_AS_DATE")
	@FunctionParameters({ @FunctionParameter("weekInt") })
    public static Date WEEK_INT_AS_DATE(Integer weekInt) {
    	if (weekInt != null) {
        	Calendar c = Calendar.getInstance();
    		c.setMinimalDaysInFirstWeek(4);
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int year = weekInt / 100;
        	int week = (weekInt - (year * 100));
        	c.set(Calendar.YEAR, year);
        	c.set(Calendar.WEEK_OF_YEAR, week);
        	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        	return c.getTime();
    	} else {
    		return null;
    	}
    }

	@Function("MONTH_INT_AS_DATE")
	@FunctionParameters({ @FunctionParameter("anyDateAsMonthInt") })
    public static Date MONTH_INT_AS_DATE(Integer anyDateAsMonthInt) {
    	if (anyDateAsMonthInt != null) {
        	Calendar c = Calendar.getInstance();
        	// cut time
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.set(Calendar.MILLISECOND, 0);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	int year = anyDateAsMonthInt / 100;
        	int month = (anyDateAsMonthInt - (year * 100));
        	c.set(Calendar.YEAR, year);
        	c.set(Calendar.MONTH, month - 1);
        	c.set(Calendar.DAY_OF_MONTH, 1);
        	return c.getTime();
    	} else {
    		return null;
    	}
    }

	@Function("IS_BETWEEN")
	@FunctionParameters({ @FunctionParameter("anyDate"),
		@FunctionParameter("start"),
		@FunctionParameter("end") })
	public static boolean IS_BETWEEN(Date anyDate, Date start, Date end) {
		if (anyDate == null) {
			return false;
		}
		if (start.equals(anyDate)) {
			return true;
		} else if (end.equals(anyDate)) {
			return true;
		} else if (start.before(anyDate) && end.after(anyDate)) {
			return true;
		} else {
			return false;
		}
	}

	@Function("COALESCE")
	@FunctionParameters({ @FunctionParameter("dates") })
	public static Date COALESCE(Date ...dates) {
		for (Date d : dates) {
			if (d != null) 
				return d;			
		}
		return null;
	}

	@Function("MONTH_START")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date MONTH_START(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance();
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_MONTH, 1);
    	return c.getTime();
    }

	@Function("MONTH_END")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date MONTH_END(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance();
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_YEAR, -1);
    	return c.getTime();
    }

	@Function("WEEK_START")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date WEEK_START(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance();
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
    	return c.getTime();
    }

	@Function("WEEK_START_DE")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date WEEK_START_DE(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance(Locale.GERMAN);
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
    	return c.getTime();
    }

	@Function("WEEK_START_EN")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date WEEK_START_EN(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance(Locale.UK);
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
    	return c.getTime();
    }

	@Function("WEEK_END")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date WEEK_END(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance();
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		c.add(Calendar.DAY_OF_YEAR, 7);
    	return c.getTime();
    }

	@Function("WEEK_END_DE")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date WEEK_END_DE(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance(Locale.GERMAN);
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		c.add(Calendar.DAY_OF_YEAR, 7);
    	return c.getTime();
    }

	@Function("WEEK_END_EN")
	@FunctionParameters({ @FunctionParameter("anyDate") })
    public static Date WEEK_END_EN(Date anyDate) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance(Locale.UK);
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		c.add(Calendar.DAY_OF_YEAR, 7);
    	return c.getTime();
    }

	@Function("PARSE_DATE")
	@FunctionParameters({ @FunctionParameter("pattern"), @FunctionParameter("anyDateStr") })
    public static Date PARSE_DATE(String pattern, String anyDateStr) throws Exception {
    	if (anyDateStr == null) return null;
    	if (pattern == null) throw new Exception("Pattern cannot ne null!");
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	return sdf.parse(anyDateStr);
    }

	@Function("MONTH_NAME")
	@FunctionParameters({ @FunctionParameter("anyDate"), @FunctionParameter("locale") })
    public static String MONTH_NAME(Date anyDate, String locale) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance(Locale.UK);
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
    	int index = c.get(Calendar.MONTH);
    	Symbols symbols = getSymbols(locale);
    	return symbols.getMonthName(index);
    }

	@Function("WEEK_DAY_NAME")
	@FunctionParameters({ @FunctionParameter("anyDate"), @FunctionParameter("locale") })
    public static String WEEK_DAY_NAME(Date anyDate, String locale) {
    	if (anyDate == null) return null;
    	Calendar c = Calendar.getInstance(Locale.UK);
		c.setMinimalDaysInFirstWeek(4);
    	c.setTime(TRUNCATE_TO_DATE(anyDate));
    	int index = c.get(Calendar.MONTH);
    	Symbols symbols = getSymbols(locale);
    	return symbols.getWeekDayName(index);
    }

	@Function("DURATION_TO_STRING")
	@FunctionParameters({ @FunctionParameter("millis") })
    public static String DURATION_TO_STRING(Long millis) {
		if (millis == null) {
			return null;
		}
		Duration duration = Duration.of(millis, ChronoUnit.MILLIS);
		long days = duration.toDays();
		long hours = duration.toHours() - (days * 24);
		long minutes = duration.toMinutes() - ((days * 24 * 60) + (60 * hours));
		long seconds = (duration.toMillis() / 1000) - ((days * 24 * 60 * 60) + (hours * 60 * 60) + (minutes * 60));
		StringBuilder sb = new StringBuilder();
		if (days > 0) {
			sb.append(days);
			sb.append("d ");
		}
		if (hours > 0) {
			sb.append(hours);
			sb.append("h ");
		}
		if (minutes > 0) {
			sb.append(minutes);
			sb.append("min ");
		}
		if (seconds > 0) {
			sb.append(seconds);
			sb.append("s");
		}
		return sb.toString();
	}

}