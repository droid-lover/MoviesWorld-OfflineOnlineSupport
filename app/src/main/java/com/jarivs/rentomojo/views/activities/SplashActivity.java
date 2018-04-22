package com.jarivs.rentomojo.views.activities;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.VideoView;


import com.jarivs.rentomojo.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends Activity {

    private int SPLASH_TIME_OUT = 5100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Utils.hideStatusBar(SplashActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initializeView();


    }

    private void initializeView() {


        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                          startActivity(intent);

                                      }

                                  }
                , SPLASH_TIME_OUT);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
