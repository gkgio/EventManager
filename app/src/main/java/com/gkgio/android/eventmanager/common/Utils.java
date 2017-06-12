package com.gkgio.android.eventmanager.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class Utils {

    public static String getStringDateFromLong(int time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}
