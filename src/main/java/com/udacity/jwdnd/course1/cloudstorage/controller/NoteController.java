package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newNote") NoteForm addNote, Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserId();
    }

    @PostMapping("add-note")
    public String addNote(Authentication authentication, @ModelAttribute("newNote") NoteForm addNote, Model model) {
        String userName = authentication.getName();
        String newTitle = addNote.getTitle();
        Integer noteId = addNote.getNoteId();
        String newDescription = addNote.getDescription();
        Note [] notes = noteService.getAllNotes(getUserId(authentication));
        boolean noteExists = false;
        for (int i = 0; i < notes.length; i++) {
            if (notes[i].getNoteTitle().equals(newTitle)) {
                noteExists = true;
                break;
            }
        }
        if (!noteExists) {
            if(newTitle.length() > 20){
                model.addAttribute("result", "error");
                model.addAttribute("message", "Note Title cannot exceed 20 characters.");
            }else if(newDescription.length() > 1000){
                model.addAttribute("result", "error");
                model.addAttribute("message", "Note Description cannot exceed 1000 characters.");
            }else {
                if (noteId == null)
                    noteService.addNote(newTitle, newDescription, userName);
                else {
                    Note existingNote = getNote(noteId);
                    noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
                }
                model.addAttribute("result", "success");
            }
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "There is a note with the same title.");
        }
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getAllNotes(userId));
        return "result";
    }

    @GetMapping("/get-note/{noteId}")
    public Note getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(
            Authentication authentication, @PathVariable Integer noteId, @ModelAttribute("newNote") NoteForm addNote, Model model) {
        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("result", "success");
        return "result";
    }
}
