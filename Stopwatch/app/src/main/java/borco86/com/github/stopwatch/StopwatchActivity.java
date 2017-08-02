package borco86.com.github.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StopwatchActivity extends AppCompatActivity {

    public static final String SECONDS_COUNTER = "secondsCounter";
    public static final String IS_RUNNING = "isRunning";
    public static final String WAS_RUNNING = "wasRunning";
    @BindView(R.id.timer_text_view)
    TextView timerTextView;

    private boolean isRunning = false;
    private int secondsCounter = 0;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        ButterKnife.bind(this);
        runTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            isRunning = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }

    @OnClick(R.id.start_button)
    void onStartButtonClick() {
        isRunning = true;
    }

    @OnClick(R.id.stop_button)
    void onStopButtonClick() {
        isRunning = false;
    }

    @OnClick(R.id.reset_button)
    void onResetButtonClick() {
        isRunning = false;
        secondsCounter = 0;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int seconds = secondsCounter % 60;
                int minutes = (secondsCounter % 3600) / 60;
                int hours = secondsCounter / 3600;
                String time = String.format("%d:%02d:%02d", hours, minutes, seconds);
                timerTextView.setText(time);
                if (isRunning) {
                    secondsCounter++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SECONDS_COUNTER, secondsCounter);
        outState.putBoolean(IS_RUNNING, isRunning);
        outState.putBoolean(WAS_RUNNING, wasRunning);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.secondsCounter = savedInstanceState.getInt(SECONDS_COUNTER);
        this.isRunning = savedInstanceState.getBoolean(IS_RUNNING);
        this.wasRunning = savedInstanceState.getBoolean(WAS_RUNNING);
    }
}
