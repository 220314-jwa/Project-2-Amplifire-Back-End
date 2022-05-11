package dev.amplifire;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class GlobalLibraryHomePage {
	public static void main(String[] args) {
		// load in the edgedriver file and set that up as the driver we're using
		File file = new File("src/main/resources/msedgedriver.exe");
		System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
		
		WebDriver driver = new EdgeDriver();
		
		
		// send the driver to the page
		//Using the main page of Global Library
		driver.get("https://localhost:4200");
		
		// by inspecting the page we found the element #inputUsername and #inputPassword
		// also #login-button
		WebElement inputUsername = driver.findElement(By.id("usernameInput"));
		WebElement inputPassword = driver.findElement(By.id("passwordInput"));
		WebElement loginButton = driver.findElement(By.id("logInBtn"));
		
		// Send the information to site for Selenium testing
		inputUsername.sendKeys("Dro");
		inputPassword.sendKeys("L");
		loginButton.click();
		
		
		
		
		// Always remember to close out your driver
		driver.close();
		
	}

}
