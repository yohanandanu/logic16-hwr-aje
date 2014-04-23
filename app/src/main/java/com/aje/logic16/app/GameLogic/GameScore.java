package com.aje.logic16.app.GameLogic;

/**
 * Created by Arne on 23.04.14.
 */
public class GameScore
{
    private static GameScore mInstance = new GameScore();
    public static GameScore getInstance()
    {
        return mInstance;
    }

    int mPlayerScore = 0;

    private GameScore()
    {}

    public int getScore()
    {
        return mPlayerScore;
    }

    public void incrementScore()
    {
        mPlayerScore++;
    }

    public void resetScore()
    {
        mPlayerScore = 0;
    }
}
