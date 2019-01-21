package units.r00we.test_task.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    private final Locale current;
    private final DateFormat timeFormatter;

    public DateFormatter(Locale current) {
        this.current = current;
        timeFormatter = new SimpleDateFormat("HH:mm", current);
    }

    public String getHMFromeDate(Date date) {
        return timeFormatter.format(date);
    }

    public Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
