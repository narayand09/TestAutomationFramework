package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 101 : Design login Page for Open Cart Application ")
@Story("US 100 : Design login Page Feature..")
public class LoginPageTest extends BaseTest{
	
	@Test
	@Description("login Page Title Test")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageTitleTest()
	{
		String ActualTitle=loginPage.getLoginPageTitle();
		System.out.println("Login Page title :"+ActualTitle);
		Assert.assertEquals(ActualTitle, Constants.LOGIN_PAGE_TITLE, Errors.LOGIN_PAGE_TITLE_MISMATCHED);
	}
	
	@Test
	@Description("login PageURL Test")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageURLTest()
	{
		String ActualURL=loginPage.getLoginPageURL();
		System.out.println("Login Page URL :"+ActualURL);
		Assert.assertTrue(ActualURL.contains(Constants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Test
	@Description("forgot PwdLink Test")
	@Severity(SeverityLevel.CRITICAL)
	public void forgotPwdLinkTest()
	{
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Test
	@Description("login Test")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertTrue(accPage.isAccountPageHeaderExist());
	}
	
	@Test
	@Description("RegisterLink Exist")
	@Severity(SeverityLevel.CRITICAL)
	public void isRegisterLinkExist()
	{
		Assert.assertTrue(loginPage.isRegisterLinkExist());
	}

}
