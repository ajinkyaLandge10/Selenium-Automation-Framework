package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailId() {
		return "TestAutomation" + System.currentTimeMillis() +"@opencart.com";
	}
	
	
	//Providing TestData with Method
	@DataProvider
	public Object[][] getUserRegData() {
		return new Object[][] {
			{"Josh","Williams", "9815263740", "Williams@3121", "yes"},
			{"Toby", "Brown", "9826374150", "Brown@4232", "no"},
			{"Harry","Lewis","9837485260", "Lewis@5343", "yes"},
		};
	}
	@Test(dataProvider = "getUserRegData")
	public void userRegisterTest1(String firstName, String lastName, String telephone, String password, String subscribe) {
		boolean isRegistersuccessful = registerPage.userRegister(firstName, lastName, getRandomEmailId(), telephone, password, subscribe);
		Assert.assertTrue(isRegistersuccessful);
	}
	
	
	//Providing TestData with Excel
	@DataProvider
	public Object[][] getUserRegTestExcelData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_DATA_SHEET_NAME);
		return regData;
	}	
	@Test(dataProvider = "getUserRegTestExcelData")
	public void userRegisterTest2(String firstName, String lastName, String telephone, String password, String subscribe) {
		boolean isRegistersuccessful = registerPage.userRegister(firstName, lastName, getRandomEmailId(), telephone, password, subscribe);
		Assert.assertTrue(isRegistersuccessful);
	}
	
	
}
