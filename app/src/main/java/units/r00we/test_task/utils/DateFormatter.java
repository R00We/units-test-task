package units.r00we.test_task.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

}
