package com.gkgio.android.eventmanager;

import android.database.Cursor;
import android.provider.CalendarContract;

import com.gkgio.android.eventmanager.model.Event;

import java.util.List;

/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class EventsInflator {

    public static void fillList(Cursor source, List<Event> target) {
        if (source.moveToFirst()) {
            while (!source.isAfterLast()) {
                target.add(createEventFromCursor(source));
                source.moveToNext();
            }
        }
    }

    private static Event createEventFromCursor(Cursor cursor) {
        Event event = new Event();
        event.setId(getLong(cursor, CalendarContract.Events._ID));
        event.setCalendarId(getLong(cursor, CalendarContract.Events.CALENDAR_ID));
        event.setDateStart(getLong(cursor, CalendarContract.Events.DTSTART));
        event.setDateEnd(getLong(cursor, CalendarContract.Events.DTEND));
        event.setTimeZone(getString(cursor,CalendarContract.Events.EVENT_TIMEZONE));
        event.setTitle(getString(cursor, CalendarContract.Events.TITLE));
        event.setDescription(getString(cursor, CalendarContract.Events.DESCRIPTION));
        return event;
    }

    private static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    private static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}