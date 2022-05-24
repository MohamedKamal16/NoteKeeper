package com.example.notekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notekeeper.R;
import com.example.notekeeper.adapter.NoteAdapter;
import com.example.notekeeper.model.DataManager;
import com.example.notekeeper.model.NoteInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class NoteListActivity extends AppCompatActivity {


    private NoteAdapter mMNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        findViews();
        setSupportActionBar(toolbar);

        initializeDisplayContent();
        fab.setOnClickListener(view -> {
            startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMNoteAdapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
        /*ListView listView=findViewById(R.id.list_notes);
        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent =new Intent(NoteListActivity.this,NoteActivity.class);
            *//*NoteInfo note = (NoteInfo) listView.getItemAtPosition(position) ;*//*

            intent .putExtra(NoteActivity.NOTE_POSITION,position);
            startActivity(intent);
        });*/
        mRecyclerView = findViewById(R.id.list_notes);
        LinearLayoutManager noteLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(noteLayoutManager);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        mMNoteAdapter = new NoteAdapter(this, notes);
        mRecyclerView.setAdapter(mMNoteAdapter);

    }

    public void findViews() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
    }

    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    Toolbar toolbar;

}