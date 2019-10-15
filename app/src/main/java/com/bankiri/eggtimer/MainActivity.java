package com.bankiri.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    Button buttonPressed;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;
    boolean pressedButton = false;

    public void TimeView(int seconds) {
        int minutes = seconds/60;
        seconds -= (minutes*60);

        String secondString = Integer.toString(seconds);

        if (secondString.length() == 1) {
            secondString = "0"+secondString;
        }

        textView.setText(String.format("%d:%s", minutes, secondString));
    }

    public void resetTimer(){
        pressedButton = false;
        buttonPressed.setText("Go!");
        seekBar.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
    }

    public void startRun(View view) {
        if(pressedButton){
            resetTimer();
        } else {
            pressedButton = true;
            buttonPressed.setText("Stop!");
            seekBar.setVisibility(View.INVISIBLE);
            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000, 1000){
                public void onTick(long millisecondsUntilDone){
                    TimeView((int) millisecondsUntilDone/1000);
                }
                public void onFinish(){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tolling_bell);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPressed = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        // set the seek bar up
        int startingTimer = 30;
        seekBar = findViewById(R.id.seekBar);

        seekBar.setMax(600);
        seekBar.setProgress(startingTimer);
        TimeView(startingTimer);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TimeView(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
