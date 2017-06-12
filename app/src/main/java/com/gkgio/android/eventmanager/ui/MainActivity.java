package com.gkgio.android.eventmanager.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gkgio.android.eventmanager.CalendarEventsLoader;
import com.gkgio.android.eventmanager.R;
import com.gkgio.android.eventmanager.common.adapters.EventRecyclerAdapter;
import com.gkgio.android.eventmanager.model.Event;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_READ_REQUEST_CODE = 1;
    private static final int PERMISSION_WRITE_REQUEST_CODE = 1;
    private static final int LOADER_ID = 1;
    private static final int REQUEST_CODE_UPDATE = 12;
    private static final int REQUEST_CODE_ADD = 143;

    public static final String INTENT_EVENT_PARAM = "Event";
    public static final String INTENT_POSITION_PARAM = "Position";

    private EventRecyclerAdapter eventRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddOrUpdateEventActivity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkCallLogPermission();

        eventRecyclerAdapter = new EventRecyclerAdapter(this);
        RecyclerView rvEvents = (RecyclerView) findViewById(R.id.rvEvents);
        rvEvents.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(eventRecyclerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_READ_REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                getSupportLoaderManager().initLoader(LOADER_ID, null,
                        new CalendarEventsLoaderCallBack());
            } else showToast(R.string.toast_error_message);
        }
    }

    private void checkCallLogPermission() {
        int permissionResultRead = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR);
        int permissionResultWrite = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR);
        if ((permissionResultRead == PackageManager.PERMISSION_GRANTED) &&
                (permissionResultWrite == PackageManager.PERMISSION_GRANTED)) {
            getSupportLoaderManager().initLoader(LOADER_ID, null,
                    new CalendarEventsLoaderCallBack());
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    PERMISSION_READ_REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CALENDAR},
                    PERMISSION_WRITE_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_about:
                break;
            case R.id.nav_exit:
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showToast(int message) {
        LayoutInflater inflater = getLayoutInflater();

        View toastView = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));
        toastView.setBackgroundResource(R.drawable.toast_error_bg);

        TextView tvToast = (TextView) toastView.findViewById(R.id.tvToast);
        tvToast.setText(message);

        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.BOTTOM, 0, 64);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.show();
    }

    public void editEvent(final Event event, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_what_action_event)
                .setNeutralButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteEvent(event, position);
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.dialog_update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, AddOrUpdateEventActivity.class);
                        intent.putExtra(INTENT_EVENT_PARAM, event);
                        intent.putExtra(INTENT_POSITION_PARAM, position);
                        startActivityForResult(intent, REQUEST_CODE_UPDATE);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.dialog_nothing, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    private void deleteEvent(Event event, int position) {
        eventRecyclerAdapter.deleteEvent(position);
        final String[] selectionArgs = new String[]{Long.toString(event.getId())};
        ContentResolver contentResolver = this.getContentResolver();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            contentResolver.delete(
                    CalendarContract.Events.CONTENT_URI,
                    CalendarContract.Events._ID + " = ? ",
                    selectionArgs
            );
        }
    }

    private void startAddOrUpdateEventActivity() {
        Intent intent = new Intent(MainActivity.this, AddOrUpdateEventActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD) {
                if (data != null) {
                    final Event event = (Event) data.getSerializableExtra(INTENT_EVENT_PARAM);
                    eventRecyclerAdapter.setEvent(event);
                    insertEvent(event);
                }
            } else if (requestCode == REQUEST_CODE_UPDATE) {
                if (data != null) {
                    final Event event = (Event) data.getSerializableExtra(INTENT_EVENT_PARAM);
                    final int position = data.getIntExtra(INTENT_POSITION_PARAM, -1);
                    eventRecyclerAdapter.updateEvent(event, position);
                    updateEvent(event);
                }
            }
        }
    }

    private void insertEvent(Event event) {
        ContentResolver resolver = this.getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, event.getDateStart());
        values.put(CalendarContract.Events.DTEND, event.getDateEnd());
        values.put(CalendarContract.Events.TITLE, event.getTitle());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, event.getTimeZone());
        values.put(CalendarContract.Events.CALENDAR_ID, event.getCalendarId());
        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            resolver.insert(CalendarContract.Events.CONTENT_URI, values);
        }
    }

    private void updateEvent(Event event) {
        ContentResolver resolver = this.getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, event.getDateStart());
        values.put(CalendarContract.Events.DTEND, event.getDateEnd());
        values.put(CalendarContract.Events.TITLE, event.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            resolver.update(CalendarContract.Events.CONTENT_URI, values, CalendarContract.Events._ID + " = ?",
                    new String[]{String.valueOf(event.getId())});
        }
    }

    private class CalendarEventsLoaderCallBack implements LoaderManager.LoaderCallbacks<List<Event>> {

        @Override
        public Loader<List<Event>> onCreateLoader(int id, Bundle args) {
            return new CalendarEventsLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<Event>> loader, List<Event> data) {
            if (data != null) {
                eventRecyclerAdapter.setEvents(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Event>> loader) {

        }
    }
}