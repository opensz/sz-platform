package org.sz.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.i18n.LocaleContextHolder;
import org.sz.core.Constants;
import org.sz.core.util.DateUtil;

public class DateUtil {

	private static final Log log = LogFactory.getLog(DateUtil.class);

	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm";

	private DateUtil() {
	}

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		String defaultDatePattern;
		try {
			defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY,
					locale).getString("date.format");
		} catch (MissingResourceException mse) {
			defaultDatePattern = "MM/dd/yyyy";
		}

		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 * @see java.text.SimpleDateFormat
	 */
	public static Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.warn("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(final String strDate)
			throws ParseException {
		return convertStringToDate(getDatePattern(), strDate);
	}

	public static Calendar setStartDay(Calendar cal) {
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal;
	}

	public static Calendar setEndDay(Calendar cal) {
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal;
	}

	public static void copyYearMonthDay(Calendar destCal, Calendar sourceCal) {
		destCal.set(1, sourceCal.get(1));
		destCal.set(2, sourceCal.get(2));
		destCal.set(5, sourceCal.get(5));
	}

	public static String formatEnDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

		return sdf.format(date).replaceAll("上午", "AM").replaceAll("下午", "PM");
	}

	public static Date parseDate(String dateString) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateString, new String[] {
					"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
		} catch (Exception ex) {
			log.error(new StringBuilder().append("Pase the Date(")
					.append(dateString).append(") occur errors:")
					.append(ex.getMessage()).toString());
		}

		return date;
	}

	public static String addOneDay(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			Date dd = format.parse(date);
			calendar.setTime(dd);
			calendar.add(5, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tmpDate = format.format(calendar.getTime());
		return new StringBuilder().append(tmpDate.substring(5, 7)).append("/")
				.append(tmpDate.substring(8, 10)).append("/")
				.append(tmpDate.substring(0, 4)).toString();
	}

	public static String addOneHour(String date) {
		String amPm = date.substring(20, 22);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();

		int hour = Integer.parseInt(date.substring(11, 13));
		try {
			if (amPm.equals("PM")) {
				hour += 12;
			}
			date = new StringBuilder()
					.append(date.substring(0, 11))
					.append(hour >= 10 ? Integer.valueOf(hour)
							: new StringBuilder().append("0").append(hour)
									.toString()).append(date.substring(13, 19))
					.toString();
			Date dd = format.parse(date);
			calendar.setTime(dd);
			calendar.add(11, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tmpDate = format.format(calendar.getTime());

		hour = Integer.parseInt(tmpDate.substring(11, 13));
		amPm = (hour >= 12) && (hour != 0) ? "PM" : "AM";
		if (amPm.equals("PM")) {
			hour -= 12;
		}
		tmpDate = new StringBuilder()
				.append(tmpDate.substring(5, 7))
				.append("/")
				.append(tmpDate.substring(8, 10))
				.append("/")
				.append(tmpDate.substring(0, 4))
				.append(" ")
				.append(hour >= 10 ? Integer.valueOf(hour)
						: new StringBuilder().append("0").append(hour)
								.toString())
				.append(tmpDate.substring(13, tmpDate.length())).append(" ")
				.append(amPm).toString();

		return tmpDate;
	}

	public static String timeStrToDateStr(String timeStr) {
		String dateStr = new StringBuilder().append(timeStr.substring(24, 28))
				.append("-").toString();

		String mon = timeStr.substring(4, 7);
		if (mon.equals("Jan"))
			dateStr = new StringBuilder().append(dateStr).append("01")
					.toString();
		else if (mon.equals("Feb"))
			dateStr = new StringBuilder().append(dateStr).append("02")
					.toString();
		else if (mon.equals("Mar"))
			dateStr = new StringBuilder().append(dateStr).append("03")
					.toString();
		else if (mon.equals("Apr"))
			dateStr = new StringBuilder().append(dateStr).append("04")
					.toString();
		else if (mon.equals("May"))
			dateStr = new StringBuilder().append(dateStr).append("05")
					.toString();
		else if (mon.equals("Jun"))
			dateStr = new StringBuilder().append(dateStr).append("06")
					.toString();
		else if (mon.equals("Jul"))
			dateStr = new StringBuilder().append(dateStr).append("07")
					.toString();
		else if (mon.equals("Agu"))
			dateStr = new StringBuilder().append(dateStr).append("08")
					.toString();
		else if (mon.equals("Sep"))
			dateStr = new StringBuilder().append(dateStr).append("09")
					.toString();
		else if (mon.equals("Oct"))
			dateStr = new StringBuilder().append(dateStr).append("10")
					.toString();
		else if (mon.equals("Nov"))
			dateStr = new StringBuilder().append(dateStr).append("11")
					.toString();
		else if (mon.equals("Dec")) {
			dateStr = new StringBuilder().append(dateStr).append("12")
					.toString();
		}

		dateStr = new StringBuilder().append(dateStr).append("-")
				.append(timeStr.substring(8, 10)).toString();

		return dateStr;
	}

	public static int getExtraDayOfWeek(String sDate) {
		try {
			String formater = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			String weekday = date.toString().substring(0, 3);
			if (weekday.equals("Mon"))
				return 1;
			if (weekday.equals("Tue"))
				return 2;
			if (weekday.equals("Wed"))
				return 3;
			if (weekday.equals("Thu"))
				return 4;
			if (weekday.equals("Fri"))
				return 5;
			if (weekday.equals("Sat")) {
				return 6;
			}
			return 0;
		} catch (Exception ex) {
		}
		return 0;
	}

	public static String getDateWeekDay(String sDate) {
		try {
			String formater = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			String weekday = date.toString().substring(0, 3);

			return weekday;
		} catch (Exception ex) {
		}
		return "";
	}

	public static List<String> getUpDownFiveYear(Calendar cal) {
		List yearlist = new ArrayList();

		int curyear = cal.get(1);
		yearlist.add(String.valueOf(curyear - 2));
		yearlist.add(String.valueOf(curyear - 1));
		yearlist.add(String.valueOf(curyear));
		yearlist.add(String.valueOf(curyear + 1));
		yearlist.add(String.valueOf(curyear + 2));

		return yearlist;
	}

	public static List<String> getTwelveMonth() {
		List monthlist = new ArrayList();

		for (int idx = 1; idx <= 12; idx++) {
			monthlist.add(String.valueOf(idx));
		}

		return monthlist;
	}

	public static String[] getDaysBetweenDate(String startTime, String endTime) {
		String[] dateArr = null;
		try {
			String stime = timeStrToDateStr(startTime);
			String etime = timeStrToDateStr(endTime);

			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(stime);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(etime);

			long day = (date1.getTime() - date2.getTime()) / 86400000L > 0L ? (date1
					.getTime() - date2.getTime()) / 86400000L : (date2
					.getTime() - date1.getTime()) / 86400000L;

			dateArr = new String[Integer.valueOf(String.valueOf(day + 1L))
					.intValue()];
			for (int idx = 0; idx < dateArr.length; idx++) {
				if (idx == 0) {
					dateArr[idx] = stime;
				} else {
					stime = addOneDay(stime);
					stime = new StringBuilder().append(stime.substring(6, 10))
							.append("-").append(stime.substring(0, 2))
							.append("-").append(stime.substring(3, 5))
							.toString();

					dateArr[idx] = stime;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateArr;
	}
}
