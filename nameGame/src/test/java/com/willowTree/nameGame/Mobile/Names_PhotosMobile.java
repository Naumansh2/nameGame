package com.willowTree.nameGame.Mobile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.willowTree.nameGame.TestBase;
import com.willowTree.nameGame.UI.GamePage;
import com.willowTree.nameGame.UI.GamePageMobile;

public class Names_PhotosMobile extends TestBase {
	/**
	 * @author Nauman
	 * @For WillowTree
	 */
	GamePageMobile gamepage;
	
	@Parameters({"browser","mobileDevice","osVersion"})
	@BeforeMethod
	public void start(@Optional("chrome")String browser,@Optional("Emulator")String mobileDevice,@Optional("6.0")String osVersion) throws MalformedURLException, InterruptedException {
		MobileTablet(browser,mobileDevice,osVersion);		
	}
	
	
	@Test(priority=1,enabled=true)
	public void Tc006_Verify_Names_Photos_Change() throws InterruptedException {
		/**
		 * Test method verifies that name and photos change after correct selection
		 */
		gamepage=new GamePageMobile(Adriver);
		List<String> screen1_Name=new ArrayList<String>();
		List<String> screen2_Name=new ArrayList<String>();
		Map<String, String> Name_Photos = gamepage.Game_Nmaes_Photos_Change();
		Map<String, String> sortedName_Photos = new TreeMap<String, String>(Name_Photos);
		for (Map.Entry<String, String> entry : sortedName_Photos.entrySet())
		{
		    if(entry.getKey().contains("screen0")) {
		    	screen1_Name.add(entry.getValue());
		    }else if(entry.getKey().contains("screen1")) {
		    	screen2_Name.add(entry.getValue());
		    }
		}
			
		boolean result = screen1_Name.equals(screen2_Name);
		Assert.assertFalse(result, "Test failed as arrays matches");
		}
	
	@Test(priority=2,enabled=true)
	public void Tc007_Verify_Names_Fail_HighRepetition() throws InterruptedException {
		/**
		 * Test verifies that Wrongly selected names are shown more frequently
		 * Since no clarity in requirements: In 5 games wrongly if No. of repetition for wrongly selected names is >0, Test case will pass 
		 */
		//List<String> RepeatedName=new ArrayList<String>();
		int RepeatedName = 0;
		int j=0;
		int NumberOfTimesGameToBePalyed=5;
		boolean result=false;
		gamepage=new GamePageMobile(Adriver);
		List<String> Name_Fail = gamepage.Game_IncorrectSelection_Repetition(NumberOfTimesGameToBePalyed);
		for (String nameFail:Name_Fail)
		{
			j++;
			for(int i=j;i<Name_Fail.size();i++) {
				if(nameFail.contentEquals(Name_Fail.get(i))) {
					RepeatedName++;
				}
			}
		   
		}
		if(RepeatedName>0) {
			result=true;
		}
			Assert.assertTrue(result);
		System.out.println(RepeatedName);
		System.out.println(result);
		}

	
	@AfterMethod
	public void close() throws InterruptedException {
		TearDownBrowserMobile();
	}
	

	
}
