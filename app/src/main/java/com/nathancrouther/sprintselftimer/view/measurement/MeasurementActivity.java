package com.nathancrouther.sprintselftimer.view.measurement;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nathancrouther.sprintselftimer.R;
import com.nathancrouther.sprintselftimer.controller.Controller;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class MeasurementActivity extends ActionBarActivity {
    // reaction time ends when acceleration exceeds this (during first motion)
    private static final double SETTING_START_NUMBER_GS = 2.0;
    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float accelerationX = event.values[0];
            float accelerationY = event.values[1];
            float accelerationZ = event.values[2];

            double accelerationTotal = Math.sqrt(accelerationX * accelerationX +
                    accelerationY * accelerationY + accelerationZ * accelerationZ);

            final double GRAVITATIONAL_ACCELERATION = 9.8;

            if (accelerationTotal > (SETTING_START_NUMBER_GS * GRAVITATIONAL_ACCELERATION)) {
                onScreenMovedRapidly();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    // how long to wait for before saying get set
    private static final int SETTING_ON_YOUR_MARKS_DELAY_MILLISECONDS = 5000;
    // ignore motion for this many seconds to let user get set
    private static final int SETTING_GET_SET_DELAY_MILLISECONDS = 2000;
    // after runner is set, wait at least this long to start
    private static final int SETTING_BANG_FIXED_DELAY_MILLISECONDS = 1000;
    // after runner is set, wait a random amount <= this to start
    private static final int SETTING_BANG_RANDOM_DELAY_MILLISECONDS = 1000;
    private final Controller controller = new Controller();
    private final Handler handler = new Handler();
    @InjectView(R.id.measurement_stepLabel)
    TextView tvStepLabel;
    @InjectView(R.id.measurement_progressBar)
    ProgressBar pbProgressBar;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private State state = State.IDLE;
    private long bangTimeInMilliseconds;
    private long firstMovedRapidlyTimeInMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        ButterKnife.inject(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        sensorManager.registerListener(
                accelerometerListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (state == State.IDLE) {
            setStageInitial();
        }
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(accelerometerListener);

        super.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onScreenPressed();
                return true;
            case MotionEvent.ACTION_UP:
                onScreenReleased();
                return true;
        }
        return false;
    }

    private void onScreenPressed() {
        hideNavigation();

        state = State.PRESSED;
        setStageOnYourMarks();
        playOnYourMarks();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void hideNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View v = getWindow().getDecorView();
            int flags = v.getSystemUiVisibility();
            v.setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void showNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View v = getWindow().getDecorView();
            int flags = v.getSystemUiVisibility();
            v.setSystemUiVisibility(flags & ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void playOnYourMarks() {
        animateStage(SETTING_ON_YOUR_MARKS_DELAY_MILLISECONDS, new Runnable() {
            @Override
            public void run() {
                if (state == State.PRESSED) {
                    setStageGetSet();
                    playGetSet();
                }
            }
        });

        vibrator.vibrate(new long[]{0, 100, 50, 100, 50, 100}, -1);
    }

    private void playGetSet() {
        animateStage(SETTING_GET_SET_DELAY_MILLISECONDS, new Runnable() {
            @Override
            public void run() {
                state = State.SET;
                setStageStayStill();
                onRunnerSet();
            }
        });

        vibrator.vibrate(new long[]{0, 100, 50, 100}, -1);
    }

    private void animateStage(int duration, Runnable nextStageTransition) {
        final int PROGRESS_STEPS = 10;

        for (int i = 1; i <= PROGRESS_STEPS; ++i) {
            final int elapsedTicks = i;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (state == State.PRESSED) {
                        setStageProgress(elapsedTicks * 100 / PROGRESS_STEPS);
                    }
                }
            }, elapsedTicks * duration / (PROGRESS_STEPS + 1));
        }

        handler.postDelayed(nextStageTransition, duration);
    }

    private void onRunnerSet() {
        int bangDelay = SETTING_BANG_FIXED_DELAY_MILLISECONDS +
                (int) (Math.random() * SETTING_BANG_RANDOM_DELAY_MILLISECONDS);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (state == State.SET) {
                    setStageGo();
                    playBang();
                }
            }
        }, bangDelay);
    }

    private void playBang() {
        state = State.REACTING;
        vibrator.vibrate(100);
        bangTimeInMilliseconds = System.currentTimeMillis();
    }

    private void onScreenReleased() {
        showNavigation();

        long releasedTimeInMilliseconds = System.currentTimeMillis();
        long totalTimeInMilliseconds = releasedTimeInMilliseconds - bangTimeInMilliseconds;
        Long reactionTimeInMilliseconds;
        if (state == State.RUNNING) {
            reactionTimeInMilliseconds =
                    firstMovedRapidlyTimeInMilliseconds - bangTimeInMilliseconds;
            controller.onMeasurementComplete(
                    this, totalTimeInMilliseconds, reactionTimeInMilliseconds);
            setStageDone();
        } else if (state == State.REACTING) {
            controller.onMeasurementComplete(
                    this, totalTimeInMilliseconds, null);
            setStageDone();
        } else {
            if (state != State.IDLE) {
                handler.removeCallbacksAndMessages(null);
                Toast.makeText(
                        this,
                        getString(R.string.toast_SprintCancelled),
                        Toast.LENGTH_LONG).show();
                vibrator.vibrate(500);
            }
            setStageInitial();
        }

        state = State.IDLE;
    }

    private void onScreenMovedRapidly() {
        if (state == State.REACTING) {
            firstMovedRapidlyTimeInMilliseconds = System.currentTimeMillis();
            state = State.RUNNING;
        } else if (state == State.SET) {
            handler.removeCallbacksAndMessages(null);
            Toast.makeText(this, getString(R.string.toast_FalseStart), Toast.LENGTH_LONG).show();
            vibrator.vibrate(500);
            state = State.IDLE;
            setStageInitial();
        }
    }

    private void setStageProgress(int percent) {
        pbProgressBar.setProgress(percent);
    }

    private void setStageInitial() {
        tvStepLabel.setText(R.string.step_start);
        pbProgressBar.setVisibility(View.GONE);
    }

    private void setStageOnYourMarks() {
        tvStepLabel.setText(R.string.step_onYourMark);
        pbProgressBar.setVisibility(View.VISIBLE);
        setStageProgress(0);
    }

    private void setStageGetSet() {
        tvStepLabel.setText(R.string.step_getSet);
        pbProgressBar.setVisibility(View.VISIBLE);
        setStageProgress(0);
    }

    private void setStageStayStill() {
        tvStepLabel.setText(R.string.step_stayStill);
        pbProgressBar.setVisibility(View.GONE);
    }

    private void setStageGo() {
        tvStepLabel.setText(R.string.step_go);
        pbProgressBar.setVisibility(View.GONE);
    }

    private void setStageDone() {
        tvStepLabel.setText("");
        pbProgressBar.setVisibility(View.GONE);
    }

    private static enum State {
        IDLE,
        PRESSED,
        SET,
        REACTING,
        RUNNING
    }
}
