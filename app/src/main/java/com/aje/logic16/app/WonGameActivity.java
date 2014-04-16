package com.aje.logic16.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aje.logic16.app.HighScoreLogic.highScore;
import com.aje.logic16.app.serverApi.api;

import org.w3c.dom.Text;

/**
 * Created by Engin on 16.04.14.
 */
public class WonGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.won_screen);
        TextView score = (TextView) findViewById(R.id.actualScore);
        score.setText("3000");
        EditText personName = (EditText)findViewById(R.id.namePerson);
        final Button addHighscore = (Button) findViewById(R.id.addHighscoreButton);

        personName.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    addHighscore.setText(R.string.add_highscore);
                } else
                {
                    addHighscore.setText(R.string.hi_back_to_start);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


    }

    public void add_highscore(View view) {

        EditText name = (EditText) findViewById(R.id.namePerson);
        String insertedName = name.getText().toString();

        if (insertedName.length() > 0)
        {
            TextView score = (TextView) findViewById(R.id.actualScore);
            Integer intScore = Integer.parseInt(score.getText().toString());

            api myApi = new api();
            myApi.addScore(new highScore(insertedName,intScore));
        }

        Intent intent = new Intent(this,StartPage.class);
        startActivity(intent);
    }

    public void continue_game(View view) {
        Intent intent = new Intent(this,GameScreen.class);
        startActivity(intent);
    }
}
