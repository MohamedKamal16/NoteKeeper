package com.example.notekeeper.viewModel;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    public String originalNoteCourseId;
    public  String originalNoteTitle;
    public String originalNoteCText;
    public boolean IsNewlyCreated=true;

    public static final String ORIGINAL_NOTE_TITLE="ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_Text="ORIGINAL_NOTE_Text";
    public static final String ORIGINAL_NOTE_COURSE="ORIGINAL_NOTE_COURSE";

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE,originalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TITLE,originalNoteTitle);
        outState.putString(ORIGINAL_NOTE_Text,originalNoteCText);
    }

    public void retoreState(Bundle inState) {
       originalNoteCourseId=inState.getString(ORIGINAL_NOTE_COURSE);
       originalNoteTitle=inState.getString(ORIGINAL_NOTE_TITLE);
       originalNoteCText=inState.getString(ORIGINAL_NOTE_Text);
    }

}
