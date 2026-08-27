package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.xpath("//div[@id='content']//h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]//li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]//li");

	//private Map<String, String> productMap = new HashMap<String, String>(); // Order is Not Maintained / Random Order
	//private Map<String, String> productMap = new LinkedHashMap<String, String>(); // Order is Maintained as per insertion order
	private Map<String, String> productMap = new TreeMap<String, String>(); // Sort Keys on the basis of alphabetical order

	// Page Constructor:
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// Page Actions/methods:
	public String getProductHeaderName() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("Product Header : " + productHeaderVal);
		return productHeaderVal;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForVisibilityOfAllElements(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
		System.out.println("Product : " + getProductHeaderName() + " Images Count : " + imagesCount);
		return imagesCount;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	private void getProductMetaData() {
		List<WebElement> metaDataList = eleUtil.waitForVisibilityOfAllElements(productMetaData,AppConstants.MEDIUM_DEFAULT_WAIT);

		for (WebElement e : metaDataList) {
			String metaData = e.getText();
			String metaKey = metaData.split(":")[0].trim();
			String metaVal = metaData.split(":")[1].trim();

			productMap.put(metaKey, metaVal);
		}
	}

	private void getProductPriceData() {
		List<WebElement> metaPriceList = eleUtil.waitForVisibilityOfAllElements(productPriceData, AppConstants.MEDIUM_DEFAULT_WAIT);
		
		String productPrice = metaPriceList.get(0).getText();
		String productExTaxPrice = metaPriceList.get(1).getText().split(":")[1].trim();
		
		productMap.put("Price", productPrice);
		productMap.put("ExTaxPrice", productExTaxPrice);	
	}
	
	public Map<String, String> getProductDetails() {
		productMap.put("ProductName", getProductHeaderName());
		getProductMetaData();
		getProductPriceData();
		
		System.out.println(productMap);
		return productMap;
	}
	
	
	
}


