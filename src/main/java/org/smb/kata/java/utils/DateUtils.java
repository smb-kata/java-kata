package org.smb.kata.java.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
    public static SimpleDateFormat getFormatter()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter;
    }

    public static Date FloorHour(Date d)
    {
        Calendar c = new GregorianCalendar();
        c.setTime(d);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.setTimeZone(TimeZone.getTimeZone("UTC"));

        return c.getTime();

    }
}
