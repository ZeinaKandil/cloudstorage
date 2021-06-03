package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid, userid;
    private String notetitle, notedescription;

    public Note(Integer noteid, String notetitle, String notedescription, Integer userid) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Note(String notetitle, String notedescription) {
        this.notetitle = notetitle;
        this.notedescription = notedescription;
    }

    public Integer getNoteId() {
        return noteid;
    }

    public void setNoteId(Integer noteid) {
        this.noteid = noteid;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }

    public String getNoteTitle() {
        return notetitle;
    }

    public void setNoteTitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNoteDescription() {
        return notedescription;
    }

    public void setNoteDescription(String notedescription) {
        this.notedescription = notedescription;
    }
}
