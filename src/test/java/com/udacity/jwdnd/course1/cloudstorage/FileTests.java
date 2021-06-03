package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileTests extends CloudStorageApplicationTests{

    private void uploadFile(HomePage homePage){
        homePage.navigateFilesTab();
        homePage.uploadNewFile();
        homePage.submitUploadFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateFilesTab();
    }

    @Test
    public void testFileUpload(){
        HomePage homePage = signUpAndLogin();
        uploadFile(homePage);
        homePage = new HomePage(driver);
        homePage.navigateFilesTab();

        deleteFile(homePage);
        homePage.logout();
    }

    @Test
    public void testDelete(){
        //Upload a file and delete it to confirm that it's no longer there
        HomePage homePage = signUpAndLogin();
        uploadFile(homePage);
        homePage.navigateFilesTab();
        homePage = new HomePage(driver);
        Assertions.assertFalse(homePage.noFiles(driver));
        deleteFile(homePage);
        Assertions.assertTrue(homePage.noFiles(driver));
    }

    private void deleteFile(HomePage homePage) {
        homePage.deleteFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }


}
