package com.teak.blog.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.teak.blog.utils.TimeDependentConstant.*;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author:
 * @Date: 2023/4/21 11:12
 * @Project: crm
 * @File: TeakUtils.java
 * @Description: 时间封装工具
 */
@Component
public class TimeUtils {


    public Date getDate(Date date) {
        String strDate = dateToStringFormat(date);
        return getDate(strDate);
    }

    /**
     * 获取时间Date
     */
    private Date getDate(String time) {
        return parseDate(time);
    }

    /**
     * 格式化字符串时间
     */
    public Date parseDate(String stringDate) {
        try {
            if (Pattern.matches(REGEX_YEAR_MONTH, stringDate)) {
                return new SimpleDateFormat(PATTERN_YEAR_MONTH).parse(stringDate);
            } else if (Pattern.matches(REGEX_DATE, stringDate)) {
                return new SimpleDateFormat(PATTERN_DATE).parse(stringDate);
            } else {
                return new SimpleDateFormat(PATTERN_DATETIME).parse(stringDate);
            }
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败: " + stringDate, e); // 保留原始异常
        }
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 格式日期为String类型
     */
    public String dateToStringFormat(Date date) {
        return DATE_FORMAT_CACHE.get().format(date);
    }
    public String dateToStringFormat(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }


    public String yearMonth(Date date) {
        return new SimpleDateFormat(PATTERN_YEAR_MONTH).format(date);
    }

    /**
     * 增加天数
     */
    public Date increaseDays(Date date, Integer days) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 增加月数
     */
    public Date increaseMonths(String time, Integer months) {
        Date date = parseDate(time);
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 增加月数
     */
    public Date increaseMonths(Date date, Integer months) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 增加季度
     */
    public Date increaseQuarter(Date date, Integer quarters) {
        return
                quarters >= 0 ? increaseMonths(date
                        , ((quarters - 1) * 3) + (4 - monthOfQuarter(date)))
                        : increaseMonths(date, -(((quarters + 1) * 3) + monthOfQuarter(date)) - 2);

    }

    /**
     * 增加年数
     */
    public Date increaseYears(String strDate, Integer years) {
        return increaseYears(parseDate(strDate), years);
    }

    public Date increaseYears(Date date, Integer years) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.YEAR, years);

        // 修正后的闰年处理逻辑
        int newYear = calendar.get(Calendar.YEAR);
        if (isFebruary29th(date) && !isLeapYear(calendar.getTime())) {
            calendar.set(Calendar.DAY_OF_MONTH, 28);
        }
        return calendar.getTime();
    }

    private boolean isFebruary29th(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.MONTH) == Calendar.FEBRUARY
                && calendar.get(Calendar.DAY_OF_MONTH) == 29;
    }

    /**
     * 给定时间获取年
     */
    public int getYear(String stringDate) {
        Date date = parseDate(stringDate);
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.YEAR);
    }

    public int getYear(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间是第几个季度的
     */
    public int getQuarter(Date date) {
        int month = getMonth(date);
        return (month + 2) / 3;
    }

    public int getQuarter(String strDate) {
        Date date = parseDate(strDate);
        int month = getMonth(date);
        return (month + 2) / 3;
    }

    public int getMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getMonth(String time) {
        Date date = parseDate(time);
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期在一个月内是第几周
     */
    public int getWeekInMonth(Date date) {
        Calendar calendar = getCalendar(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int weeks = 0;
        while (true) {
            if (getDayInMonth(getStartOfWeek_inMonth(calendar.getTime())) <= day && day <= getDayInMonth(getEndOfWeek_inMonth(calendar.getTime()))) {
                return weeks + 1;
            }
            calendar.setTime(increaseDays(calendar.getTime(), 8 - dayOfTheWeek(calendar.getTime())));
            weeks++;
        }
    }

    public int getWeekInMonth(String StringDate) {
        Date date = parseDate(StringDate);
        Calendar calendar = getCalendar(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int weeks = 0;
        while (true) {
            if (getDayInMonth(getStartOfWeek_inMonth(calendar.getTime())) <= day && day <= getDayInMonth(getEndOfWeek_inMonth(calendar.getTime()))) {
                return weeks + 1;
            }
            calendar.setTime(increaseDays(calendar.getTime(), 8 - dayOfTheWeek(calendar.getTime())));
            weeks++;
        }
    }

    /**
     * 获取当月日期
     */
    public int getDayInMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayInMonth(String StringDate) {
        Date date = parseDate(StringDate);
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayInYear(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public int getDayInYear(String StringDate) {
        Date date = parseDate(StringDate);
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }


    /**
     * 获取指定日期的周一
     */
    public String getStartOfWeek(String stringDate) {
        Calendar calendar = Calendar.getInstance();
        Date date = parseDate(stringDate);
        calendar.setTime(date);
        int i = dayOfTheWeek(date);
        if (i == 1) {
            return dateToStringFormat(date);
        }
        date = increaseDays(date, -i + 1);
        return dateToStringFormat(date);
    }

    /**
     * 已周一开始计算第几周,在一年里面
     *
     * @param date
     * @return
     */
    public int calculateTheWeekFromMondayOnwardsInYear(Date date) {
        Calendar calendar = getCalendar(date);
        if (isEquals(calendar.getTime(), parseDate(calendar.get(Calendar.YEAR) + "-12-31"))) {
            calendar.setTime(increaseDays(calendar.getTime(), -1));
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public String getStartOfWeek(Date date) {
        Calendar calendar = getCalendar(date);
        int i = dayOfTheWeek(date);
        if (i == 1) {
            return dateToStringFormat(date);
        }
        date = increaseDays(date, -i + 1);
        return dateToStringFormat(date);
    }

    public String getEndOfWeek(String stringDate) {
        Date date = parseDate(stringDate);
        int i = dayOfTheWeek(date);
        if (i == 7) {
            return dateToStringFormat(date);
        }
        date = increaseDays(date, 7 - i);
        return dateToStringFormat(date);
    }


    public String getEndOfWeek(Date date) {
        Calendar calendar = getCalendar(date);
        int i = dayOfTheWeek(date);
        if (i == 7) {
            return dateToStringFormat(date);
        }
        date = increaseDays(date, 7 - i);
        return dateToStringFormat(date);
    }

    /**
     * 获取本周开头，不能跨月
     */
    public String getStartOfWeek_inMonth(Date date) {
        Date tempDate = date;
        Calendar calendar = getCalendar(date);
        int day = getDayInMonth(tempDate);
        if (day == 1) {
            return dateToStringFormat(tempDate);
        }
        int i = dayOfTheWeek(tempDate);
        if (i == 1) {
            return dateToStringFormat(tempDate);
        }
        tempDate = increaseDays(tempDate, -i + 1);
        if (getMonth(date) != getMonth(tempDate)) {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return dateToStringFormat(calendar.getTime());
        }
        return dateToStringFormat(tempDate);
    }

    /**
     * 获取本周本周结尾，不能跨月
     */
    public String getEndOfWeek_inMonth(Date date) {
        Date tempDate = date;
        Calendar calendar = getCalendar(date);
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == getDayInMonth(tempDate)) {
            return dateToStringFormat(tempDate);
        }
        int i = dayOfTheWeek(tempDate);
        if (i == 7) {
            return dateToStringFormat(tempDate);
        }
        tempDate = increaseDays(tempDate, 7 - i);
        if (getMonth(date) != getMonth(tempDate)) {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return dateToStringFormat(calendar.getTime());
        }
        return dateToStringFormat(tempDate);
    }

    /**
     * 给一个时间 2023-7-8 返回月初
     */
    public String getStartOfMonth(String stringDate) {
        Date date = parseDate(stringDate);
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dateToStringFormat(calendar.getTime());
    }

    public String getStartOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dateToStringFormat(calendar.getTime());
    }

    /**
     * 给一个时间 2023-7-8 返回月末
     */
    public String getEndOfMonth(String stringDate) {
        Date date = parseDate(stringDate);
        Calendar calendar = getCalendar(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        return dateToStringFormat(calendar.getTime());
    }

    public String getEndOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        return dateToStringFormat(calendar.getTime());
    }

    public String getStartQuarter(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, (getQuarter(date) * 3) - 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dateToStringFormat(calendar.getTime());
    }

    /**
     * 给一个时间，获取该季度的最后一个月的最后一天
     */
    public String getEndQuarter(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, (getQuarter(date) * 3) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStringFormat(calendar.getTime());
    }

    public String getEndQuarter(String stringDate) {
        Date date = parseDate(stringDate);
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, (getQuarter(date) * 3) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStringFormat(calendar.getTime());
    }

    /**
     * 获取给定时间的一年中的第一个月的第一天
     */
    public String getStartYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateToStringFormat(calendar.getTime());
    }

    public String getStartYear(String strDate) {
        Date date = parseDate(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateToStringFormat(calendar.getTime());
    }

    public String getEndYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStringFormat(calendar.getTime());
    }

    public String getEndYear(String strDate) {
        Date date = parseDate(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStringFormat(calendar.getTime());
    }

    /**
     * 计算月是季度的第几个月
     */
    public int monthOfQuarter(Date date) {
        int quarterMonth = getMonth(getStartQuarter(date));
        int month = getMonth(date);
        return (month - quarterMonth) + 1;
    }


    /**
     * 当前日期在当前月的第几天
     */
    public int dayOfTheMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当前日期在当前周的第几天
     */
    public int dayOfTheWeek(Date date) {
        Calendar calendar = getCalendar(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        i = i - 1;
        if (i == 0) {
            return 7;
        }
        return i;
    }

    /**
     * 计算一个月有几周
     * 从周末开始计算
     */
    public int weekOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 计算这个月有几周
     * 从周一开始计算
     * 不满一周也计算为一周
     */
    public int getWeekNumOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int month = getMonth(date);
        int week = 0;
        do {
            int num = dayOfTheWeek(calendar.getTime());
            calendar.setTime(increaseDays(calendar.getTime(), 8 - num));
            week++;
        } while (getMonth(calendar.getTime()) == month);
        return week;
    }

    /**
     * 传入日期 2023-04-01或者2023-04计算当月有多少天
     */
    public Integer daysOfTheMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断传入的日期是否相等
     */
    public boolean isEquals(Date date1, Date date2) {
        int i = compareTo(date1, date2);
        return i == 0;
    }

    /**
     * 两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public Long differenceInDaysAndTwoDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        long timeInMillis1 = cal1.getTimeInMillis();
        long timeInMillis2 = cal2.getTimeInMillis();
        long diffInMillis = Math.abs(timeInMillis1 - timeInMillis2);
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断目标时间是否在制定区间内
     *
     * @param startDate  日期前
     * @param targetDate 需要判断日期
     * @param endDate    日期后
     * @return 返回true或false
     */
    public boolean isBetween(Date startDate, Date targetDate, Date endDate) {
        if (isEquals(targetDate, startDate) || isEquals(targetDate, endDate)) {
            return true;
        } else return targetDate.after(startDate) && targetDate.before(endDate);
    }

    /**
     * 判断年是否为闰年
     *
     * @param date 时间2023-1-6
     * @return 是闰年返回true否则返回false
     */
    public boolean isLeapYear(Date date) {
        int year = getYear(date);
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int compareTo(Date date1, Date date2) {
        Calendar calendar = getCalendar(date1);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date2);
        return calendar.compareTo(instance);
    }

    /**
     * 判断星期是否跨月
     *
     * @param date
     * @return
     */
    public Week getWeek(Date date) {
        Week weekClass = new Week();
        int week = getWeekNumOfMonth(date);
        String endOfMonth = getEndOfMonth(date);
        int i = dayOfTheWeek(parseDate(endOfMonth));
        if (i != 7) {
            week = week - 1;
            weekClass.setWeek(week);
            weekClass.setFlagCrossMonth(true);
        }
        return weekClass;
    }

    /**
     * 判断传入日期是否跨月
     *
     * @param date
     * @return
     */
    public Week judgingCrossMonth(Date date) {
        Calendar calendar = getCalendar(date);
        Week week = new Week();
        int endOfMonth = getMonth(getEndOfWeek(date));
        int startOfMonth = getMonth(getStartOfWeek(date));
        int month = calendar.get(Calendar.MONTH) + 1;
        if ((month != startOfMonth) || (month != endOfMonth)) {
            week.setFlagCrossMonth(true);
        }
        return week;
    }

    @Data
    public static class Week {
        private int week;
        private boolean flagCrossMonth;
    }

}
