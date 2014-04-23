package com.aje.logic16.app.GameLogic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by arne on 16.04.14.
 */
public class LiteralButton extends Literal implements View.OnClickListener
{
    private Paint paint = new Paint();


    private Border[] mBorders;

    private int mColumn;

    private GameLogic mClickReceiver;

    /**
     * Constructor
     * @param context
     * @param imageLayoutParams
     * @param value
     * @param width
     * @param height
     * @param column
     */
    public LiteralButton(ContextThemeWrapper context,
                         LinearLayout.LayoutParams imageLayoutParams,
                         E_LITERAL_VALUE value,
                         int width, int height, int column, GameLogic clickReceiver)
    {
        super(context, imageLayoutParams, value, width, height);

        mColumn = column;

        mClickReceiver = clickReceiver;

        // Komponente soll klickbar sein
        this.setClickable(true);
        this.setOnClickListener(this);

        mBorders = new Border[4];
        mBorders[0] = new Border(Border.BORDER_TOP);
        mBorders[1] = new Border(Border.BORDER_BOTTOM);
        mBorders[2] = new Border(Border.BORDER_LEFT);
        mBorders[3] = new Border(Border.BORDER_RIGHT);
    }

    public void onClick(View arg0)
    {
        swapLiteral();
        mClickReceiver.buttonClicked(mColumn);
    }

    private void init(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBorders == null) return;

        for(Border border : mBorders){
            paint.setColor(border.getColor());
            paint.setStrokeWidth(border.getWidth());

            if(border.getStyle() == Border.BORDER_TOP){
            canvas.drawLine(1, 1, getWidth()-1, 1, paint);
            } else
            if(border.getStyle() == Border.BORDER_RIGHT){
                canvas.drawLine(getWidth()-1, 1, getWidth()-1, getHeight()-1, paint);
            } else
            if(border.getStyle() == Border.BORDER_BOTTOM){
                canvas.drawLine(1, getHeight()-1, getWidth()-1, getHeight()-1, paint);
            } else
            if(border.getStyle() == Border.BORDER_LEFT){
                canvas.drawLine(1, 1, 1, getHeight()-1, paint);
            }
        }
    }

    public Border[] getBorders() {
        return mBorders;
    }

    public void setBorders(Border[] borders) {
        this.mBorders = borders;
    }
}
