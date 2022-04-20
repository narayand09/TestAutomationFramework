package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;


public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	@Description("pre login for accounts page tests")
	public void accPageSetup()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	@Description("accounts Page Title Test")
	@Severity(SeverityLevel.NORMAL)
	public void accountsPageTitleTest()
	{
		String accPageTitle=accPage.getAccountPageTitle();
		Assert.assertEquals(accPageTitle, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
	
	@Test
	public void accountsPageHeaderTest()
	{
		Assert.assertTrue(accPage.isAccountPageHeaderExist());
	}
	
	@Test
	public void accountsPageSearchTest()
	{
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Test
	public void accSectionTest()
	{
		List<String> actSectionList=accPage.getAccountsPageSectionsList();
		System.out.println(actSectionList);
		Assert.assertEquals(actSectionList, Constants.expAccSecList);
	}
	
	@Test
	public void searchHeaderTest() {
		searchResultsPage=accPage.doSearch("Macbook");
		String actSearchHeaderValue=searchResultsPage.getResultsPageHeaderValue();
		Assert.assertTrue(actSearchHeaderValue.contains("Macbook"));
	}
	
	@Test
	public void searchProductTest() {
		searchResultsPage=accPage.doSearch("iMac");
		int actProductCount=searchResultsPage.getProductSearchCount();
		Assert.assertEquals(actProductCount, Constants.IMAC_PRODUCT_COUNT);
	}
	
	@Test
	public void getSearchProductListTest() {
		searchResultsPage=accPage.doSearch("iMAC");
		List<String> actProductList = searchResultsPage.getProductResultsList();
		System.out.println(actProductList);
	}

}
