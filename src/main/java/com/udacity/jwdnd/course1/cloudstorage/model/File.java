package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileid, userid;
    private String filename, contenttype, filesize;
    private byte[] filedata;

    public File(Integer fileid, String filename, String contenttype, String filesize, Integer userid, byte[] filedata){
        this.fileid = fileid;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public File(String filename){
        this.filename = filename;
    }

    public Integer getFileId() {
        return fileid;
    }

    public void setFileId(Integer fileid) {
        this.fileid = fileid;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contenttype;
    }

    public void setContentType(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFileSize() {
        return filesize;
    }

    public void setFileSize(String filesize) {
        this.filesize = filesize;
    }

    public byte[] getFileData() {
        return filedata;
    }

    public void setFileData(byte[] filedata) {
        this.filedata = filedata;
    }
}
