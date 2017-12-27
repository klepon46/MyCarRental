package com.fatin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.zkoss.zk.ui.Executions;

public class AdrStringUtil {

	public static String wrapPercent(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(string);
		sb.append("%");

		return sb.toString();
	}

	public String addCompanyName(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append(string);
		sb.insert(0, "ptadaro\\");

		return sb.toString();
	}

	public String clobToString(Clob data) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);

			String line;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
			br.close();
		} catch (SQLException e) {
			// handle this exception
		} catch (IOException e) {
			// handle this exception
		}
		return sb.toString();
	}

	public static String generateId() {

		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMyydd");

		if (timePosition() == 0) {
			date = DateUtils.addDays(new Date(), -1);
		} else {
			date = new Date();
		}

		return sdf.format(date).toUpperCase();
	}

	public static String generateId(Date inputDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("MMMyydd");
		return sdf.format(inputDate).toUpperCase();
	}

	public static String generateIdSecond() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmss");

		return sdf.format(date);
	}

	public static String generateIdSecond(Date inputDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmssS");

		return sdf.format(inputDate);
	}

	public static int timePosition() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour >= 0 && hour <= 5) {
			return 0;
		} else {
			return 1;
		}
	}

	public static List<Date> getNextWeekCount() {
		List<Date> dates = new ArrayList<Date>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(Calendar.DATE, -i + 8);

		Date start = cal.getTime();

		cal.add(Calendar.DATE, 6);
		Date end = cal.getTime();

		Calendar starto = Calendar.getInstance();
		starto.setTime(start);

		Calendar endto = Calendar.getInstance();
		endto.setTime(end);

		for (Date dateto = starto.getTime(); !starto.after(endto); starto.add(
				Calendar.DATE, 1), dateto = starto.getTime()) {
			dates.add(dateto);
		}

		return dates;
	}

	public static String generateIdPlan() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(Calendar.DATE, -i + 8);

		Date start = cal.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("MMMyy");
		String idPlan = sdf.format(start);

		int week = cal.get(Calendar.WEEK_OF_MONTH);
		idPlan = idPlan + "W" + week;

		return idPlan.toUpperCase();
	}

	public static String generateIdPlan(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setMinimalDaysInFirstWeek(1);
		Date start = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMyy");
		String idPlan = sdf.format(start);

		int week = cal.get(Calendar.WEEK_OF_MONTH);
		idPlan = idPlan + "W" + week;

		return idPlan.toUpperCase();
	}

	public static Date calculateDate() {

		Date date;

		int time = AdrStringUtil.timePosition();

		if (time == 1) {
			date = new Date();
		} else {
			date = DateUtils.addDays(new Date(), -1);
		}
		return date;
	}

	/**
	 * mengembalikan List String yang berisikan jam dari jam 6 pagi sampai jam 6
	 * pagi di hari berikutnya
	 * 
	 * @param shift
	 *            1 = pagi , 2 = malam
	 * @return List<String>
	 */
	public static List<String> populateListTime(int shift) {
		List<String> l = new ArrayList<String>();

		if (shift == 1) {
			int x = 6;

			for (int i = 0; i < 12; i++) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, x + i++);
				Date date1 = cal.getTime();

				cal.set(Calendar.HOUR_OF_DAY, x + i);
				Date date2 = cal.getTime();

				SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
				String sDate = sdf.format(date1);
				String sDate2 = sdf.format(date2);

				String gab = sDate + " - " + sDate2;
				l.add(gab);

				i--;
			}
		} else {

			int x = 18;
			for (int i = 0; i < 12; i++) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, x + i++);
				Date date1 = cal.getTime();

				cal.set(Calendar.HOUR_OF_DAY, x + i);
				Date date2 = cal.getTime();

				SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
				String sDate = sdf.format(date1);
				String sDate2 = sdf.format(date2);

				String gab = sDate + " - " + sDate2;
				l.add(gab);

				i--;
			}
		}

		return l;
	}

	// Menghitung Perbedaan Menit diantara dua waktu
	public static int minutesDifference(Date startMinute, Date endMinute)
			throws ParseException {

		long diffInMillis = 0L;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String startString = sdf.format(startMinute);
		String stopStirng = sdf.format(endMinute);

		startMinute = sdf.parse(startString);
		endMinute = sdf.parse(stopStirng);

		if (startMinute != null && endMinute != null) {
			diffInMillis = endMinute.getTime() - startMinute.getTime();
			diffInMillis = diffInMillis / 60000;

			if (diffInMillis < 0) {
				// 24 Jam di konversi ke dalam menit
				long totalDayHour = 24 * 60;

				diffInMillis = diffInMillis + totalDayHour;
			}
		}

		return (int) diffInMillis;
	}

	// untuk mengecek shift malam atau tidak
	public static int checkShift() {

		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour >= 19 || hour <= 7) {
			// shift malam return 2
			return 2;
		} else {
			// shift pagi return 1
			return 1;
		}

	}

	// mengecek shift berdasarkan parameter
	public static int calculateShift(String hour) {

		Integer i = Integer.valueOf(hour.substring(0, 2));

		if (i >= 18 || i <= 5) {
			// shift malam return 2
			return 2;
		} else {
			// shift pagi return 1
			return 1;
		}

	}

	/**
	 * Method yang Digunakan Untuk menghitung sumProduct
	 * 
	 * @param net
	 * @param cv
	 * @return BigDecimal
	 */
	public static BigDecimal sumProduct(List<BigDecimal> net,
			List<BigDecimal> cv) {
		BigDecimal sumTonnage = new BigDecimal(0);
		BigDecimal tonMultiCv = new BigDecimal(0);

		for (int i = 0; i < net.size(); i++) {
			sumTonnage = sumTonnage.add(net.get(i));

			tonMultiCv = tonMultiCv.add(net.get(i).multiply(cv.get(i)));
		}

		return tonMultiCv.divide(sumTonnage, RoundingMode.HALF_UP);
	}

	public static String getCurrentUser() {
		return (String) Executions.getCurrent().getSession()
				.getAttribute("userName");
	}
}
