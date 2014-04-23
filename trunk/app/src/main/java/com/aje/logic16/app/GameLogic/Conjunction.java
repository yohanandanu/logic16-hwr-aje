package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aje.logic16.app.R;

import org.apache.http.conn.ConnectionKeepAliveStrategy;

/**
 * Created by arne on 14.04.14.
 */
public class Conjunction extends Row
{
    private Literal[] mLiterals = new Literal[GameLogic.NUM_LITERALS];
    private ConjunctionResult mResult = null;

    public Conjunction(Context widget, DisplayMetrics metrics)
    {
        super(widget, metrics);

        createLiterals(metrics);
    }

    private void createLiterals(DisplayMetrics metrics)
    {
        int imageWidth = metrics.widthPixels / (GameLogic.NUM_LITERALS + 1 + 2); // 8 images + 1 result image + some place (2)
        int imageHeight = metrics.heightPixels / (GameLogic.NUM_CONJUNCTIONS + 1 + 5); // 16 imageRows, + change Button (1) + some place (5)

        ContextThemeWrapper rectangleImageViewContext = new ContextThemeWrapper(getContext(), R.style.rectangleImageView);

        LinearLayout.LayoutParams imageLayoutParams = getLiteralLayout(metrics);

        for (int j=0;j < GameLogic.NUM_LITERALS;j++)
        {
            mLiterals[j] = new Literal(rectangleImageViewContext, imageLayoutParams, E_LITERAL_VALUE.POSITIV, imageWidth, imageHeight);
            this.addView(mLiterals[j]);
        }

        mResult = new ConjunctionResult(rectangleImageViewContext, imageLayoutParams, E_LITERAL_VALUE.POSITIV, imageWidth, imageHeight);
        this.addView(mResult);
    }

    protected LinearLayout.LayoutParams getLiteralLayout(DisplayMetrics metrics)
    {
        int literalSpacing = (int)Math.round(metrics.density*2);

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.setMargins(literalSpacing, literalSpacing, literalSpacing, literalSpacing);

        return imageLayoutParams;
    }

    public Literal getLiteral(int index)
    {
        if (index >= 0 && index < mLiterals.length)
            return mLiterals[index];
        return null;
    }

    public boolean isSolved(Assignment playerAssignment)
    {
        for (int literalIndex = 0; literalIndex < mLiterals.length; literalIndex++)
        {
            if (mLiterals[literalIndex].getLiteral() != E_LITERAL_VALUE.NOT_SET)
            {
                if (mLiterals[literalIndex].getLiteral() == playerAssignment.literals[literalIndex])
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateOutput(Assignment playerAssignment)
    {
        for (int literalIndex = 0; literalIndex < GameLogic.NUM_LITERALS; literalIndex++)
        {
            mLiterals[literalIndex].setUserAssignment(playerAssignment.literals[literalIndex]);
        }

        if (isSolved(playerAssignment) == true)
        {
            mResult.setOutput(E_LITERAL_VALUE.POSITIV);
        }
        else
        {
            mResult.setOutput(E_LITERAL_VALUE.NEGATIV);
        }
    }
}
