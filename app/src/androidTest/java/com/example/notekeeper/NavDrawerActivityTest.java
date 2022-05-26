package com.example.notekeeper;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.notekeeper.model.DataManager;
import com.example.notekeeper.model.NoteInfo;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class NavDrawerActivityTest {
    @Rule
    public ActivityTestRule<NavDrawerActivity> mActivityTestRule =
            new ActivityTestRule<>(NavDrawerActivity.class);

    @Test
    public void NextThroughNotes() {
        //open drawer layout
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        //navigate to nav_notes in menu
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes));
        //in RecyclerView choose first card in list
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //here we bring list of notes
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        //loop on list
        for (int index = 0; index < notes.size(); index++) {
            NoteInfo note = notes.get(index);
            //check if note title from list == title text from first card in the list
            onView(withId(R.id.edit_text_note_title)).check(matches(withText(note.getTitle())));

            if (index < notes.size() - 1)
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click());
        }
        onView(withId(R.id.action_next)).check(matches(not(isEnabled())));
        pressBack();
    }
}