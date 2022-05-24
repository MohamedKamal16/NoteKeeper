package com.example.notekeeper.model;

import junit.framework.TestCase;

import org.junit.Test;

public class DataManagerTest extends TestCase {
@Test
    public void testCreateNewNote() {
        DataManager dm = DataManager.getInstance();
        //Declare value of new note
        final CourseInfo courseInfo = dm.getCourse("android_test");
        final String noteTitle="Test Note Title";
        final String noteText="Test Note Text";
        //call createNewNote() to create spot for new note
        int noteIndex=dm.createNewNote();
        //associate the note in that index by set the values
        NoteInfo newNote=dm.getNotes().get(noteIndex);
        newNote.setCourse(courseInfo);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        /**
         Test
         */
        NoteInfo compareNote=dm.getNotes().get(noteIndex);

       // assertSame(newNote,compareNote);
        assertEquals(courseInfo,compareNote.getCourse());
        assertEquals(noteText,compareNote.getText());
        assertEquals(noteTitle,compareNote.getTitle());
    }




}