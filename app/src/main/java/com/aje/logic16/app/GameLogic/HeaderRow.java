package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Arne on 24.04.14.
 */
public class HeaderRow extends Row
{
    private TextView mScoreText;
    private TimerTextView mCountDown;

    public HeaderRow(Context widget, DisplayMetrics metrics, GameLogic gameLogic)
    {
        super(widget, metrics);

        createTexts(widget, metrics, gameLogic);
    }

    private void createTexts(Context widget, DisplayMetrics metrics, GameLogic gameLogic)
    {
        int imageHeight = metrics.heightPixels / (GameLogic.NUM_CONJUNCTIONS + 1 + 5) ; // 16 imageRows, + change Button (1) + some place (5)

        mScoreText = new TextView(widget);
        mScoreText.setText("Score: " + GameScore.getInstance().getScore());

        //mScoreText.setTextSize(30);
        int textSize = metrics.heightPixels / (GameLogic.NUM_CONJUNCTIONS + 1 + 5) /2 ; // 16 imageRows, + change Button (1) + some place (5)
        mScoreText.setTextSize(textSize);
        LinearLayout.LayoutParams CScoreParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mScoreText.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.LEFT);
        mScoreText.setMinimumWidth((int) (metrics.widthPixels * 0.6));
        this.addView(mScoreText,CScoreParams);

        mCountDown = new TimerTextView(widget, gameLogic);
        LinearLayout.LayoutParams CCTParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.addView(mCountDown.getCountDownElement(), CCTParams);
    } 
    public void startTimer()
    {
        mCountDown.startTimer();
    }

    public void stopTimer()
    {
        mCountDown.stopTimer();
    }
}
