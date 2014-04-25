package com.aje.logic16.app.GameLogic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aje.logic16.app.GameOverActivity;
import com.aje.logic16.app.GameScreen;
import com.aje.logic16.app.StartPage;
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
    public static final int NUM_SOME_PLACE = 7;
    public static final int NUM_SECONDS = 60;

    private Conjunction[] mConjunctions = null;
    private ButtonRow mButtonRow = null;
    private Assignment mPlayerAssignment = new Assignment();
    private Context mWidget;
    private HeaderRow mHeaderRow;
    /**
     * Das Starten von der Verloren-Activity kann ein winzigen Augenblick, in dem der Spieler noch
     * Klicken kann. Daher ist es möglich in der Zeit in der die Activity geladen wird, das Spiel
     * noch zu gewinnen. Daher muss das Verhindert werden.
     */
    private boolean mbLost = false;

    /**
     * Constructor
     */
    public GameLogic(Context widget, DisplayMetrics metrics)
    {
        mWidget = widget;

        // schonmal alle Komponenten konstruieren
        createConjunctions(widget, metrics);
        mButtonRow = new ButtonRow(widget, metrics, this);
        mHeaderRow = new HeaderRow(widget, metrics, this);
        mHeaderRow.setGravity(Gravity.RIGHT);
        mHeaderRow.setMinimumWidth((int) ((int)metrics.widthPixels*0.8));
    }

    public Conjunction[] getConjunctions()
    {
        return mConjunctions;
    }

    public HeaderRow getHeaderRow()
    {
        return mHeaderRow;
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
        if (formulaString.length() <= 0)
        {
            new AlertDialog.Builder(mWidget)
                    .setMessage("No Internet Connection, or Server not Available")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            onTimeOver();
                            Intent intent = new Intent(mWidget,StartPage.class);
                            mWidget.startActivity(intent);
                        }
                    })
                    .show();
            return;
        }
        FormulaParser parser = new FormulaParser();
        parser.parseFormula(formulaString, mConjunctions);

        updateScreen();
    }

    public void buttonClicked(int column)
    {
        updatePlayerAssignment(column);
        updateScreen();
        if ((isWon() == true) && (mbLost == false))
        {
            // Der Timer muss gestoppt werden, damit das Spiel nicht verloren wird
            mHeaderRow.stopTimer();

            // Das Spiel wurde gewonnen
            // Score hochzählen
            GameScore.getInstance().incrementScore();

            // und den Win Screen einblenden
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

    public void onTimeOver()
    {
        mbLost = true;
        GameScore.getInstance().resetScore();
        mHeaderRow.stopTimer();
        Intent intent = new Intent(mWidget, GameOverActivity.class);
        mWidget.startActivity(intent);
    }
}
