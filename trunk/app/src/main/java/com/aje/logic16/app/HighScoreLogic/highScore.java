package com.aje.logic16.app.HighScoreLogic;

/**
 * Created by Engin on 09.04.14.
 */
public class highScore {
    private String name;
    private int score;

    public highScore(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return this.score;
    };
    public String getName() {
        return this.name;
    };
}
