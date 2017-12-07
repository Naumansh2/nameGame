package com.willowTree.nameGame.Mobile;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.willowTree.nameGame.TestBase;

public class AppMainPageMobile extends TestBase{
	/**
	 * @author Nauman SH.
	 * @throws InterruptedException 
	 * @For WillowTree
	 */
	@BeforeClass
	public void Serverstart() throws MalformedURLException, InterruptedException {
		startServer();		
	}
	
	@Parameters({"browser","mobileDevice","osVersion"})
	@BeforeMethod
	public void start(@Optional("chrome")String browser,@Optional("Emulator")String mobileDevice,@Optional("6.0")String osVersion) throws MalformedURLException, InterruptedException {
		MobileTablet(browser,mobileDevice,osVersion);		
	}
	
	@Test(enabled=true)
	public void Tc001_VerifyTitle() {
		String title = Adriver.getTitle();
		Assert.assertEquals(title, "name game");
	}
	
	
	@AfterMethod
	public void close() throws InterruptedException {
		TearDownBrowserMobile();
	}
}
