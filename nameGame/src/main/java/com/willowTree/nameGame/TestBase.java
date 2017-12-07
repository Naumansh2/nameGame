package com.willowTree.nameGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class TestBase {

	public WebDriver driver;
	DesiredCapabilities capabilities;
	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	public AppiumDriver<WebElement> Adriver;
	Properties prop=new Properties();
	
	@BeforeClass
	public void LoadProperties() throws IOException{
		File filepath=new File(System.getProperty("user.dir")+"\\config.properties");
		FileInputStream file=new FileInputStream(filepath);
		prop.load(file);
	}
	
	public void InitializeDesktop(String driverType,String driverName) {
		Desktop(driverType,driverName);
		URL(prop.getProperty("url"));
	}
	
	public void Desktop(String driverType,String driverName) {
		if(driverType.equalsIgnoreCase("LocalDriver")) {
			LocalDriver(driverName);
		}
	}
	
	public void MobileTablet(String browser,String mobileDevice,String platformVersion) throws MalformedURLException {
		AndroidDriver(browser,mobileDevice,platformVersion);
		MURL(prop.getProperty("url"));
	}
	public void LocalDriver(String driverName) {
		if(driverName.equalsIgnoreCase("chrome") || driverName.isEmpty()) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions(); 
			options.addArguments("--disable-popup-blocking");
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("start-maximized");
			DesiredCapabilities Mycapabilities = capabilities();
			Mycapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver=new ChromeDriver(Mycapabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			System.out.println(driver);
		}else if(driverName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/drivers/geckodriver.exe");
			DesiredCapabilities Mycapabilities = capabilities();
			driver=new FirefoxDriver(Mycapabilities);
		}
	}
	
	public WebElement waitforEle(By ele,WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		 WebElement wa = wait.until(ExpectedConditions.presenceOfElementLocated(ele));
	return wa;
	}
	public List<WebElement> waitforList(By ele,WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		List<WebElement> wa = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ele));
	return wa;
	}
	public WebElement waitOnly(WebElement ele,WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement wa = wait.until(ExpectedConditions.visibilityOf(ele));
	return wa;
	}
	
	public List<WebElement> waitforListMobile(By ele,AppiumDriver<WebElement> Adriver) {
		WebDriverWait wait = new WebDriverWait(Adriver, 30);
		List<WebElement> wa =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ele));
	return wa;
	}
	public WebElement waitforEleMobile(By ele,AppiumDriver<WebElement> Adriver) {
		WebDriverWait wait = new WebDriverWait(Adriver, 60);
		MobileElement wa = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(ele));
	return wa;
	}
	public WebElement waitOnlyMobile(WebElement ele,AppiumDriver<WebElement> Adriver) {
		WebDriverWait wait = new WebDriverWait(Adriver, 30);
		WebElement wa = wait.until(ExpectedConditions.visibilityOf(ele));
	return wa;
	}
	public DesiredCapabilities capabilities() {
		DesiredCapabilities  capabilities=new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
		return capabilities;
	}
	
	public void AndroidDriver(String browser,String mobileDevice,String platformVersion) throws MalformedURLException {
		DesiredCapabilities capabilities=new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,mobileDevice);
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,browser);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,platformVersion);
		Adriver=new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		Adriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	public void URL(String url) {
		driver.get(url);
	}
	
	public void MURL(String url) {
		Adriver.get(url);
	}
	public void TearDownBrowser() throws InterruptedException {
		driver.close();
	}
	
	public void TearDownBrowserMobile() throws InterruptedException {
		Adriver.close();
	}
	
	public void Touch(MobileElement ele,AppiumDriver<WebElement> Adriver) {
		TouchAction touch=new TouchAction(Adriver);
		touch.tap(ele).release().perform();
	}
	public void AppiumServer() {
		ServerSocket serverSocket;
		int port = 472;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Socket closed");
		}
		
	}
	
	public void startServer() throws InterruptedException {
		
		AppiumServer();
		//Set Capabilities
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("noReset", "false");
		
		//Build the Appium service
		builder = new AppiumServiceBuilder();
				
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withCapabilities(capabilities);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		System.out.println("Service started");
	}
	
	
	public void stopServer() {
		service.stop();
	}
 
	public boolean checkIfServerIsRunnning(int port) {
		
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}	
 

}
