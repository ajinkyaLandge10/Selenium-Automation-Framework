package com.qa.opencart.utils;

import java.time.Duration;
//@formatter:off
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exception.FrameworkException;
import com.qa.opencart.factory.DriverFactory;


import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	private static final Logger log = LogManager.getLogger(ElementUtil.class);

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	/** This Method is Used to Highlight the Web Element */
	 private void isHighlight(WebElement element) {
		 if(Boolean.parseBoolean(DriverFactory.highlight)) {
				jsUtil.flash(element);
			}
	 }

	public By getBy(String locatorType, String locatorValue) {
		By by = null;
		switch (locatorType.toLowerCase().trim()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "class":
			by = By.className(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "css":
			by = By.cssSelector(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		case "tag":
			by = By.tagName(locatorValue);
			break;

		default:
			System.out.println("Wrong locator type is passed " + locatorType);
			throw new FrameworkException("Wrong locator type...");
		}
		return by;
	}
	
	private void logLocator(By locator) {
		log.info("Locator : " + locator);
	}
	private void logLocator(By locator, String value) {
		log.info("Locator : " + locator + "---Value---" + value);
	}
	

//	locatorType = "id", locatorValue = "input-email", value = "Test@gmail.com"
	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}
	@Step("Entering Value: {1} To Element: {0}")
	public void doSendKeys(By locator, String value) {
		logLocator(locator, value);
		getElement(locator).sendKeys(value);
	}

	@Step("Clicking on ELement: {0}")
	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}
	public void doClick(By locator) {
		logLocator(locator);
		getElement(locator).click();
	}

	
	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	
	public String doGetElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	
//	public WebElement getElement(String locatorType, String locatorValue) {
//		return driver.findElement(getBy(locatorType, locatorValue));
//	}
//	public WebElement getElement(By locator) {
//		return driver.findElement(locator);
//	}
	
	// For Highlighting
	public WebElement getElement(By locator) {
		WebElement element =  driver.findElement(locator);
		isHighlight(element);
		return element;
	}
	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		isHighlight(element);
		return element;
	}
	
	
	
	
	
	// WAF :- Capture the text of all the page links and store in ArrayList return List<String>.
	// Here getElements() return list of WebElements, so we have to create empty list of string and add WebElements 1 by 1
	public List<String> getElementTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	// WAF:- Capture specific attribute from the list:
	public List<String> getElementsAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			eleAttrList.add(attrValue);
		}
		return eleAttrList;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	public boolean checkSingleElementPresent(By locator) {
		return driver.findElements(locator).size()==1 ? true : false;
	}
	public boolean checkElementPresent(By locator) {
		return driver.findElements(locator).size()>=1 ? true : false;
	}
	public boolean checkElementPresent(By locator,int totalElements) {
		return driver.findElements(locator).size()==totalElements ? true : false;
	}
	
	public void searchAndCLick(By searchField, By suggestions, String searchKey, String suggName)throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);
		
		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}
	}
	
	public void clickOnElement(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("Total Count : " + eleList.size());

		int count = 1;
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(count + " : " + text);
			count++;
			if (text.contains(eleText)) {
				e.click();
				break;
			}
		}
	}
	
	
//		||********************************* Select Drop-Down Utility *********************************||
	
	private Select createSelect(By locator) {				//--> This method will create select object in every method below..  
		Select select = new Select(getElement(locator));
		return select;
	}
	
	public void doSelectDropDownByIndex(By locator, int index) {
//		Select select = new Select(getElement(locator));    //--> createSelect method will help to skip this part
//		select.selectByIndex(index);
		createSelect(locator).selectByIndex(index);
	}
	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
//		Select select = new Select(getElement(locator));
//		select.selectByVisibleText(visibleText);
		createSelect(locator).selectByVisibleText(visibleText);
	}
	public void doSelectDropDownByValue(By locator, String value) {
//		Select select = new Select(getElement(locator));
//		select.selectByValue(value);
		createSelect(locator).selectByValue(value);
	}
	public int getDropDownOptionsCount(By locator) {
//		Select select = new Select(getElement(locator));
//		return select.getOptions().size();
		return createSelect(locator).getOptions().size();
	}
	public List<String> getDropDownOptions(By locator) {
//		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = createSelect(locator).getOptions();
		List<String> optionsTextList = new ArrayList<String>();

		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}
	public void selectDropDownOptions(By locator, String dropDownValue) {
//		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = createSelect(locator).getOptions();
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}

	}
	public void selectDropDownvalueWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		for(WebElement e: optionsList) {
			String text = e.getText();
			if(text.equals(value)) {
				e.click();
				break;
			}
		}
	}
	
	public boolean isDropDownMultiple(By locator) {
//		Select select = new Select(getElement(locator));
//		return select.isMultiple() ? true : false;
		return createSelect(locator).isMultiple() ? true : false;
	}

	/**
	 * This method is used to select values from DropDown. It can Select:
	 * 1. Single Selection
	 * 2. Multiple Selection
	 * 3. All Selection :- Please Pass "SelectAll" as value Parameter, to select all values from DropDown.
	 * @param locator
	 * @param values
	 */
	public void selectDropDownMultipleValues(By locator,By optionsLocator, String... values) {  // '...'  -> SpreadOperator
//		Select select = new Select(getElement(locator));

		if (isDropDownMultiple(locator)) {
			if (values[0].equalsIgnoreCase("SelectAll")) {
				List<WebElement> optionsList = getElements(optionsLocator);
				for (WebElement e : optionsList) {
					e.click();
				}
			}else {
				for (String value : values) {
					createSelect(locator).selectByVisibleText(value);
				}
			}

		}
	}
	
	
//	||********************************* Actions Class Utility *********************************||
	
	public void doActionsSendKeys(By locator,String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator),value).perform();
	}
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	public void twoLevelMenuHandle(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator))
			.build()
				.perform();
		Thread.sleep(2000);
//		driver.findElement(childMenuLocator).click();
		doClick(childMenuLocator);
	}
	
	public void fourLevelMenuHandle(By parentMenuLocator, By firstChildMenuLocator, By secondChildMenuLocator,
			By thirdChildMenuLocator) throws InterruptedException {

		Actions act = new Actions(driver);
//		driver.findElement(parentMenuLocator).click();
		doClick(parentMenuLocator);
		Thread.sleep(1000);
		act.moveToElement(getElement(firstChildMenuLocator)).build().perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(secondChildMenuLocator)).build().perform();
		Thread.sleep(1000);
//		driver.findElement(thirdChildMenuLocator).click();
		doClick(thirdChildMenuLocator);
	}
	
	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char val[] = value.toCharArray();
		for (char c : val) {
			act.sendKeys(getElement(locator), String.valueOf(c))
				.pause(500)
					.build()
						.perform();
		}
	}
	
	public void doClickByScrollToElement(By locator) {
		Actions act = new Actions(driver);
		act.scrollToElement(getElement(locator))
			.click(getElement(locator))
				.build()
					.perform();
		
	}
	
//	||********************************* Wait Utility *********************************||
	
	/**
	 * This Method Check Element is present inside DOM only
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	public WebElement waitForPresenceOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		isHighlight(element);
		return element;
	}
	/**
	 * This Method Check Element is present inside DOM only
	 * @param locator
	 * @param timeout
	 * @param intervalTime
	 * @return
	 */
	public WebElement waitForPresenceOfElement(By locator, int timeout, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(intervalTime) );
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		isHighlight(element);
		return element;
	}
	/**
	 * This Method is checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeout
	 * @return List<WebElement>
	 */
	public List<WebElement> waitForPresenceOfAllElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	/**
	 * This Method Check Element is present inside DOM as well as on the Page
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	@Step("Waiting For Visibility Of Element:{0} & Timeout:{1}")//Step For Allure Report
	public WebElement waitForVisibilityOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}
	/**
	 * This Method Check Element is present inside DOM as well as on the Page
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @param intervalTime
	 * @return
	 */
	public WebElement waitForVisibilityOfElement(By locator, int timeout, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(intervalTime));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}
	/**
	 * This Method is checking that all elements present on the web page that match the locator are visible.
	 * @param locator
	 * @param timeout
	 * @return List<WebElement>
	 */
	public List<WebElement> waitForVisibilityOfAllElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	// Click & SendKeys with Explicit wait
	public void doClickWithWait(By locator, int timeOut) {
		waitForVisibilityOfElement(locator, timeOut).click();
	}
	public void doSendKeysWithWait(By locator, String value, int timeOut) {
		waitForVisibilityOfElement(locator, timeOut).sendKeys(value);
	}
	
	// Explicit wait On Title
	public String waitForTitleContains(String titleFractionValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFractionValue))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(titleFractionValue + ": Title is Not Present...");
			e.printStackTrace();
		}
		return null;
	}
	
	@Step("Waiting For the Page Title:{0} & TimeOut:{1}") //Step For Allure Report
	public String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(title + ": Title is Not Present...");
			e.printStackTrace();
		}
		return null;
	}
	
	@Step("Waiting For URL:{0} & Timeout:{1}") //Step For Allure Report
	// Explicit wait On URL
	public String waitForURLContains(String URLFractionValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(URLFractionValue))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println(URLFractionValue + ": URL is Not Present...");
			e.printStackTrace();
		}
		return null;
	}
	
	public String waitForURLIs(String URL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(URL))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(URL + ": URL is Not Present...");
			e.printStackTrace();
		}
		return null;
	}
	
	// Explicit wait On Alerts
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	public void acceptJSAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}
	public void dismissJSAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
	}
	public String getJsAlertText(int timeOut) {
		return waitForJSAlert(timeOut).getText();
	}
	public void enterValueOnJsAlert(String value, int timeOut) {
		waitForJSAlert(timeOut).sendKeys(value);
	}
	
	// Explicit wait On Frames
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	public void waitForFrameByIndex(int index, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}
	public void waitForFrameByNameOrId(String NameOrID, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(NameOrID));
	}
	public void waitForFrameByElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	// Explicit wait On Browser Window
	public boolean checkNewWindowExist(int timeOut, int expectedNumberOfWindows) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if(wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
				return true;
		}
		}catch(TimeoutException e) {
			System.out.println("Number of Windows not matched...");
		}
		return false;
	}
	
	/**
	 * This Method checks an element is Visible and Enabled & Click when Element is Ready
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (TimeoutException e) {
			System.out.println("Element is not Clickable or Enabled...");
		}
	}
	
	// Fluent wait
	public WebElement waitForElementWithFluentWait(By locator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(intervalTime))
										.withMessage("TimeOut done..& Element Is Not Found...")
											.ignoring(NoSuchElementException.class)
												.ignoring(ElementNotInteractableException.class);

		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}
	public void waitForFrameWithFluentWait(String frameIdOrName, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(intervalTime))
										.withMessage("TimeOut done..& Frame Is Not Found...")
											.ignoring(NoSuchFrameException.class);

		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
	}
	public Alert waitForJSAlertWithFluentWait(String frameIdOrName, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
									.pollingEvery(Duration.ofSeconds(intervalTime))
										.withMessage("TimeOut done..& Alert is Not appeared...")
											.ignoring(NoAlertPresentException.class);

		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	// WebDriver wait with Fluent wait features:
	public WebElement waitWithWebDriverwaitHavingFluentFeatures(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.pollingEvery(Duration.ofSeconds(2))
				.withMessage("TimeOut done..& Element Is Not Found...")
					.ignoring(NoSuchElementException.class)
						.ignoring(StaleElementReferenceException.class)
							.ignoring(ElementNotInteractableException.class)
								.ignoring(TimeoutException.class);
						  
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
//	||********************************* Custom Wait Utility *********************************||
	
	//Default Interval/Polling Time : 500 MiliSeconds
		public WebElement retryingElement(By locator, int timeOut) {
			WebElement element = null;
			int attempt = 0;

			while (attempt < timeOut) {
				try {
					element = getElement(locator);
					System.out.println("Element is Found in : "+ attempt +" attempts");
					break;
				} catch (NoSuchElementException e) {
					System.out.println("Element is not Found in : " + attempt +" attempts");
					try {
						Thread.sleep(500);//Default Polling Time
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				attempt++;
			}
			if(element == null) {
				System.out.println("Element is Not Found...tried for " + timeOut + " times " + " With Interval of " + 500 + " Miliseconds");
				throw new FrameworkException("No Such Element..");
			}
			isHighlight(element);
			return element;
		}
		
		//Overloaded Method with Interval/Polling Time parameter
		public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
			WebElement element = null;
			int attempt = 0;

			while (attempt < timeOut) {
				try {
					element = getElement(locator);
					System.out.println("Element is Found in : "+ attempt +" attempts");
					break;
				} catch (NoSuchElementException e) {
					System.out.println("Element is not Found in : " + attempt +" attempts");
					try {
						Thread.sleep(intervalTime);//Custom Polling Time
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				attempt++;
			}
			if(element == null) {
				System.out.println("Element is Not Found...tried for " + timeOut + " times " + " With Interval of " + intervalTime + " Miliseconds");
				throw new FrameworkException("No Such Element..");
			}
			isHighlight(element);
			return element;
		}

		/**
		 * This Method help in checking the state of page (loading,interactive & Complete).
		 * If page state is 'Complete' return true otherwise in case of Loading & interactive returns false.
		 * @param timeOut
		 * @return boolean
		 */
		public boolean isPageLoaded(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();
			return Boolean.parseBoolean(flag);
		}
	
	
//		||********************************* Streams Utility *********************************||	
		
		public void printListUsingStreams(By locator) {
			driver.findElements(locator)
				.stream()
					.forEach(e -> System.out.println(e.getText()));
		}	
		
		public List<String> getListUsingStreams(By locator) {
			return driver.findElements(locator)
			.stream()
				.map(e -> e.getText())
					.collect(Collectors.toList());
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
