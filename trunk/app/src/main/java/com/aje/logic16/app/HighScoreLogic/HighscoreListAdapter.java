package com.aje.logic16.app.HighScoreLogic;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.aje.logic16.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Engin on 12.04.14.
 */
public class HighscoreListAdapter extends ArrayAdapter<highScore>{

    private final Context context;
    private ArrayList<highScore> values;

    public HighscoreListAdapter(Context context, ArrayList<highScore> values) {
        super(context, R.layout.highscore_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.highscore_list_item, parent, false);
        TextView rank = (TextView) convertView.findViewById(R.id.highscoreListItemRank);
        TextView name = (TextView) convertView.findViewById(R.id.highscoreListItemName);
        TextView score = (TextView) convertView.findViewById(R.id.highscoreListItemScore);
        if (position >= values.size())
            return convertView;
        String rang = Integer.toString(position+1);
        if (rang.length() == 1) rang = "0"+rang;
        rank.setText(rang);
        name.setText(values.get(position).getName());
        score.setText(Integer.toString(values.get(position).getScore()));

        return convertView;
    }

    private android.os.Handler mHandler = new android.os.Handler();

    public void setDataFromThread(ArrayList<highScore> data) {
        // Enqueue work on mHandler to change the data on
        // the main thread.
        final ArrayList<highScore> newData = data;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                values = newData;
                notifyDataSetChanged();
            }
        });
    }

    public void setDataFromThread(highScore data) {
        // Enqueue work on mHandler to change the data on
        // the main thread.
        final highScore newData = data;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                values.add(newData);
                notifyDataSetChanged();
            }
        });
    }
}
