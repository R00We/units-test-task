package units.r00we.test_task.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import units.r00we.test_task.R;

import static android.text.format.DateUtils.FORMAT_SHOW_DATE;

public class DateView extends RecyclerView.ViewHolder{

    private final TextView dateTextView;

    public DateView(View itemView) {
        super(itemView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
    }

    public void fill(@NonNull Date date){
        dateTextView.setText(DateUtils.getRelativeTimeSpanString(date.getTime(),
                System.currentTimeMillis(),
                DateUtils.DAY_IN_MILLIS, FORMAT_SHOW_DATE));

    }
}