package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static String firstname = "Zeina", lastname = "Kandil", username = "ZK", password = "ZeinaPass", noteTitle = "SuperDuperDrive", noteDescription = "Once again - SuperDuperDrive", credURL = "sdd.com";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void requestLoginForHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void requestLoginForResultPage() {
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void AuthorisationTest() {
		//SignUp then login then logout and lose access

		//Signing up with Name, username and password
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputFirstName.sendKeys(firstname);
		inputLastName.sendKeys(lastname);
		inputUsername.sendKeys(username);
		inputPassword.sendKeys(password);

		driver.findElement(By.id("signup")).click();
		Assertions.assertEquals("Login", driver.getTitle());

		//Now that user signed up they need to login to their account
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputUsername.sendKeys(username);
		inputPassword.sendKeys(password);

		driver.findElement(By.id("login")).click();
		Assertions.assertEquals("Home", driver.getTitle());

		//Log out and test that user is no longer authorised
		driver.findElement(By.id("logout")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
