package com.example.dell.escaperoom;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.dell.escaperoom.Database.DBObjects.Record;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private DatabaseReference databaseRecords;
    private ListView recordListView;
    private List<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        databaseRecords = FirebaseDatabase.getInstance().getReference("Records");
        recordListView = (ListView) findViewById(R.id.listViewRecords);
        recordList = new ArrayList<Record>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseRecords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                recordList.clear();
                for(DataSnapshot recordsSnapshot : dataSnapshot.getChildren()){
                    Record record = recordsSnapshot.getValue(Record.class);
                    recordList.add(record);
                }

                Record.RecordsList adapter = new Record.RecordsList(RecordsActivity.this, recordList);
                recordListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
