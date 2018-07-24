package com.yonbor.mydicapp.utils;



import com.yonbor.baselib.utils.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间工具类 除了私信使用getMessageTime外，其他都使用getDateTime方法
 * 
 * 传过来的long都是UTC时间
 * 
 * @author
 */
public class DateUtil {

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN1 = "yyyy年MM月dd日";
	public static final String PATTERN2 = "yyyy年MM月";
	public static final String PATTERN3 = "yyyy-MM-dd";
	public static final String PATTERN4 = "MM-dd";
	public static final String PATTERN5 = "yyyy.MM.dd";
	public static final String PATTERN6 = "yyyy.MM.dd HH:mm";
	public static final String PATTERN7 = "yyyy-MM-dd HH:mm";
	//add Id:none 咨询聊天 chenkai 20170916 start
	public static final String PATTERN8 = "HH:mm";
	//add Id:none 咨询聊天 chenkai 20170916 end
	public static String tmepReturnStr = "不祥";
	public static DateFormat format = new SimpleDateFormat(
			PATTERN);
	public static DateFormat format1 = new SimpleDateFormat(PATTERN1);
	public static DateFormat format2 = new SimpleDateFormat(PATTERN2);
	public static DateFormat format3 = new SimpleDateFormat(PATTERN3);
	public static DateFormat format4 = new SimpleDateFormat(PATTERN4);
	public static DateFormat format5 = new SimpleDateFormat(PATTERN5);
	public static DateFormat format6 = new SimpleDateFormat(PATTERN6);
	public static Calendar cal = Calendar.getInstance();

	public static String getDateTime(long d) {
		String result = "";
		if (d > 0) {
			result = getString(new Date(d));
		}
		return result;
	}

	public static long getDateTime(String date){
		long time=0l;
		if(date.length()>10){
			date=date.substring(0,10);
		}
		try {
			time=format3.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	
//	/**
//	 * UTC时间
//	 * 
//	 * @param d
//	 * @return
//	 */
//	public static String getDateTime(long d) {
//		String result = "";
//		if (d > 0) {
//			result = getString(new Date(d
//					+ cal.get(java.util.Calendar.ZONE_OFFSET)));
//		}
//		return result;
//	}


	/**
			*
			* @param strDate
	* @param oldFormat
	* @param newFormat
	* @return
			*/
	public static String formatDateStr(String strDate, String oldFormat, String newFormat) {
		if (strDate == null || strDate.equals("")) {
			return null;
		}
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, Locale.getDefault());
		try {
			date = sdf.parse(strDate);
			sdf.applyPattern(newFormat);
		} catch (Exception ex) {
			date = null;
			return strDate;
		}
		return sdf.format(date);

	}

	public static int getAge(long bityhday) {
		Date d1 = new Date(bityhday + cal.get(Calendar.ZONE_OFFSET));
		Date d2 = new Date();
		return d2.getYear() - d1.getYear();
	}

	//获取年龄，传递格式要为yyyy-MM-dd
	public static int getAge(String dateString) {
		if(StringUtil.isEmpty(dateString)){
			return -1;
		}
		if(dateString.length()>10){
			dateString=dateString.substring(0,10);
		}
		Date dateOfBirth = getDateTime("yyyy-MM-dd",dateString);
		if (dateOfBirth != null) {
			int age = 0;
			Calendar born = Calendar.getInstance();
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				return -1;
			} else {
				for (int i = 1; i <= Integer.MAX_VALUE; i++) {
					born.add(Calendar.YEAR, 1);
					if (born.after(now)) {
						if (i == 1) age = 0;
						else
							age = i - 1;
						break;
					}
				}
			}
			return age;
		}else{
			return -1;
		}
	}

	public static long getTime(String s) {
		try {
			Date d1 = format3.parse(s);
			return d1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

    public static boolean isOverOneDay(long date) {
        long diffSecond = (new Date().getTime() - date) / 1000L;
        return diffSecond>86400;
    }

	// 无法选择未来时间，≤now
	public static boolean check(String d) {
		try {
			Date d1 = format3.parse(d);
			Date d2 = format3.parse(format3.format(new Date()));
			return d2.getTime() >= d1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 是否小于起始时间，≤now
	public static boolean check(String startTime, String endTime) {
		try {
			Date d1 = format3.parse(startTime);
			Date d2 = format3.parse(endTime);
			return d2.getTime() < d1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 间隔是否大于1年，≤now
	public static boolean checkOutOfRange(String startTime, String endTime) {
		try {
			Date d1 = format3.parse(startTime);
			Date d2 = format3.parse(endTime);
			return Math.abs(d2.getTime() - d1.getTime())>86400000L*365;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 无法选择未来时间，≤now
	public static boolean check1(String d) {
		try {
			Date d1 = format3.parse(d);
			Date d2 = format3.parse(format3.format(new Date()));
			return d2.getTime() < d1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getDateTime1(long d) {
		String result = "";
		if (d > 0) {
			result = getString(new Date(d));
		}
		return result;
	}

	public static String getTimeByDate(String format, Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

	// 传过来的不是UTC时间
	public static String getNewHongDongDay(long d) {
		return format1.format(new Date(d));
	}

	// 返回当前的UTC时间
	public static long getNowTime() {
		return System.currentTimeMillis()
				- cal.get(Calendar.ZONE_OFFSET);
	}

	public static long getUtcTimeByStr(String str) {
		try {
			return format3.parse(str).getTime()
					- cal.get(Calendar.ZONE_OFFSET);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getBirthDateTime(long d) {
		return format3.format(new Date(d
				+ cal.get(Calendar.ZONE_OFFSET)));
	}

	public static String getDateTime(DateFormat format, long d) {
		return format.format(new Date(d));
	}

	public static String getDateTime(String format, long d) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(d));
	}

	public static Date getDateTime(String formate, String d) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formate);
			return sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String dateFormate(Date date, String formate) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(formate);
			return sdf.format(date);
		} else {
			return null;
		}

	}

	//add Id:none 咨询聊天 chenkai 20170916 start

	/**
	 * 小于一天显示 HH:mm ;前一天显示 昨天 HH:mm ;其他时间显示 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String getStrByDate(Long date){
		long curTime = System.currentTimeMillis();
		long diffSecond = (curTime - date) / 1000L;
		if (diffSecond < (24*60*60)){//当天
			return getDateTime(PATTERN8,date);
		}else if (diffSecond < (24*60*60*2)){//昨天
			return "昨天 "+getDateTime(PATTERN8,date);
		}else {
			return getDateTime(PATTERN3,date);
		}
	}
	//add Id:none 咨询聊天 chenkai 20170916 end

	// public static Date getSodaoDateByString(String d) throws ParseException {
	// format.setTimeZone(TimeZone.getTimeZone("UTC"));
	// return format.parse(d);
	// }

	/**
	 * 0秒-60秒内显示“刚刚” 1分钟-2分钟内显示“1分钟前” 1小时-2小时内显示“1小时前” 1天-2天内显示“1天前”
	 * 7天-14天显示“1周前” 30天-60天显示1月前 之后已月类推计算 ）
	 */
	public static String getString(Date date) {
		long curTime = System.currentTimeMillis();

		long diffSecond = 0L;
		long diffDay = 0L;
		diffSecond = (curTime - date.getTime()) / 1000L;

		if (diffSecond >= 86400) {// 两个相差
			Calendar curDate = new GregorianCalendar();
			curDate.setTime(new Date(curTime));
			curDate.set(Calendar.HOUR_OF_DAY, 23);
			curDate.set(Calendar.MINUTE, 59);
			curDate.set(Calendar.SECOND, 59);
			curDate.set(Calendar.MILLISECOND, 999);
			diffDay = (curDate.getTimeInMillis() - date.getTime()) / 86400000L;
			if (diffDay <= 30) {
				return diffDay + "天前";
			} else {
				if (date.getYear() == new Date().getYear()) {
					return format4.format(new Date(date.getTime()
							+ cal.get(Calendar.ZONE_OFFSET)));
				} else {
					return getBirthDateTime(date.getTime());
				}
			}
		} else {
			if (diffSecond < 0) {
				return "刚刚";
			} else if (diffSecond < 60) {
				return "刚刚";
			} else if (diffSecond < 3600) {
				return diffSecond / 60 + "分钟前";
			} else if (diffSecond < 86400) {
				return diffSecond / 3600 + "小时前";
			} else if (diffSecond < 86400) {
				return diffSecond / 3600 + "小时前";
			}
		}
		return "...";
	}

	public static long getDayTime(String d) {
		try {
			return format1.parse(d).getTime()
					- cal.get(Calendar.ZONE_OFFSET);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getMonthTime(String d) {
		try {
			return format2.parse(d).getTime()
					- cal.get(Calendar.ZONE_OFFSET);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static String[] weeks = new String[]{"周日","周一","周二","周三","周四","周五","周六"};

	/**
	 * 获取日期的 周几
	 * @param date
	 * @return
	 */
	public static String getWeek(long date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置sunday是一周的第一天
		int week = calendar.get(Calendar.DAY_OF_WEEK);//按sunday为1 saturday为7
		return weeks[week-1];
		
	}

	private static String[] weeks2 = new String[]{"日","一","二","三","四","五","六"};

	/**
	 * 获取日期的 周几
	 * @param date
	 * @return
	 */
	public static String getWeek2(long date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置sunday是一周的第一天
		int week = calendar.get(Calendar.DAY_OF_WEEK);//按sunday为1 saturday为7
		return weeks2[week-1];

	}

	/**
	 * 是否同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(long date1, long date2) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTimeInMillis(date1);

		Calendar calDateB = Calendar.getInstance();
		calDateB.setTimeInMillis(date2);

		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
				&& calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
				&& calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
				.get(Calendar.DAY_OF_MONTH);
	}

	public static void main(String args[]) {
		System.out.println(dateFormate(new Date(1448504072862l), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(dateFormate(new Date(1448504072866l), "yyyy-MM-dd HH:mm:ss"));
	}


	/**
	 * 获取两个时间差距天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetween(long startDate,long endDate){
		int diff= (int) ((endDate-startDate)/(1000*3600*24));
		return diff;
	}

}
