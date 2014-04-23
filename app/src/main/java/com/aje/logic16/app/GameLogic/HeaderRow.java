package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Arne on 24.04.14.
 */
public class HeaderRow extends Row
{
    private TextView mScoreText;

    public HeaderRow(Context widget, DisplayMetrics metrics)
    {
        super(widget, metrics);

        createTexts(widget, metrics);
    }

    private void createTexts(Context widget, DisplayMetrics metrics)
    {
        Row headerRow = new Row(widget, metrics);
        this.addView(headerRow);

        int imageHeight = metrics.heightPixels / (GameLogic.NUM_CONJUNCTIONS + 1 + 5) ; // 16 imageRows, + change Button (1) + some place (5)

        mScoreText = new TextView(widget);
        mScoreText.setText("Score: " + GameScore.getInstance().getScore());

        //mScoreText.setTextSize(30);
        int textSize = metrics.heightPixels / (GameLogic.NUM_CONJUNCTIONS + 1 + 5) /2 ; // 16 imageRows, + change Button (1) + some place (5)
        mScoreText.setTextSize(textSize);

        headerRow.addView(mScoreText);
    }
}
