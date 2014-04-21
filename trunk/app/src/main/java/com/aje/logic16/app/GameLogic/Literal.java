package com.aje.logic16.app.GameLogic;

import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by arne on 14.04.14.
 */
public class Literal extends TextView {

    public enum E_LITERAL_VALUE{
        POSITIV,
        NEGATIV,
        NOT_SET,
    };

    private E_LITERAL_VALUE mValue;

    /**
     * Konstruktor
     * @param theme
     * @param value
     */
    public Literal(ContextThemeWrapper context, LinearLayout.LayoutParams imageLayoutParams, E_LITERAL_VALUE value, int width, int height)
    {
        super(context);
        this.setMinimumWidth(width);
        this.setMinimumHeight(height);
        this.setLayoutParams(imageLayoutParams);
        this.setGravity(Gravity.CENTER);
        setOutput(value);

        mValue = value;
        setOutput(mValue);
    }

    public E_LITERAL_VALUE getLiteral()
    {
        return mValue;
    }

    public void setUserAssignment(E_LITERAL_VALUE value)
    {
        if (mValue == E_LITERAL_VALUE.NOT_SET)
        {
            // Immer ok wenn der Literal nicht gesetzt ist
            setOutput(E_LITERAL_VALUE.POSITIV);
        }
        else if (value == mValue)
        {
            // wenn das Literal überinstimmt, dann geben wir "+" aus
            setOutput(E_LITERAL_VALUE.POSITIV);
        }
        else
        {
            // wenn das Literal nicht überinstimmt, dann geben wir "-" aus
            setOutput(E_LITERAL_VALUE.NEGATIV);
        }
    }

    protected void swapLiteral()
    {
        if (mValue == E_LITERAL_VALUE.POSITIV)
        {
            mValue = E_LITERAL_VALUE.NEGATIV;
        }
        else if (mValue == E_LITERAL_VALUE.NEGATIV)
        {
            mValue = E_LITERAL_VALUE.POSITIV;
        }
        setOutput(mValue);
    }

    protected void setOutput(E_LITERAL_VALUE value)
    {
        if (value == E_LITERAL_VALUE.POSITIV)
        {
            this.setBackgroundColor(Color.GREEN);
            this.setText("+");
        }
        else
        {
            this.setBackgroundColor(Color.RED);
            this.setText("-");
        }
    }
}
