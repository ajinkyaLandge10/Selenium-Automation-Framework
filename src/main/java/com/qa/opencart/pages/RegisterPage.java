package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// By locators or Page Object Repository:
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline']//input[@type='radio'])[1]");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline']//input[@type='radio'])[2]");
	private By agreeCheckBox = By.xpath("//input[@name='agree']");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By successMessg = By.xpath("//div[@id='content']/h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register"); 
	
	
	// Page Constructor:
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	// Page Actions/methods:
	
	public boolean userRegister(String FName, String LName, String Email, String TelephoneNo, String Password,String Subscribe) {
		
		eleUtil.waitForVisibilityOfElement(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(FName);
		eleUtil.doSendKeys(this.lastName, LName);
		eleUtil.doSendKeys(this.email, Email);
		eleUtil.doSendKeys(this.telephone, TelephoneNo);
		eleUtil.doSendKeys(this.password, Password);
		eleUtil.doSendKeys(this.confirmPassword, Password);
		
		if(Subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String successMesg = eleUtil.waitForVisibilityOfElement(successMessg, AppConstants.MEDIUM_DEFAULT_WAIT).getText();
		System.out.println(successMesg);
		
		if(successMesg.contains(AppConstants.REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
