package Gluecode;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import Pages.LibraryAppHomePage;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LogInStep {
	static WebDriver driver;
	static LibraryAppHomePage LibraryAppHome;
	
	@BeforeAll
	public static void setUp() {
		File file = new File("src/main/resources/msedgedriver.exe");
		System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
		
		driver = new EdgeDriver();
		LibraryAppHome = new LibraryAppHomePage(driver);
	}
	
	@AfterAll
	public static void closeDriver() {
		driver.quit();
	}
	
	@Given("the user is on the homepage")
	public void the_user_is_on_the_homepage() {
	    LibraryAppHome.navigateTo();
	}

	@When("the user enters the correct username")
	public void the_user_enters_the_correct_username() {
	    LibraryAppHome.inputUsername("snicholes");
	}

	@When("the user enters the correct password")
	public void the_user_enters_the_correct_password() {
	    LibraryAppHome.inputPassword("pass");
	}

	@When("the user clicks the login button")
	public void the_user_clicks_the_login_button() {
	    LibraryAppHome.submitLogin();
	}

	@Then("the nav will show the user's first name")
	public void the_nav_will_show_the_user_s_first_name() {
		assertTrue(LibraryAppHome.getNavText().contains("Sierra"));
		LibraryAppHome.logOut();
	}

	@When("the user enters an incorrect username")
	public void the_user_enters_an_incorrect_username() {
	    LibraryAppHome.inputUsername("asdfghjkl");
	}

	@Then("an incorrect credentials message will be displayed")
	public void an_incorrect_credentials_message_will_be_displayed() {
		String message = LibraryAppHome.getMessageBoxText().toLowerCase();
	    assertTrue(message.contains("incorrect credentials"));
	}

	@When("the user enters the incorrect password")
	public void the_user_enters_the_incorrect_password() {
	    LibraryAppHome.inputPassword("12345678987654321");
	}
	
	@When("the user enters the username {string}")
	public void the_user_enters_the_username(String username) {
	    LibraryAppHome.inputUsername(username);
	}

	@When("the user enters the password {string}")
	public void the_user_enters_the_password(String password) {
	    LibraryAppHome.inputPassword(password);
	}

	@Then("an invalid input message will be displayed")
	public void an_invalid_input_message_will_be_displayed() {
		String message = LibraryAppHome.getMessageBoxText().toLowerCase();
	    assertTrue(message.contains("invalid input"));
	}
}
