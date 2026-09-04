package com.qa.opencart.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design OpenCart Login Page")
@Story("US 101: Login Page Feature")
@Feature("F102 : Feature Login Page")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(LoginPageTest.class);
	
	@Description("Login Page Title Test....")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		log.info("Actual Login Page Title : " + actualTitle);
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("Login Page URL Test....")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("Verifying Forgot Password Link Test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Description("Verifying App Logo Test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	public void appLogoExistTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}
	
	@Description("Verifying User Is Able To Login With Correct Credentials....")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 5)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
