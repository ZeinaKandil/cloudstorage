package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests extends CloudStorageApplicationTests {

    public static final String User1URL = "https://github.com/ZeinaKandil", User2URL = "https://www.youtube.com/watch?v=joiq1Seh61w", User3URL = "https://www.wizardingworld.com/";
    public static final String Username1 = "Zeina", Username2 = "Gru", Username3 = "TheChosenOne";
    public static final String Password1 = "Meh", Password2 = "Lisa", Password3 = "Expelliarmus";

    // Test the display of credentials and that passwords are properly encrypted.
    @Test
    public void testCredentialCreation() {
        HomePage homePage = signUpAndLogin();
        createAndTestCredential(User1URL, Username1, Password1, homePage);
        homePage.deleteCredential();
        //Result page displays success
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.logout();
    }

    private void createAndTestCredential(String url, String username, String password, HomePage homePage) {
        createCredential(url, username, password, homePage);
        homePage.navigateCredentialsTab();
        Credential credential = homePage.getTopCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUserName());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) {
        //Go to Credentials tab in Home page
        homePage.navigateCredentialsTab();
        //Create Credential using url, username and password
        homePage.addNewCredential();
        enterCredentialData(url, username, password, homePage);
        // Result page displays Success
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }

    private void enterCredentialData(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
        homePage.saveCredential();
    }


    // Check that edit works correctly and that changes are displayed
    @Test
    public void testEditCredential() {
        HomePage homePage = signUpAndLogin();
        //First Credential
        createAndTestCredential(User1URL, Username1, Password1, homePage);
        Credential originalCredential = homePage.getTopCredential();
        String firstEncryptedPassword = originalCredential.getPassword();
        //Change Credential
        homePage.editCredential();
        String Url2 = User2URL, credentialUsername2 = Username2, password2 = Password2;
        enterCredentialData(Url2, Username2, Password2, homePage);
        //Result page displays success and go back to Credentials Tab
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateCredentialsTab();
        Credential changedCredential = homePage.getTopCredential();
        Assertions.assertEquals(Url2, changedCredential.getUrl());
        Assertions.assertEquals(credentialUsername2, changedCredential.getUserName());
        Assertions.assertNotEquals(password2, changedCredential.getPassword());
        Assertions.assertNotEquals(firstEncryptedPassword, changedCredential.getPassword());
        homePage.deleteCredential();
        resultPage.clickOk();
        homePage.logout();
    }

    // Delete All Credentials
    @Test
    public void testDeletion() {
        HomePage homePage = signUpAndLogin();
        createCredential(User1URL, Username1, Password1, homePage);
        createCredential(User2URL, Username2, Password2, homePage);
        createCredential(User3URL, Username3, Password3, homePage);
        WebDriverWait wait = new WebDriverWait(driver, 30000);
        //Check that all 3 credentials are there before deletion
        Assertions.assertFalse(homePage.noCredentials(driver));
        ResultPage resultPage = new ResultPage(driver);
        //Delete all 3 credentials one by one
        for (int i = 0; i < 3; i++) {
            homePage.navigateCredentialsTab();
            homePage.deleteCredential();
            resultPage.clickOk();
        }
        //Confirm that all credentials were deleted
        homePage.navigateCredentialsTab();
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }
}