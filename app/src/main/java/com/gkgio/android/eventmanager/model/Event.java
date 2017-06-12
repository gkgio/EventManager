package com.gkgio.android.eventmanager.model;

import java.io.Serializable;

/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class Event implements Serializable {

    private long id;
    private long calendarId;
    private String title;
    private String description;
    private String timeZone;
    private long dateStart;
    private long dateEnd;

    public Event() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (calendarId != event.calendarId) return false;
        if (dateStart != event.dateStart) return false;
        if (dateEnd != event.dateEnd) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null)
            return false;
        return timeZone != null ? timeZone.equals(event.timeZone) : event.timeZone == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (calendarId ^ (calendarId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        result = 31 * result + (int) (dateStart ^ (dateStart >>> 32));
        result = 31 * result + (int) (dateEnd ^ (dateEnd >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", calendarId=" + calendarId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getId() {
        return id;
    }

    public long getCalendarId() {
        return calendarId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public long getDateStart() {
        return dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }
}
