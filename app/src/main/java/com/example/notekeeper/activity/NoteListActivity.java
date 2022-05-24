package com.example.notekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notekeeper.R;
import com.example.notekeeper.model.DataManager;
import com.example.notekeeper.model.NoteInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;


public class NoteListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        findViews();
        setSupportActionBar(toolbar);

        initializeDisplayContent();
     fab.setOnClickListener(view -> {
         startActivity(new Intent(NoteListActivity.this,NoteActivity.class));
     });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent(){
        ListView listView=findViewById(R.id.list_notes);
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
       adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent =new Intent(NoteListActivity.this,NoteActivity.class);
            /*NoteInfo note = (NoteInfo) listView.getItemAtPosition(position) ;*/

            intent .putExtra(NoteActivity.NOTE_POSITION,position);
            startActivity(intent);
        });

    }
    public void findViews(){
        toolbar=findViewById(R.id.toolbar);
        fab=findViewById(R.id.fab);
    }

    ArrayAdapter<NoteInfo> adapter;
    FloatingActionButton fab;
    Toolbar toolbar;
}