package com.example.notekeeper.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.notekeeper.NoteActivityViewModel;
import com.example.notekeeper.model.CourseInfo;
import com.example.notekeeper.model.DataManager;
import com.example.notekeeper.R;
import com.example.notekeeper.model.NoteInfo;

import java.util.List;

public class NoteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        findViews();
        setSupportActionBar(toolbar);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mViewModel = viewModelProvider.get(NoteActivityViewModel.class);
        if (savedInstanceState!=null && mViewModel.IsNewlyCreated)
            mViewModel.retoreState(savedInstanceState);

        mViewModel.IsNewlyCreated=false;
        //understand this part todo
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        //for spinner
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCourses);

        readDisplayStateValues();
        saveOriginalNoteValue();

        if (!mISNewNote) displayNote(spinner, textNoteTitle, textNoteText);


    }

    private void saveOriginalNoteValue() {
        if (mISNewNote)
            return;
        mViewModel.originalNoteCourseId = mNoteInfo.getCourse().getCourseId();
        mViewModel.originalNoteTitle = mNoteInfo.getTitle();
        mViewModel.originalNoteCText = mNoteInfo.getText();
    }

    private void displayNote(Spinner spinner, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int coursesIndex = courses.indexOf(mNoteInfo.getCourse());
        spinner.setSelection(coursesIndex);
        textNoteTitle.setText(mNoteInfo.getTitle());
        textNoteText.setText(mNoteInfo.getText());
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        mISNewNote = position == POSITION_NOT_SET;

        if (mISNewNote) {
            createNewNote();
        } else {
            mNoteInfo = DataManager.getInstance().getNotes().get(position);
        }

    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        notePosition = dm.createNewNote();
        mNoteInfo = dm.getNotes().get(notePosition);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (IsCancel) {
            if (mISNewNote) {
                DataManager.getInstance().removeNote(notePosition);
            } else {
                storePreviousNoteValues();
            }
        } else {
            saveNote();
        }

    }

    private void storePreviousNoteValues() {
        CourseInfo courseInfo = DataManager.getInstance().getCourse(mViewModel.originalNoteCourseId);
        mNoteInfo.setCourse(courseInfo);
        mNoteInfo.setText(mViewModel.originalNoteCText);
        mNoteInfo.setTitle(mViewModel.originalNoteTitle);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState!=null)
            mViewModel.saveState(outState);
    }

    private void saveNote() {
        mNoteInfo.setCourse((CourseInfo) spinner.getSelectedItem());
        mNoteInfo.setText(textNoteText.getText().toString());
        mNoteInfo.setTitle(textNoteTitle.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sendEmail();
            return true;
        } else if (id == R.id.action_Cancel) {
            IsCancel = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) spinner.getSelectedItem();
        String subject = textNoteTitle.getText().toString();
        String text = "Checkout what i learnt in plualsight\"" + course.getTitle() + "\"\n"
                + textNoteTitle.getText().toString();
        //send something with gmail(implicit Intent)
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message//rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);

    }

    public void findViews() {
        toolbar = findViewById(R.id.toolbar_Note);
        spinner = findViewById(R.id.spinner_courses);
        textNoteTitle = findViewById(R.id.edit_text_note_title);
        textNoteText = findViewById(R.id.edit_text_note_text);
    }

    public static final String NOTE_POSITION = "NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNoteInfo;
    boolean mISNewNote;
    Boolean IsCancel = false;
    Toolbar toolbar;
    Spinner spinner;
    EditText textNoteTitle;
    EditText textNoteText;
    private NoteActivityViewModel mViewModel;
    int notePosition;
}