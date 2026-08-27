package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// By locators or Page Object Repository:
	private By logoutLink = By.linkText("Logout");
	private By search = By.xpath("//input[@name='search']");
	private By searchIcon = By.xpath("//div[@id='search']//button[@type='button']");
	private By accHeaders = By.xpath("//div[@id='content']//h2");
	
	// Page Constructor:
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	// Page Actions/methods:
	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE,AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Acc page Title : " + title);
		return title;
	}

	public String getAccPageURL() {	
		String url = eleUtil.waitForURLContains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION,AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Acc page URL : " + url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForVisibilityOfElement(logoutLink, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}
	
	public void logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
	}
	
	public boolean isSearchFieldExist() {
		return eleUtil.waitForVisibilityOfElement(search, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}
	
	public List<String> getAccountsHeader() {
		 List<WebElement> headersList = eleUtil.waitForVisibilityOfAllElements(accHeaders, AppConstants.MEDIUM_DEFAULT_WAIT);
		 List<String> headersValList = new ArrayList<String>();
		 for(WebElement e : headersList) {
			 String text = e.getText();
			 headersValList.add(text);
		 }
		 return headersValList;
	}
	
	public SearchResultsPage doSearch(String searchKey ) {
		eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
		eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		
		return new SearchResultsPage(driver);
	}
	
}
