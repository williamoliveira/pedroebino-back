package com.pin2.pedrobino.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {

    public static Date createDate(String dateString, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date createDate(String dateString) {
        return createDate(dateString, "dd/MM/yyyy HH:mm");
    }

    public static Date plusSeconds(Date date, long amount) {
        return plus(date, ChronoUnit.SECONDS, amount);
    }

    public static Date plusDays(Date date, long amount) {
        return plus(date, ChronoUnit.DAYS, amount);
    }

    public static Date plusHours(Date date, long amount) {
        return plus(date, ChronoUnit.HOURS, amount);
    }

    public static Date minusDays(Date date, long amount) {
        return minus(date, ChronoUnit.DAYS, amount);
    }

    public static Date plus(Date date, ChronoUnit unit, long amount) {
        LocalDateTime ldt = dateToLocalDateTime(date);
        ldt = ldt.plus(amount, unit);

        return localDateTimeToDate(ldt);
    }

    public static Date minus(Date date, ChronoUnit unit, long amount) {
        LocalDateTime ldt = dateToLocalDateTime(date);
        ldt = ldt.minus(amount, unit);

        return localDateTimeToDate(ldt);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
