package com.gkgio.android.eventmanager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
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

    private EventsContentObserver eventsContentObserver;

    public CalendarEventsLoader(Context context) {
        super(context);
        eventsContentObserver = new EventsContentObserver();
        context.getContentResolver().registerContentObserver(
                CalendarContract.Events.CONTENT_URI,
                false, eventsContentObserver);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        getContext().getContentResolver().unregisterContentObserver(eventsContentObserver);
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

    private class EventsContentObserver extends ContentObserver {

        public EventsContentObserver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            onContentChanged();
        }
    }
}