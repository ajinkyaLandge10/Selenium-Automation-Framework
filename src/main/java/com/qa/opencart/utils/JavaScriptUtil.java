package com.qa.opencart.utils;

//@formatter:off
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
	}

	public String getTitleByUsingJs() {
//		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return document.title").toString();
	}
	public String getURLByUsingJs() {
//		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return document.URL").toString();
	}
	public void navigateBackwardUsingJS() {
		js.executeScript("history.go(-1)");
	}
	public void pageRefreshUsingJS() {
		js.executeScript("history.go(0)");
	}
	public void navigateForwardUsingJS() {
		js.executeScript("history.go(1)");
	}

//	||********************************* JavaScript Alert *********************************||

	public void generateJSAlert(String mesg) {
		js.executeScript("alert('" + mesg + "')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	public void generateJSConfirm(String mesg) {
		js.executeScript("confirm('" + mesg + "')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	public void generateJSPrompt(String mesg, String value) {
		js.executeScript("prompt('" + mesg + "')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}

	// return all text from Web Page
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

//	||********************************* JavaScript Scrolling *********************************||
	
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	public void scrollMiddlePage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	}
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '"+height+"');");
	}
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
	}
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public void zoomChromeEdgeSafari(int zoomPercentage) {
		String zoom = "document.body.style.zoom = '"+zoomPercentage+"%'";
		js.executeScript(zoom);
	}
	public void zoomFirefox(int zoomPercentage) {
		String zoom = "document.body.style.MozTransform = 'scale("+zoomPercentage+")'";
		js.executeScript(zoom);
	}

	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 5; i++) {
			changeColor("rgb(0,200,0)", element);// Green
			changeColor(bgcolor, element);// Purple
		}
	}
	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		}catch(InterruptedException e) {
			
		}
	}
	
	//Click & SendKeys using JS (Not Recommended)
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	public void sendKeysUsingWithId(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
						//document.getElementById('input-email').value ='test@gmail.com'
	}
	
	
	
	
	
	
	
	
	
	
}
