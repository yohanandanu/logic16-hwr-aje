package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by arne on 14.04.14.
 */
public class GameLogic
{
    private static GameLogic msInstance = new GameLogic();
    public static GameLogic getInstance()
    {
        return msInstance;
    }

    public static final int NUM_CONJUNCTIONS = 16;
    public static final int NUM_LITERALS = 8;
    public static final int NUM_RESULT_COLUMN = 1;

    private Conjunction[] mConjunctions = null;
    private ButtonRow mButtonRow = null;

    /**
     * Singleton Constructor
     */
    private GameLogic()
    {}

    public Conjunction[] getConjunction(Context widget, DisplayMetrics metrics)
    {
        if (mConjunctions == null)
        {
            mConjunctions =  new Conjunction[GameLogic.NUM_CONJUNCTIONS];

            for (int i = 0; i < GameLogic.NUM_CONJUNCTIONS; i++)
            {
                mConjunctions[i] = new Conjunction(widget, metrics);
            }
        }
        return mConjunctions;
    }

    public ButtonRow getButtonRow(Context widget, DisplayMetrics metrics)
    {
        if (mButtonRow == null)
        {
            mButtonRow = new ButtonRow(widget, metrics);
        }
        return mButtonRow;
    }

    public void buttonClicked(int column)
    {
    }
}
