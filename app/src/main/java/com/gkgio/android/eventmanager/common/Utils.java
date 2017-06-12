package com.gkgio.android.eventmanager.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class Utils {

    public static String formatDateTime(String format, long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        final String formattedDate = dateFormat.format(new Timestamp(timestamp));

        // возвращаем с заглавной первой буквой в дне недели
        return formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
    }
}
