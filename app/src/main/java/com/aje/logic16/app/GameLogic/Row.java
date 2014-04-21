package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.aje.logic16.app.R;

/**
 * Created by arne on 16.04.14.
 */
public class Row extends LinearLayout
{
    private Literal[] mLiterals = new Literal[GameLogic.NUM_LITERALS];

    public Row(Context widget, DisplayMetrics metrics)
    {
        super(widget);

        LinearLayout.LayoutParams LLHParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(getResources().getColor(R.color.backgroundBlue));
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.setLayoutParams(LLHParams);
    }
}
