package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{



	@BeforeClass
	public void productInfoSetup() {
		accPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	/*
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro"},
			{"Macbook", "MacBook Air"},
			{"Apple", "Apple Cinema 30\""}
		};
	}*/
	
	@DataProvider
	public Object[][] getProductData() {
		Object prodData[][]=ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
		return prodData;
	}
	@Test(dataProvider = "getProductData")
	public void productInfoHeaderTest(String productName,String mainProductName) {
		searchResultsPage=accPage.doSearch(productName);
		productInfoPage=searchResultsPage.selectProduct(mainProductName);
		Assert.assertEquals(productInfoPage.getProductHeaderText(),mainProductName);
	}
	
	@Test
	public void productImagesTest() {
		searchResultsPage=accPage.doSearch("Macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Air");
		Assert.assertEquals(productInfoPage.getProductImagesCount(),Constants.MACBOOK_IMAGES_COUNT);
	}
	
	@Test
	public void getProductInfoTest() {
		searchResultsPage=accPage.doSearch("Macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		actProductInfoMap.forEach((k,v) -> System.out.println(k+" : "+v));
		softassert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softassert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softassert.assertEquals(actProductInfoMap.get("Reward Points"), "800");
		softassert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
		softassert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softassert.assertAll();
		
	}
}
