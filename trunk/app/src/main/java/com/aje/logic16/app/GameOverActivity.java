package com.aje.logic16.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aje.logic16.app.HighScoreLogic.highScore;
import com.aje.logic16.app.serverApi.api;

/**
 * Created by Engin on 16.04.14.
 */
public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_over_screen);
    }

    public void try_again(View view) {
        Intent intent = new Intent(this,GameScreen.class);
        startActivity(intent);
    }

    public void to_start_page(View view) {
        Intent intent = new Intent(this,StartPage.class);
        startActivity(intent);
    }
}
