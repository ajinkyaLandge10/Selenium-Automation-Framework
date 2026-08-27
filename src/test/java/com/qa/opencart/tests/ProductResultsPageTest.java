package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductResultsPageTest extends BaseTest{

	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	//Providing TestData with Method
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"MacBook","MacBook Pro",4},
			{"MacBook","MacBook Air",4},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Samsung","Samsung Galaxy Tab 10.1",7},
			{"iPhone","iPhone",6},
			{"HP","HP LP3065",3},
		};
	}
	@Test(dataProvider = "getSearchData")
	public void productImagesTest1(String searchKey, String productName, int imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage  = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), imageCount) ;
	}

	
	
	
	//Providing TestData with Excel
	@DataProvider
	public Object[][] getProductSearchExcelTestData() {
		return ExcelUtil.getTestData(AppConstants.PRODUCTS_DATA_SHEET_NAME);
	}	
	@Test(dataProvider = "getProductSearchExcelTestData")
	public void productImagesTest2(String searchKey, String productName, String imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage  = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(String.valueOf(productInfoPage.getProductImagesCount()), imageCount) ;
	}
	
	
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("MacBook");
		productInfoPage  = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDetailsMap = productInfoPage.getProductDetails();
		
		softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productDetailsMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDetailsMap.get("Availability"), "Out Of Stock");
		
		softAssert.assertEquals(productDetailsMap.get("Price"), "$2,000.00");
		softAssert.assertEquals(productDetailsMap.get("ExTaxPrice"), "$2,000.00");
		
		softAssert.assertAll();
	}
}
