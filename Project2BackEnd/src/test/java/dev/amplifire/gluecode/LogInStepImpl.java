package dev.amplifire.gluecode;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import dev.amplifire.app.pages.GlobalLibraryHomePage;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LogInStepImpl {
	
	static WebDriver driver;
	static GlobalLibraryHomePage globalLibraryHome;
	
	
	@BeforeAll
	public static void setUpDriver() {
		File file= new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver= new ChromeDriver();
		globalLibraryHome = new GlobalLibraryHomePage(driver);
		}
	@AfterAll
	public static void closeDriver() {
		driver.quit();	
	}
	
	@Given("the user is on the homepage")
	public void the_user_is_on_the_homepage() {
	 globalLibraryHome.navigateTo();
	}

	@When("the user enters the correct username")
	public void the_user_enters_the_correct_username() {
	    // Write code here that turns the phrase above into concrete actions
		globalLibraryHome.inputUsername("dro");
	}

	@When("the user enters the correct password")
	public void the_user_enters_the_correct_password() {
	    // Write code here that turns the phrase above into concrete actions
		globalLibraryHome.inputPassword("l");
	}

	@When("the user clicks on the login button")
	public void the_user_clicks_on_the_login_button() {
	    globalLibraryHome.submitLogin();
	}

	@Then("the nav will show the user's first name.")
	public void the_nav_will_show_the_user_s_first_name() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("the user enters an incorrect username")
	public void the_user_enters_an_incorrect_username() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("the user enters enter the correct password")
	public void the_user_enters_enter_the_correct_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("the incorrect credentials message will be displayed.")
	public void the_incorrect_credentials_message_will_be_displayed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("the user enters an incorrect password")
	public void the_user_enters_an_incorrect_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


}
