package com.gkgio.android.eventmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gkgio.android.eventmanager.R;
import com.gkgio.android.eventmanager.common.Config;

/**
 * Created by Георгий on 11.06.2017.
 * gkgio
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // запускаем соответствующую активити после задержки
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMain();
                finishActivity();
            }
        }, Config.SHOW_SPLASH_DELAY_MILLIS);
    }

    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void finishActivity() {
        finish();
        // включаем анимацию при закрытии заставки - исчезновение
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
    }
}
