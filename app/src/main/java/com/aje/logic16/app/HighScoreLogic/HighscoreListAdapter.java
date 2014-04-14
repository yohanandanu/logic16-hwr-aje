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

import java.util.List;

/**
 * Created by Engin on 12.04.14.
 */
public class HighscoreListAdapter extends ArrayAdapter<highScore>{

    private final Context context;
    private final highScore[] values;

    public HighscoreListAdapter(Context context, highScore[] values) {
        super(context, R.layout.highscore_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.highscore_list_item, parent, false);
        TextView rank = (TextView) rowView.findViewById(R.id.highscoreListItemRank);
        TextView name = (TextView) rowView.findViewById(R.id.highscoreListItemName);
        TextView score = (TextView) rowView.findViewById(R.id.highscoreListItemScore);

        rank.setText(position);
        name.setText(values[position].getName());
        score.setText(Integer.toString(values[position].getScore()));

        return rowView;
    }
}
