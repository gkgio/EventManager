package com.gkgio.android.eventmanager.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gkgio.android.eventmanager.R;
import com.gkgio.android.eventmanager.common.Utils;
import com.gkgio.android.eventmanager.model.Event;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Георгий on 11.06.2017.
 * gkgio
 */

public class AddOrUpdateEventActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Event event;
    private int position;
    private long dateTimeFrom;
    private long dateTimeTo;

    private EditText etTitle;
    private EditText etDescription;
    private TextView tvCalendarFromDate;
    private TextView tvCalendarToDate;
    private Button btnSave;

    private EditText[] editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        event = (Event) getIntent().getSerializableExtra(MainActivity.INTENT_EVENT_PARAM);
        position = getIntent().getIntExtra(MainActivity.INTENT_POSITION_PARAM, -1);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        tvCalendarFromDate = (TextView) findViewById(R.id.tvCalendarFromDate);
        tvCalendarToDate = (TextView) findViewById(R.id.tvCalendarToDate);
        btnSave = (Button) findViewById(R.id.btnSave);

        editTexts = new EditText[]{etTitle, etDescription};
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcherImpl());
        }

        final Calendar calendarFrom = Calendar.getInstance(TimeZone.getDefault());
        //текущая дата и на сутки раньше по умолчанию
        if (event == null) {
            tvCalendarFromDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", calendarFrom.getTimeInMillis() - 86400000));
            tvCalendarToDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", calendarFrom.getTimeInMillis()));

            calendarFrom.setTimeInMillis(calendarFrom.getTimeInMillis() - 86400000);

            dateTimeFrom = calendarFrom.getTimeInMillis();
        } else {
            tvCalendarFromDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", event.getDateStart()));
            tvCalendarToDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", event.getDateEnd()));

            calendarFrom.setTimeInMillis(event.getDateStart());
            dateTimeFrom = event.getDateStart();
        }

        DatePickerDialog.OnDateSetListener dateListenerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker,
                                  int year, int monthOfYear, int dayOfMonth) {
                calendarFrom.set(Calendar.YEAR, year);
                calendarFrom.set(Calendar.MONTH, monthOfYear);
                calendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvCalendarFromDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", calendarFrom.getTimeInMillis()));
                dateTimeFrom = calendarFrom.getTimeInMillis();
            }
        };

        final DatePickerDialog dateDialogFrom = new DatePickerDialog(this, dateListenerFrom, calendarFrom.get(Calendar.YEAR),
                calendarFrom.get(Calendar.MONTH), calendarFrom.get(Calendar.DAY_OF_MONTH));

        tvCalendarFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialogFrom.show();
            }
        });

        final Calendar calendarTo = Calendar.getInstance(TimeZone.getDefault());

        if (event == null) {
            dateTimeTo = calendarTo.getTimeInMillis();
        } else {
            calendarTo.setTimeInMillis(event.getDateEnd());
            dateTimeTo = event.getDateEnd();
        }

        DatePickerDialog.OnDateSetListener dateListenerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker,
                                  int year, int monthOfYear, int dayOfMonth) {
                calendarTo.set(Calendar.YEAR, year);
                calendarTo.set(Calendar.MONTH, monthOfYear);
                calendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvCalendarToDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", calendarTo.getTimeInMillis()));
                dateTimeTo = calendarTo.getTimeInMillis();
            }
        };

        final DatePickerDialog dateDialogTo = new DatePickerDialog(this, dateListenerTo, calendarTo.get(Calendar.YEAR),
                calendarTo.get(Calendar.MONTH), calendarTo.get(Calendar.DAY_OF_MONTH));

        tvCalendarToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialogTo.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event localEvent = new Event();
                localEvent.setTitle(etTitle.getText().toString());
                localEvent.setDescription(etDescription.getText().toString());
                localEvent.setDateStart((int) dateTimeFrom);
                localEvent.setDateEnd((int) dateTimeTo);
                localEvent.setId(event.getId());
                localEvent.setCalendarId(event.getCalendarId());

                Intent intent = new Intent();
                intent.putExtra(MainActivity.INTENT_EVENT_PARAM, localEvent);
                intent.getIntExtra(MainActivity.INTENT_POSITION_PARAM, position);
                if (event == null) {
                    setResult(MainActivity.REQUEST_CODE_ADD);
                } else setResult(MainActivity.REQUEST_CODE_UPDATE);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (event == null)
            toolbar.setTitle(R.string.title_activity_new_event);
        else toolbar.setTitle(R.string.title_activity_update_event);
    }

    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean buttonEnabled = true;
            for (EditText editText : editTexts) {
                if (TextUtils.isEmpty(editText.getText())) {
                    buttonEnabled = false;
                    break;
                }
            }
            btnSave.setEnabled(buttonEnabled);
        }
    }
}
