package com.gkgio.android.eventmanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gkgio.android.eventmanager.R;
import com.gkgio.android.eventmanager.model.Event;

/**
 * Created by Георгий on 11.06.2017.
 * gkgio
 */

public class AddOrUpdateEventActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Event event;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        event = (Event) getIntent().getSerializableExtra(MainActivity.INTENT_EVENT_PARAM);
        position = getIntent().getIntExtra(MainActivity.INTENT_POSITION_PARAM);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (event == null)
            toolbar.setTitle(R.string.title_activity_new_event);
        else toolbar.setTitle(R.string.title_activity_update_event);
    }

}
