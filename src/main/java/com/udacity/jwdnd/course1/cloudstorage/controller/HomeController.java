package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
//And FileController
@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, EncryptionService encryptionService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newNote") NoteForm Note, @ModelAttribute("newFile") FileForm File, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("credentials", credentialService.getAllCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping
    public String newFile(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, Model model) {
        if(newFile.getFile().isEmpty()){
            model.addAttribute("result", "error");
            model.addAttribute("message", "There is nothing to upload! Please choose a file to upload");
            return "result";
        }
        String userName = authentication.getName();
        Integer userId = getUserId(authentication);
        String[] files = fileService.getAllFiles(userId);
        MultipartFile multipartFile = newFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean fileExists = false;
        for (int i = 0; i < files.length; i++)
            if(files[i].equals(fileName)){
                fileExists = true;
                break;
            }
        if (!fileExists) {
            try {
                fileService.addFile(multipartFile, userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "This is a duplicate file.");
        }
        model.addAttribute("files", fileService.getAllFiles(userId));
        return "result";
    }

    @GetMapping(value = "/get-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }

    @GetMapping("/delete-file/{fileName}")
    public String deleteFile(Authentication authentication, @PathVariable String fileName, Model model) {
        fileService.deleteFile(fileName);
        Integer userId = getUserId(authentication);
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    private Integer getUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserId();
    }

}
