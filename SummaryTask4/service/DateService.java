package ua.nure.orlovskyi.SummaryTask4.service;


import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.time.temporal.ChronoUnit;

public class DateService {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static LocalDate convert(String text) {
		LocalDate date = LocalDate.parse(text, formatter);
		return date;
	}


	/**
	 * Get a diff between two dates
	 * 
	 * @param date1
	 *            the oldest date
	 * @param date2
	 *            the newest date
	 * @param timeUnit
	 *            the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(String oldest, String newest, TimeUnit timeUnit) {
		LocalDate date1 = convert(oldest);
		LocalDate date2 = convert(newest);
		return ChronoUnit.DAYS.between(date1, date2);
	}
}
