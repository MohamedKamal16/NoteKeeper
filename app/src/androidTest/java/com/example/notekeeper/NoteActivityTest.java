package com.example.notekeeper;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
//onView and on Data
import static androidx.test.espresso.Espresso.*;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.pressBack;

import com.example.notekeeper.R;
//import com.example.notekeeper.activity.NoteListActivity;
import com.example.notekeeper.model.CourseInfo;
import com.example.notekeeper.model.DataManager;
import com.example.notekeeper.model.NoteInfo;

//handle action on view click or type
import static androidx.test.espresso.action.ViewActions.*;
//match method
import static androidx.test.espresso.assertion.ViewAssertions.*;
//get id layout
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class NoteActivityTest extends TestCase {
    static DataManager sDataManager;
    @BeforeClass
    public static void classSetUp(){
       sDataManager=DataManager.getInstance();
    }

    /*@Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityActivityTestRule =
            new ActivityTestRule<>(NoteListActivity.class);
*/
    @Test
    public void createNewNote() {
        final CourseInfo courseInfo=sDataManager.getCourse("java_lang");
        final String noteTitle="TEST TITLE";
        final String noteText="TEST TEXT";
   /*     ViewInteraction fabNewNote =onView(withId(R.id.fab));
        fabNewNote.perform(click());*/

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class),equalTo(courseInfo))).perform(click());
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString(courseInfo.getTitle()))));

        onView(withId(R.id.edit_text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));

        onView(withId(R.id.edit_text_note_text)).perform(typeText(noteText),
                closeSoftKeyboard());
        onView(withId(R.id.edit_text_note_text)).check(matches(withText(containsString(noteText))));

        pressBack();

        int noteIndex=sDataManager.getNotes().size()-1;
        NoteInfo noteInfo=sDataManager.getNotes().get(noteIndex);
        assertEquals(courseInfo,noteInfo.getCourse());
        assertEquals(noteTitle,noteInfo.getTitle());
        assertEquals(noteText,noteInfo.getText());
    }
}