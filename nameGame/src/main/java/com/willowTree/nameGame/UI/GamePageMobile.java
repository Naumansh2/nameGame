package com.willowTree.nameGame.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.willowTree.nameGame.TestBase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class GamePageMobile{
	/**
	 * @author Nauman SH.
	 * @For WillowTree
	 */
	AppiumDriver<WebElement> Adriver;
	TestBase test;
	public GamePageMobile(AppiumDriver<WebElement> Adriver){
		this.Adriver=Adriver;
		PageFactory.initElements(new AppiumFieldDecorator(Adriver),this);
		test=new TestBase();
	
	}
	By galleryLocator = By.xpath(".//div[@class='container']/div[@id='gallery']/div/div[@class='photo']");
	@FindBy(className="attempts")
	WebElement attempts;
	@FindBy(className="correct")
	WebElement correct;
	@FindBy(className="streak")
	WebElement streaks;
	@FindBy(id="name")
	WebElement name;
	@FindBy(xpath=".//div[@class='container']/div[@id='gallery']/div/div[@class='photo']/div[@class='name']")
	List<WebElement> empName;
	
	public int NoOfAttempts() {
		String attemptsCount = attempts.getText();
		int attemptcount = Integer.parseInt(attemptsCount);
		return attemptcount;
	}
	
	public int NoOfCorrectAttempts() {
		String correctCount = correct.getText();
		 int correctcount = Integer.parseInt(correctCount) ;
		 return correctcount;
	}
	
	public int NoOfCorrectStreaks() {
		String streaksCount = streaks.getText();
		 int streakcount = Integer.parseInt(streaksCount) ;
		 return streakcount;
	}
	
	public int GameTriesCount(int NoOftimes) throws InterruptedException {
		
		int manualClickCount=0;
		for(int i=0;i<NoOftimes;i++) {
			for(WebElement gal:GalleryList()) {
				test.waitOnlyMobile(gal,Adriver).click();
				manualClickCount++;
				if(gal.getAttribute("class").contains("photo correct")) {
					Thread.sleep(4000);
					break;
				}
			}
		}
		return manualClickCount;
	}

	public int GameCorrectCounterCheck(int NoOftimes) throws InterruptedException {
		int manualCorrectCheck=0;
		for(int i=0;i<NoOftimes;i++) {
			for(WebElement gal:GalleryList()) {
				test.waitOnlyMobile(gal,Adriver).click();
				if(gal.getAttribute("class").contains("photo correct")) {
					Thread.sleep(4000);
					manualCorrectCheck++;
					break;
				}
			}
		}
		return manualCorrectCheck++;
	}
	
	public int GameStreakCounterCheck(int NoOftimes) throws InterruptedException {
		int manualStreakCheck=0;
		for(int i=0;i<NoOftimes;i++) {
			for(WebElement gal:GalleryList()) {
				test.waitOnlyMobile(gal,Adriver).click();
				if(gal.getAttribute("class").contains("photo correct")) {
					Thread.sleep(4000);
					manualStreakCheck++;
					break;
				}else if(gal.getAttribute("class").contains("photo wrong")) {
					manualStreakCheck=0;
				}
			}
		}
		return manualStreakCheck++;
	}
	
	public int[] GameTries_CorrectCount(int NoOftimes) throws InterruptedException {
		int manualCorrectCheck=0;
		int manualTriesCheck=0;
		int[] counterCheck=new int[2];
		for(int i=0;i<NoOftimes;i++) {
			for(WebElement gal:GalleryList()) {
				test.waitOnlyMobile(gal,Adriver).click();
				manualTriesCheck++;
				if(gal.getAttribute("class").contains("photo correct")) {
					Thread.sleep(4000);
					manualCorrectCheck++;
					break;
				}
			}
		}
		counterCheck[0]=manualCorrectCheck;
		counterCheck[1]=manualTriesCheck;
		return counterCheck;
	}
	
	public Map<String, String> Game_Nmaes_Photos_Change() throws InterruptedException {
		
		Map<String,String> Name_Photos=new HashMap<String, String>();
		for(int i=0;i<2;i++) {
			GalleryList();
			Name_Photos.put("screen"+i,name.getText());
			for(int j=0;j<GalleryList().size();j++) {
				Name_Photos.put("screen"+i+j, empName.get(j).getText());
			}
			for(WebElement gal:GalleryList()) {
				test.waitOnlyMobile(gal,Adriver).click();
				if(gal.getAttribute("class").contains("photo correct")) {
					Thread.sleep(3000);
					break;
				}
			}
		}
		return Name_Photos;
	}
	
	public List<String> Game_IncorrectSelection_Repetition(int NoOfTimesGameToBePlayed) throws InterruptedException {
		int k=0;
		List<String> Name_Fail=new LinkedList<String>();
		for(int i=0;i<NoOfTimesGameToBePlayed;i++) {
			for(WebElement gal:GalleryList()) {
				test.waitOnlyMobile(gal,Adriver).click();
				if(gal.getAttribute("class").contains("photo wrong")) {
					Thread.sleep(3000);
					Name_Fail.add(gal.findElement(By.className("name")).getText());		
				}
				k++;
				}
		}
		return Name_Fail;
	}
	
	public List<WebElement> GalleryList() {
		List<WebElement> galleryList = test.waitforListMobile(galleryLocator,Adriver);
		return galleryList;
	}
}
