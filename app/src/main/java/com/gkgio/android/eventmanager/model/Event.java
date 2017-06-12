package com.gkgio.android.eventmanager.model;

import java.io.Serializable;

/**
 * Created by Георгий on 12.06.2017.
 * gkgio
 */

public class Event implements Serializable {

    private long id;
    private int calendarId;
    private String title;
    private String description;
    private int dateStart;
    private int dateEnd;

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
        return description != null ? description.equals(event.description) : event.description == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + calendarId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + dateStart;
        result = 31 * result + dateEnd;
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", calendarId=" + calendarId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }

    public void setId(long id) {

        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateStart(int dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(int dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDateStart() {
        return dateStart;
    }

    public int getDateEnd() {
        return dateEnd;
    }

    public int getCalendarId() {
        return calendarId;
    }
}
