package com.willowTree.nameGame;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AppMainPage extends TestBase{
	/**
	 * @author Nauman SH.
	 * @For WillowTree
	 */
	@BeforeMethod
	public void start() throws IOException {
		InitializeDesktop("LocalDriver","Chrome");
	}
	
	@Test(enabled=true)
	public void Tc001_VerifyTitle() {
		String title = driver.getTitle();
		Assert.assertEquals(title, "name game");
	}
	
	
	@AfterMethod
	public void close() throws InterruptedException {
		TearDownBrowser();
	}
}
