package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.utils.Browser;
import com.qa.opencart.utils.Errors;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static final Logger log=Logger.getLogger(DriverFactory.class);
	
	/**
	 * It used to initialize webdriver on the basis of given browser name
	 * @param BrowserName
	 * @return
	 */
	public WebDriver init_driver(Properties prop)
	{
		String BrowserName=prop.getProperty("browser").trim();
		System.out.println("Browser name is :"+BrowserName);
		log.info("Browser name is :"+BrowserName);
		highlight=prop.getProperty("highlight").trim();
		optionsManager=new OptionsManager(prop);
		if(BrowserName.equalsIgnoreCase(Browser.CHROME_BROWSER_VAUE))
		{
			WebDriverManager.chromedriver().setup();
			//driver=new ChromeDriver(optionsManager.getChromeOptions());
			//System.setProperty(Browser.CHROME_DRIVER_BINARY_KEY, Browser.CHROME_DRIVER_PATH);
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(BrowserName.equalsIgnoreCase(Browser.FIREFOX_BROWSER_VAUE))
		{
			WebDriverManager.firefoxdriver().setup();
			//driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			//System.setProperty(Browser.GECKO_DRIVER_BINARY_KEY, Browser.FIREFOX_DRIVER_PATH);
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if(BrowserName.equalsIgnoreCase(Browser.SAFARI_BROWSER_VAUE))
		{
			WebDriverManager.safaridriver().setup();
			//driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		else 
		{
			System.out.println(Errors.BROWSER_NOT_FOUND_ERROR_MESSG + BrowserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().fullscreen();
		getDriver().get(prop.getProperty("url"));
		log.info(prop.getProperty("url") + " .... url is launched....");
		return getDriver();
	}
	
	/**
	 * this will return the thread local copy of the webdriver(driver)
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	
	/**
	 * This method initializes the properties on the basis of given
	 * environment STG/QA/PROD
	 * @return
	 */
	public Properties init_prop()
	{
		prop=new Properties();
		FileInputStream ip=null;
		String envName=System.getProperty("env");
		System.out.println("Running test on environment : "+envName);
		if (envName == null) {
			System.out.println("No env is given....hence running it on QA");
			log.info("No env is given....hence running it on QA");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		else {
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					log.info("running it on QA");
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					log.info("running it on Stage");
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("please pass the right environment....." + envName);
					log.error("please pass the right environment....." + envName);
					log.warn("env name is not found....");
					log.fatal("env is not found....");
					break;
				}
			} catch (Exception e) {

			}

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}
}
