package com.gkgio.android.eventmanager;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.gkgio.android.eventmanager.model.Event;

import java.util.List;

/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class CalendarEventsLoader extends AsyncTaskLoader<List<Event>> {

    public CalendarEventsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Event> loadInBackground() {
        return null;
    }
}
