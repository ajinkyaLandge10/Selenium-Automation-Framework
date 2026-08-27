package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	// By locators or Page Object Repository:
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@type='submit']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By registerLink = By.linkText("Register");
	private By loginErrorMessg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	
	// Page Constructor:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// Page Actions/methods:
	@Step("Getting Login Page Title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page Title : " + title);
		return title;
	}

	@Step("Getting Login Page URL")
	public String getLoginPageURL() {	
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page URL : " + url);
		return url;
	}

	@Step("Verifying Forgot Password Link Exist")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForVisibilityOfElement(forgotPwdLink, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}

	@Step("Verifying App Logo Exist")
	public boolean isLogoExist() {
		return eleUtil.waitForVisibilityOfElement(logo, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}

	@Step("UserName : {0} & Password : {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("Credentials are : " + username + " : " + pwd);
		eleUtil.waitForVisibilityOfElement(userName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(username);
		eleUtil.doActionsSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("Navigating To Register Page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForVisibilityOfElement(registerLink, AppConstants.MEDIUM_DEFAULT_WAIT).click();
		return new RegisterPage(driver);
	}
	
	public boolean doLoginWithWrongCredentials(String userName,String password) {
		
		eleUtil.waitForVisibilityOfElement(this.userName, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
	    eleUtil.waitForVisibilityOfElement(this.userName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(userName);

	    eleUtil.waitForVisibilityOfElement(this.password, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
	    eleUtil.doActionsSendKeys(this.password, password);
	    
		eleUtil.doClick(loginBtn);
		String errorMessg = eleUtil.doElementGetText(loginErrorMessg);
		System.out.println(errorMessg);
		if(errorMessg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	

	
}
