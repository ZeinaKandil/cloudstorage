package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {

    public static final String noteTitle = "Test Note", noteDescription = "Tons of blubber going on.";

    private void createNote(HomePage homePage) {
        homePage.navigateNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateNotesTab();
    }
    //    Test Creation of notes
    @Test
    public void testNoteCreation() {
        HomePage homePage = signUpAndLogin();
        createNote(homePage);
        homePage = new HomePage(driver);
        homePage.navigateNotesTab();
        Note note = homePage.getTopNote();
        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());

        deleteNote(homePage);
        homePage.logout();
    }

    @Test
    public void testDelete() {
        // Create a note and delete it and confirm that it's no longer there
        HomePage homePage = signUpAndLogin();
        createNote(homePage);
        homePage.navigateNotesTab();
        homePage = new HomePage(driver);
        Assertions.assertFalse(homePage.noNotes(driver));
        deleteNote(homePage);
        Assertions.assertTrue(homePage.noNotes(driver));
    }

    private void deleteNote(HomePage homePage) {
        homePage.deleteNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }


    //Test that edits note works and that changes are shown.
    @Test
    public void testEdit() {
        HomePage homePage = signUpAndLogin();
        createNote(homePage);
        homePage.navigateNotesTab();
        homePage = new HomePage(driver);
        homePage.editNote();
        String newTitle = "Edited Note", newDescription = "Again Blubber";
        homePage.changeNote(newTitle, newDescription);
        homePage.saveNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateNotesTab();
        Note note = homePage.getTopNote();
        Assertions.assertEquals(newTitle, note.getNoteTitle());
        Assertions.assertEquals(newDescription, note.getNoteDescription());
        deleteNote(homePage);
    }


}