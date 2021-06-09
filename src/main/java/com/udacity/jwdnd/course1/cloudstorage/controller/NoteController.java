package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        if (noteId == null)
            noteService.addNote(newTitle, newDescription, userName);
        else {
            Note existingNote = getNote(noteId);
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
        }
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping(value = "/get-note/{noteId}")
    public Note getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(
            Authentication authentication, @PathVariable Integer noteId, @ModelAttribute("newNote") NoteForm addNote, Model model) {
        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("result", "success");
        return "result";
    }
}
