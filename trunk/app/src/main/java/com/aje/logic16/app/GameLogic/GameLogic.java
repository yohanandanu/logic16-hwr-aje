package com.aje.logic16.app.GameLogic;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.util.DisplayMetrics;

import com.aje.logic16.app.GameScreen;
import com.aje.logic16.app.WonGameActivity;
import com.aje.logic16.app.serverApi.api;

/**
 * Created by arne on 14.04.14.
 */
public class GameLogic
{
    /*private static GameLogic msInstance = new GameLogic();
    public static GameLogic getInstance()
    {
        return msInstance;
    }*/

    public static final int NUM_CONJUNCTIONS = 16;
    public static final int NUM_LITERALS = 8;
    public static final int NUM_RESULT_COLUMN = 1;

    private Conjunction[] mConjunctions = null;
    private ButtonRow mButtonRow = null;
    private Assignment mPlayerAssignment = new Assignment();
    private Context mWidget;

    /**
     * Constructor
     */
    public GameLogic(Context widget, DisplayMetrics metrics)
    {
        mWidget = widget;

        // schonmal alle Komponenten konstruieren
        createConjunctions(widget, metrics);
        mButtonRow = new ButtonRow(widget, metrics, this);
    }

    public Conjunction[] getConjunction()
    {
        return mConjunctions;
    }

    private void createConjunctions(Context widget, DisplayMetrics metrics)
    {
        mConjunctions =  new Conjunction[GameLogic.NUM_CONJUNCTIONS];

        for (int i = 0; i < GameLogic.NUM_CONJUNCTIONS; i++)
        {
            mConjunctions[i] = new Conjunction(widget, metrics);
        }
    }

    public ButtonRow getButtonRow(Context widget, DisplayMetrics metrics)
    {
        return mButtonRow;
    }

    public void loadGame()
    {
        api gameLoader = new api();
        String formulaString = gameLoader.getFormula();
        FormulaParser parser = new FormulaParser();
        parser.parseFormula(formulaString, mConjunctions);

        updateScreen();
    }

    public void buttonClicked(int column)
    {
        updatePlayerAssignment(column);
        updateScreen();
        if (isWon() == true)
        {
            // Das Spiel wurde gewonnen also den Win Screen einblenden
            Intent intent = new Intent(mWidget, WonGameActivity.class);
            mWidget.startActivity(intent);
        }
    }

    private void updatePlayerAssignment(int column)
    {
        if (mPlayerAssignment.literals[column] == E_LITERAL_VALUE.POSITIV)
        {
            mPlayerAssignment.literals[column] = E_LITERAL_VALUE.NEGATIV;
        }
        else if (mPlayerAssignment.literals[column] == E_LITERAL_VALUE.NEGATIV)
        {
            mPlayerAssignment.literals[column] = E_LITERAL_VALUE.POSITIV;
        }
    }

    private void updateScreen()
    {
        for (Conjunction conjunction : mConjunctions)
        {
            conjunction.updateOutput(mPlayerAssignment);
        }
    }

    private boolean isWon()
    {
        for (Conjunction conjunction : mConjunctions)
        {
            if (conjunction.isSolved(mPlayerAssignment) == false)
            {
                return false;
            }
        }
        return true;
    }
}
