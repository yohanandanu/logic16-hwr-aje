package com.aje.logic16.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.aje.logic16.app.HighScoreLogic.HighscoreListAdapter;
import com.aje.logic16.app.HighScoreLogic.highScore;
import com.aje.logic16.app.serverApi.api;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HighScoreActivity extends ActionBarActivity {

    private HighscoreListAdapter mListAdapter;
    private ProgressBar mProgress;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_high_score);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup highScoreActivity = (ViewGroup) inflater.inflate(R.layout.highscore_list,null);
        ArrayList<highScore> highScores = new ArrayList<highScore>();
        api myApi = new api();

        ListView HighscoreList =  (ListView) highScoreActivity.findViewById(R.id.highscoreListView);
        mListAdapter = new HighscoreListAdapter(this,highScores);
        mProgress = (ProgressBar) findViewById(R.id.progressbar_loading);
        HighscoreList.setAdapter(mListAdapter);
        addContentView(highScoreActivity, new ActionBar.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //HighScoreFiller fillerTask = new HighScoreFiller(ListAdapter,progress);
        Thread FillerThread = new Thread(null,new HighScoreFiller(mListAdapter,20,this),"fillerThread");
        FillerThread.start();
    }

    private android.os.Handler mHandler = new android.os.Handler();

    public void loaderFinished() {
        // Enqueue work on mHandler to change the data on
        // the main thread.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}


class HighScoreFiller implements Runnable
{
    HighscoreListAdapter mAdapter = null;
    api myApi = new api();
    HighScoreActivity mAct = null;
    Integer mPos;

    public HighScoreFiller(HighscoreListAdapter adapter,Integer pos,HighScoreActivity act){
        this.mAdapter = adapter;
        this.mPos = pos;
        this.mAct = act;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        for (int i=0;i<mPos;i++)
        {
            highScore high = myApi.getHighScore(i+1);
            if (high == null) break;
            this.mAdapter.setDataFromThread(high);
        }
        mAct.loaderFinished();
    }
}

