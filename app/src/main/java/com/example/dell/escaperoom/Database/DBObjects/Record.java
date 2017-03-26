package com.example.dell.escaperoom.Database.DBObjects;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.escaperoom.R;

import java.util.List;

/**
 * Created by yaelgersh on 26/03/2017.
 */

public class Record {
    private String name;
    private String score;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }




    public static class RecordsList extends ArrayAdapter<Record> {
        private Activity context;
        private List<Record> recordsList;

        public RecordsList(Activity context, List<Record> recordsList) {
            super(context, R.layout.list_layout, recordsList);
            this.context = context;
            this.recordsList = recordsList;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

            TextView nameText = (TextView) listViewItem.findViewById(R.id.textViewName);
            TextView scoreText = (TextView) listViewItem.findViewById(R.id.textViewScore);

            Record record = recordsList.get(position);

            //textView . setText = record.name;...

            nameText.setText(record.getName());
            scoreText.setText(record.getScore());

            return listViewItem;
        }
    }
}



