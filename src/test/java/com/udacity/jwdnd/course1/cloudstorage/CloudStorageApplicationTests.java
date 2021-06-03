package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	protected WebDriver driver;
	@LocalServerPort
	protected int port;

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
	public void viewLogin() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public HomePage signUpAndLogin() {
		driver.get("http://localhost:" + this.port + "/signup");
		Signup signup = new Signup(driver);
		signup.setFirstName("Harry");
		signup.setLastName("Potter");
		signup.setUserName("TheChosenOne");
		signup.setPassword("Expelliarmus");
		signup.signUp();
		driver.get("http://localhost:" + this.port + "/login");
		Login login = new Login(driver);
		login.setUserName("TheChosenOne");
		login.setPassword("Expelliarmus");
		login.login();

		return new HomePage(driver);
	}
}
