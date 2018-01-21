package es.goda87.marvelcharacterdisplay.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.characterlist.ListActivity;


public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT_MS = 2000;

    private boolean paused = false;
    private boolean autostart = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_splash);


        //  NOTE, Google recomends splash that do not waste users time,
        // that can be achieved just calling launchApp from the onCreate without delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (paused) {
                    autostart = true;
                } else {
                    launchApp();
                }
            }
        }, SPLASH_TIMEOUT_MS);

    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
        if (autostart) {
            launchApp();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    private void launchApp() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}
