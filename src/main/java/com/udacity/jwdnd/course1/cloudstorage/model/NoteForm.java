package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String noteid, title, description;

    public String getNoteId() {
        return noteid;
    }

    public void setNoteId(String noteid) {
        this.noteid = noteid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
