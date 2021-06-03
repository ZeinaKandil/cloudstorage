package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "uploadButton")
    private WebElement uploadFileButton;

    @FindBy(id = "exFileName")
    private WebElement exFileName;

    @FindBy(id = "deleteFile")
    private WebElement deleteFile;

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "newNote")
    private WebElement newNoteButton;

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(id = "saveNote")
    private WebElement saveNoteButton;

    @FindBy(id = "exNoteTitle")
    private WebElement exNoteTitle;

    @FindBy(id = "exNoteDescription")
    private WebElement exNoteDescription;

    @FindBy(id = "editNote")
    private WebElement editNoteButton;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "deleteNote")
    private WebElement deleteNote;

    @FindBy(id = "newCredential")
    private WebElement newCredentialButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "editCredential")
    private WebElement editCredentialButton;

    @FindBy(id = "deleteCredential")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "saveChangesCredential")
    private WebElement saveCredentialButton;

    @FindBy(id = "exCredentialUrl")
    private WebElement exCredentialUrl;

    @FindBy(id = "exCredentialUsername")
    private WebElement exCredentialUsername;

    @FindBy(id = "exCredentialPassword")
    private WebElement exCredentialPassword;

    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    // File Stuff
    public void navigateFilesTab() {
        js.executeScript("arguments[0].click();", navFilesTab);
    }

    public void uploadNewFile() {
        fileUpload.sendKeys("C:\\Users\\Zeina Kandil\\Downloads\\CV_Zeina_Kandil  .pdf");
        js.executeScript("arguments[0].click();", fileUpload);
    }
    public void deleteFile() {
        js.executeScript("arguments[0].click();", deleteFile);
    }

    public boolean noFiles(WebDriver driver) {
        return !isElementPresent(By.id("exFileName"), driver);
    }

    public File getTopFile() {
        String fileName = wait.until(ExpectedConditions.elementToBeClickable(exFileName)).getText();
        return new File(fileName);
    }

    public void submitUploadFile() {
        js.executeScript("arguments[0].click();", uploadFileButton);
    }

    // Note Stuff
    public void editNote() {
        js.executeScript("arguments[0].click();", editNoteButton);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", deleteNote);
    }

    public void addNewNote() {
        js.executeScript("arguments[0].click();", newNoteButton);
    }

    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
    }

    public void changeNote(String newNoteTitle, String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).sendKeys(newNoteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(newNoteDescription);
    }

    public void navigateNotesTab() {
        js.executeScript("arguments[0].click();", navNotesTab);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", txtNoteDescription);
    }

    public void saveNote() {
        js.executeScript("arguments[0].click();", saveNoteButton);
    }

    public boolean noNotes(WebDriver driver) {
        return !isElementPresent(By.id("exNoteTitle"), driver) && !isElementPresent(By.id("exNoteDescription"), driver);
    }

    public Note getTopNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(exNoteTitle)).getText();
        String description = exNoteDescription.getText();
        return new Note(title, description);
    }

    //Credential Stuff
    public void editCredential() {
        js.executeScript("arguments[0].click();", editCredentialButton);
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click();", deleteCredentialButton);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", newCredentialButton);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    public void navigateCredentialsTab() {
        js.executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void saveCredential() {
        js.executeScript("arguments[0].click();", saveCredentialButton);
    }

    public boolean noCredentials(WebDriver driver) {
        return !isElementPresent(By.id("exCredentialUrl"), driver) &&
                !isElementPresent(By.id("exCredentialUsername"), driver) &&
                !isElementPresent(By.id("exCredentialPassword"), driver);
    }

    public Credential getTopCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(exCredentialUrl)).getText();
        String username = exCredentialUsername.getText();
        String password = exCredentialPassword.getText();
        return new Credential(url, username, password);
    }
}
