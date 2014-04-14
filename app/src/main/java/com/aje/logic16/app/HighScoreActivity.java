package com.aje.logic16.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.aje.logic16.app.HighScoreLogic.HighscoreListAdapter;
import com.aje.logic16.app.HighScoreLogic.highScore;
import com.aje.logic16.app.serverApi.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HighScoreActivity extends ActionBarActivity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_high_score);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup highScoreActivity = (ViewGroup) inflater.inflate(R.layout.highscore_list,null);
        highScore[] highScores = new highScore[11];
        api myApi = new api();

        for (int i=1;i<11;i++)
        {
            highScores[i] = myApi.getHighScore(i);
        }

        ListView HighscoreList =  (ListView) highScoreActivity.findViewById(R.id.highscoreListView);
        HighscoreListAdapter ListAdapter = new HighscoreListAdapter(this,highScores);
        HighscoreList.setAdapter(ListAdapter);
        ListAdapter.notifyDataSetChanged();

        addContentView(highScoreActivity,new ActionBar.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.high_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
