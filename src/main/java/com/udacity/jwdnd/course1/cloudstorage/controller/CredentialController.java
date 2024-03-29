package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    private Integer getUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserId();
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        model.addAttribute("credentials", this.credentialService.getAllCredentials(getUserId(authentication)));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping("add-credential")
    public String newCredential(Authentication authentication, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        String userName = authentication.getName();
        String newUrl = newCredential.getUrl();
        Integer credentialId = newCredential.getCredentialId();
        String password = newCredential.getPassword();

        //Generating salt
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        Credential [] credentials = credentialService.getAllCredentials(getUserId(authentication));
        boolean credentialExists = false;
        for (int i = 0; i < credentials.length; i++) {
            if (credentials[i].getUserName().equals(userName)) {
                credentialExists = true;
                break;
            }
        }
        if (!credentialExists) {
            if(newUrl.length() > 100){
                model.addAttribute("result", "error");
                model.addAttribute("message", "Credential Url cannot exceed 100 characters.");
            }else if(userName.length() > 30){
                model.addAttribute("result", "error");
                model.addAttribute("message", "Credential Username cannot exceed 30 characters.");
            }else if(password.length() > 30){
                model.addAttribute("result", "error");
                model.addAttribute("message", "Credential password cannot exceed 30 characters.");
            }else {
                if (credentialId == null) {
                    credentialService.addCredential(newUrl, userName, newCredential.getUserName(), encodedKey, encryptedPassword);
                } else {
                    Credential existingCredential = getCredential(credentialId);
                    credentialService.updateCredential(existingCredential.getCredentialid(), newCredential.getUserName(), newUrl, encodedKey, encryptedPassword);
                }
                model.addAttribute("result", "success");
            }
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "There is a Credential with the same username.");
        }
        model.addAttribute("credentials", credentialService.getAllCredentials(getUserId(authentication)));
        model.addAttribute("encryptionService", encryptionService);
        return "result";
    }

    @GetMapping("/get-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        model.addAttribute("credentials", credentialService.getAllCredentials(getUserId(authentication)));
        model.addAttribute("encryptionService", encryptionService);
        credentialService.deleteCredential(credentialId);
        model.addAttribute("result", "success");
        return "result";
    }
}
