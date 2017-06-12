package com.gkgio.android.eventmanager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.gkgio.android.eventmanager.model.Event;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class CalendarEventsLoader extends AsyncTaskLoader<List<Event>> {

    private static final String TAG = "CalendarEventsLoader";

    public CalendarEventsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    public List<Event> loadInBackground() {
        List<Event> events = new ArrayList<>();

        Cursor cursor = getEventsCursor();
        if (cursor != null) {
            EventsInflator.fillList(cursor, events);
            cursor.close();
        } else {
            Log.e(TAG, "cursor is null");
        }

        return events;
    }

    private Cursor getEventsCursor() {
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = null;
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            cursor = resolver.query(
                    CalendarContract.Events.CONTENT_URI,
                    null, null, null, null);
        }
        return cursor;
    }
}