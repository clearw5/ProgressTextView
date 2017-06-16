package com.stardust.widget.progresstextview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.stardust.widget.ProgressTextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressTextView mProgressTextView;
    private Timer mTimer = new Timer();
    private TimerTask mProgressTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressTextView = (ProgressTextView) findViewById(R.id.ptv);
        mProgressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProgressTextView.getText().equals(getString(R.string.download))) {
                    if (mProgressTextView.getProgress() == 100) {
                        mProgressTextView.setProgress(0);
                    }
                    mProgressTimerTask = newProgress();
                    mTimer.schedule(mProgressTimerTask, 0, 50);
                    mProgressTextView.setText(R.string.pause);
                } else {
                    mProgressTimerTask.cancel();
                    mProgressTextView.setText(R.string.download);
                }
            }
        });
    }

    private TimerTask newProgress() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int p = mProgressTextView.getProgress();
                        Log.d(LOG_TAG, "Progress: " + p);
                        if (p == 100) {
                            cancel();
                            mProgressTextView.setText(R.string.download);
                        } else {
                            mProgressTextView.setProgress(p + 1);
                        }
                    }
                });
            }
        };
    }

}
