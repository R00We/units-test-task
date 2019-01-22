package units.r00we.test_task.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import static android.text.format.DateUtils.*;

public class FindFormat {

    public static void find(long date, Context context){
        Log.d("FindFormat", "default -"+DateUtils.formatDateTime(context, date, 0));
        Log.d("FindFormat", "FORMAT_SHOW_TIME -"+DateUtils.formatDateTime(context, date, FORMAT_SHOW_TIME));
        Log.d("FindFormat", "FORMAT_SHOW_WEEKDAY -"+DateUtils.formatDateTime(context, date, FORMAT_SHOW_WEEKDAY));
        Log.d("FindFormat", "FORMAT_SHOW_YEAR -"+DateUtils.formatDateTime(context, date, FORMAT_SHOW_YEAR));
        Log.d("FindFormat", "FORMAT_NO_YEAR -"+DateUtils.formatDateTime(context, date, FORMAT_NO_YEAR));
        Log.d("FindFormat", "FORMAT_SHOW_DATE -"+DateUtils.formatDateTime(context, date, FORMAT_SHOW_DATE));
        Log.d("FindFormat", "FORMAT_NO_YEAR -"+DateUtils.formatDateTime(context, date, FORMAT_NO_MONTH_DAY));
        Log.d("FindFormat", "FORMAT_NO_MONTH_DAY -"+DateUtils.formatDateTime(context, date, FORMAT_NO_YEAR));
        Log.d("FindFormat", "FORMAT_12HOUR -"+DateUtils.formatDateTime(context, date, FORMAT_12HOUR));
        Log.d("FindFormat", "FORMAT_CAP_AMPM -"+DateUtils.formatDateTime(context, date, FORMAT_CAP_AMPM));
        Log.d("FindFormat", "FORMAT_NO_NOON -"+DateUtils.formatDateTime(context, date, FORMAT_NO_NOON));
        Log.d("FindFormat", "FORMAT_CAP_NOON -"+DateUtils.formatDateTime(context, date, FORMAT_CAP_NOON));
        Log.d("FindFormat", "FORMAT_NO_MIDNIGHT -"+DateUtils.formatDateTime(context, date, FORMAT_NO_MIDNIGHT));
        Log.d("FindFormat", "FORMAT_CAP_MIDNIGHT -"+DateUtils.formatDateTime(context, date, FORMAT_CAP_MIDNIGHT));

    }
}
