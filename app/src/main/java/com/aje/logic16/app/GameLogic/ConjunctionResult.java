package com.aje.logic16.app.GameLogic;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;

import com.aje.logic16.app.R;

/**
 * Created by arne on 17.04.14.
 */
public class ConjunctionResult extends Literal
{
    Drawable mRedBackground;
    Drawable mGreenBackground;

    public ConjunctionResult(ContextThemeWrapper context, LinearLayout.LayoutParams imageLayoutParams, E_LITERAL_VALUE value, int width, int height)
    {
        super(context, imageLayoutParams, value, width, height);

        mGreenBackground = getResources().getDrawable(R.drawable.rounded_corner);
        ColorFilter filter = new LightingColorFilter( Color.GREEN, Color.GREEN );
        mGreenBackground.setColorFilter(filter);

        mRedBackground = getResources().getDrawable(R.drawable.rounded_corner);
        ColorFilter redFilter = new LightingColorFilter( Color.RED, Color.RED );
        mRedBackground.setColorFilter(redFilter);

        this.setBackground(mGreenBackground);
    }

    @Override
    protected void setOutput(E_LITERAL_VALUE value)
    {
        super.setOutput(value);

        if (value == E_LITERAL_VALUE.POSITIV)
        {
            setBackground(mGreenBackground);
        }
        else if (value == E_LITERAL_VALUE.NEGATIV)
        {
            setBackground(mRedBackground);
        }
    }
}
