package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	private By ProductHeader=By.cssSelector("div#content h1");
	private By ProductImages=By.cssSelector("div#content img");
	private By ProductMetaData= By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By ProductPriceData= By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
	private By qty=By.id("input-quantity");
	private By addToCart=By.id("button-cart");
	private By successMessage=By.cssSelector("div.alert.alert-success.alert-dismissible");
	
	private Map<String, String> ProductInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleutil=new ElementUtil(driver);
	}
	
	public String getProductHeaderText() {
		return eleutil.doElementGetText(ProductHeader);
	}
	
	public int getProductImagesCount() {
		return eleutil.waitForElementsToBeVisible(ProductImages, Constants.DEFAULT_TIME_OUT).size();
	}
	
	public Map<String, String> getProductInfo()
	{
		 ProductInfoMap=new LinkedHashMap<String, String>();
		 ProductInfoMap.put("productname", getProductHeaderText().trim());
		 ProductInfoMap.put("imagescount", String.valueOf(getProductImagesCount()));
		 getProductMetaData();
		 getProductPriceData();
		 return ProductInfoMap;
	}
	
	private void getProductMetaData() {
		//Brand: Apple
				//	Product Code: Product 18
				//Reward Points: 800
				//Availability: Out Of Stock
				
				List<WebElement> MetaDataList = eleutil.getElements(ProductMetaData);
				
				for(WebElement ele: MetaDataList) {
					String text=ele.getText().trim();
					String Meta[]=text.split(":");
					String metaKey=Meta[0].trim();
					String metaValue=Meta[1].trim();
					ProductInfoMap.put(metaKey, metaValue);
				}
	}
	
	private void getProductPriceData() {
		//$2,000.00
		//Ex Tax: $2,000.00
				List<WebElement> MetaPriceList = eleutil.getElements(ProductPriceData);
				String price=MetaPriceList.get(0).getText().trim();
				String exPrice=MetaPriceList.get(1).getText().trim();
				ProductInfoMap.put("price",price);
				ProductInfoMap.put("exTaxPrice",exPrice);
	}
}
