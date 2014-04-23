package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aje.logic16.app.R;

/**
 * Created by arne on 17.04.14.
 */
public class ButtonRow extends Row
{
    private static final int TOP_MARGIN = 10;

    LiteralButton[] mButtons = new LiteralButton[GameLogic.NUM_LITERALS];

    public ButtonRow(Context widget, DisplayMetrics metrics, GameLogic buttonClickReceiver)
    {
        super(widget, metrics);

        createButtons(metrics, buttonClickReceiver);
    }

    private void createButtons(DisplayMetrics metrics, GameLogic buttonClickReceiver)
    {
        int imageWidth = metrics.widthPixels / (GameLogic.NUM_LITERALS + GameLogic.NUM_RESULT_COLUMN + 2); // 8 images + 1 result image + some place (2)
        int imageHeight = metrics.heightPixels / (GameLogic.NUM_CONJUNCTIONS + 1 + 5); // 16 imageRows, + change Button (1) + some place (5)

        ContextThemeWrapper rectangleImageViewContext = new ContextThemeWrapper(getContext(), R.style.rectangleImageView);

        LinearLayout.LayoutParams imageLayoutParams = getLiteralLayout(metrics);

        for (int column=0;column < GameLogic.NUM_LITERALS; column++)
        {
            mButtons[column] = new LiteralButton(rectangleImageViewContext, imageLayoutParams, E_LITERAL_VALUE.POSITIV, imageWidth, imageHeight, column, buttonClickReceiver);
            this.addView(mButtons[column]);
        }

        TextView placeHolder = new TextView(rectangleImageViewContext);
        placeHolder.setMinimumWidth(imageWidth);
        placeHolder.setMinimumHeight(imageHeight);
        placeHolder.setLayoutParams(imageLayoutParams);
        placeHolder.setGravity(Gravity.CENTER);
        this.addView(placeHolder);
    }

    private LayoutParams getLiteralLayout(DisplayMetrics metrics)
    {
        int literalSpacing = (int)Math.round(metrics.density*2);
        int topSpacing = literalSpacing + TOP_MARGIN;

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.setMargins(literalSpacing, topSpacing, literalSpacing, literalSpacing);

        return imageLayoutParams;
    }
}
