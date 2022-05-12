package dev.amplifire.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GlobalLibraryHomePage {
WebDriver driver;

@FindBy(id="usernameLogin")
WebElement usernameInput;

@FindBy(id="passwordLogin")
WebElement passwordInput;

WebElement logInBtn;

public GlobalLibraryHomePage(WebDriver driver) {
	
	this.driver = driver;
	PageFactory.initElements(driver, this);
	
	
}
public void navigateTo() {
	driver.get("");// input path in between parentheses. 
}

public void inputUsername(String username){
	usernameInput.sendKeys(username);	
}
public void inputPassword(String password) {
	passwordInput.sendKeys(password);
}
public void submitLogin() {
	logInBtn.click();
}

}

