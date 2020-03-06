package ml.universo42.abatedouro.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.YearMonth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String[] SHORT_MONTHS = new String[] { "jan", "fev", "mar", "abr", "mai", "jun",
			"jul", "ago", "set", "out", "nov", "dez" };
	
	public static final DateFormat DF_DATE_TIME_FILE_NAME = new SimpleDateFormat("dd-MM-yy_-_HH-mm-ss");
	public static final DateFormat DF_DATE_TIME = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	public static final DateFormat DF_DATE = new SimpleDateFormat("dd/MM/yy");
	public static final DateFormat DF_DATE_MONTH_YEAR = new SimpleDateFormat("MMM-yyyy");
	
	public static final DateFormat DF_DATE_AMERICAN = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String shortMonth(int month) {
		return SHORT_MONTHS[month];
	}

	public static String toStringTime(LocalTime time) {
		return time.toString("HH:mm");
	}

	public static String toStringTime(long minutes) {
		boolean isNegative = false;
		
		if (minutes < 0) {
			isNegative = true;
			minutes = Math.abs(minutes);
		}

		long hours = minutes / 60;

		if (hours > 0) {
			minutes = minutes - (hours * 60);
		}

		return (isNegative ? "-" : "") + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
	}
	
	public static String shortMonthDescrition(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return shortMonth(c.get(Calendar.MONTH)) + "/" + c.get(Calendar.YEAR);
	}
	
	public static int minute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return c.get(Calendar.MINUTE);
	}
	
	public static int hour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	public static Date of(int year, int month, int day) {
		return new DateTime(year, month, day, 0, 0).toDate();
	}
	
	public static Date of(int year, int month, int day, int hour, int minute) {
		Date dt = of(year, month, day);
		
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		
		return c.getTime();
	}
	
	public static Date inFistDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return moveToFirstSecondOfDay(cal.getTime());
	}
	
	public static Date inLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		return moveToLastSecondOfDay(cal.getTime());
	}
	
	public static Date asDate(LocalDate localDate) {
		return localDate.toDate();
	}
	
	public static long getDifMilliseconds(Date dt1, Date dt2) {
		return Math.abs(dt1.getTime() - dt2.getTime());
	}
	
	public static long getDifSeconds(Date dt1, Date dt2) {
		return getDifMilliseconds(dt1, dt2) / 1000;
	}
	
	public static long getDifMinutes(Date dt1, Date dt2) {
		return getDifMilliseconds(dt1, dt2) / 1000 / 60;
	}
	
	public static String getDataDescritiva(Date date) {
		String str;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		str = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + " do "
				+ (c.get(Calendar.MONTH) + 1) + " de " +
				c.get(Calendar.YEAR) + ". ";
		
		str += "Às " + c.get(Calendar.HOUR_OF_DAY) + " hora"+(c.get(Calendar.HOUR_OF_DAY)>1?"s":"")+" e "
				+ c.get(Calendar.MINUTE) + " minuto" + (c.get(Calendar.MINUTE)>1?"s.":".");
		
		return str;
	}
	
	public static Date moveToLastSecondOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTime();
	}
	
	public static Date moveToFirstSecondOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public static Date addDays(Date date, int value) {
		return add(date, Calendar.DAY_OF_MONTH, value);
	}
	
	public static Date addYears(Date date, int value) {
		return add(date, Calendar.YEAR, value);
	}
	
	public static Date add(Date date, int calendarField, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, value);
		return cal.getTime();
	}

	public static Date firstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return moveToFirstSecondOfDay(cal.getTime());
	}
	
	public static Date lastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		return moveToLastSecondOfDay(cal.getTime());
	}

	public static boolean isInMonth(Date data) {
		return firstDayOfMonth().compareTo(data) <= 0 && lastDayOfMonth().compareTo(data) >= 0;
	}

	public static boolean isInMonth(Date dtAbertura, YearMonth month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtAbertura);

		int monthFromDate = cal.get(Calendar.MONTH);
		
		return monthFromDate + 1 == month.getMonthOfYear();
	}
	
	public static boolean isInMonth(Date date, Date monthAndYear) {
		return isInRange(inFistDayOfMonth(monthAndYear), inLastDayOfMonth(monthAndYear), date);
	}
	
	public static boolean isInRange(Date dtBegin, Date dtEnd, Date date) {
		return dtBegin.compareTo(date) <= 0 && dtEnd.compareTo(date) >= 0;
	}
	
	public static boolean isGreaterThanNow(Date date) {
		return date.compareTo(new Date()) > 0;
	}
	
	public static boolean isLessThanNow(Date date) {
		return date.compareTo(new Date()) < 0;
	}
	
	public static String toStringDateTimeFileName(Date data) {
		return DF_DATE_TIME_FILE_NAME.format(data);
	}
	
	public static String toStringDateTime(Date data) {
		return DF_DATE_TIME.format(data);
	}
	
	public static String toStringDate(Date data) {
		return DF_DATE.format(data);
	}
	
	public static String toStringMonthAndYear(Date date) {
		return DF_DATE_MONTH_YEAR.format(date);
	}
	
	public static Date parseDateAmerican(String strDate) {
		if (StringUtil.isEmpty(strDate)) {
			return null;
		}
		
		try {
			return DF_DATE_AMERICAN.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
	}
	
	public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
	}
	
}
