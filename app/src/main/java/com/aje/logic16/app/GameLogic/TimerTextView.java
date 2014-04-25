package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aje.logic16.app.R;

/**
 * Created by Arne on 24.04.14.
 */
public class TimerTextView extends TextView
{
    private GameLogic mGameLogic;
    private ViewGroup mCountDownElement;
    private CountDownTimer mCountDownTimer;
    private final ProgressBar mPB;
    private final Animation mAnimation;
    private final TextView mpbText;
    private long leftSeconds;
    private final int thirtySeconds;

    public TimerTextView(Context widget, GameLogic gameLogic, int seconds)
    {
        super(widget);
        leftSeconds = seconds * 1000;
        mGameLogic = gameLogic;


        int total=100;
        thirtySeconds = 1 * seconds * 1000;


        LayoutInflater inflater = (LayoutInflater) widget.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        mCountDownElement = (ViewGroup) inflater.inflate(R.layout.count_down, null);
        mPB = (ProgressBar) mCountDownElement.findViewById(R.id.progressBar);
        mpbText = (TextView) mCountDownElement.findViewById(R.id.seconds);
        mpbText.setText(Integer.toString(Math.round((thirtySeconds / 1000))));

        mPB.setProgress(total);
        mPB.setMax(total);
        mAnimation = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
        mAnimation.setFillAfter(true);


        mCountDownTimer = generateCountdownTimer(thirtySeconds);
    }

    public ViewGroup getCountDownElement()
    {
        return mCountDownElement;
    }

    public void startTimer()
    {
        mPB.startAnimation(mAnimation);
        mCountDownTimer.start();
    }

    public void stopTimer()
    {
        mCountDownTimer.cancel();
    }

    public void pauseTimer()
    {
        mCountDownTimer.cancel();
    }

    public void resumeTimer()
    {
        mCountDownTimer = generateCountdownTimer((int)leftSeconds);
        startTimer();
    }

    private CountDownTimer generateCountdownTimer(final int seconds)
    {
        return new CountDownTimer(seconds, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                leftSeconds = millisUntilFinished;
                double un = ((double)millisUntilFinished/ (double)thirtySeconds);
                int total = (int) Math.round(un * 100);
                mPB.setProgress(total);
                mpbText.setText(Integer.toString(Math.round(millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {
                //Do what you want
                mPB.setProgress(0);
                mpbText.setText("0");
                mGameLogic.onTimeOver();
            }
        };
    }
}
