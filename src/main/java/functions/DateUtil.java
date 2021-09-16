package functions;

import constants.DateFormat;
import constants.TimeZones;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Get the current date in a given {@code timezone}.
     *
     * @param timezone The timezone to set (e.g., UTC).
     * @param format The expected date format (e.g., yyyy-MM-dd)
     * @return The current date in a given {@code timezone}.
     */
    public static String getCurrentDate(String timezone, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        SimpleDateFormat date = new SimpleDateFormat(format);

        date.setTimeZone(TimeZone.getTimeZone(timezone));

        String currentDate = date.format(calendar.getTime());

        return currentDate;
    }

    /**
     * Get the current date based on UTC.
     * @return The date in {@code yyyy-MM-dd_HH-mm-ss} format.
     */
    public static String getCurrentDate() {
        return getCurrentDate(TimeZones.UTC, DateFormat.YYYY_MM_DD_HH_MM_SS);
    }


}
