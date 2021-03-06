package com.willowTree.nameGame;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.willowTree.nameGame.UI.GamePage;

public class GameCounters extends TestBase {
	/**
	 * @author Nauman SH.
	 * @For WillowTree
	 */
	GamePage gamepage;

	@BeforeMethod
	public void start() throws IOException {
		InitializeDesktop("LocalDriver","Chrome");
	}
	
	@Test(priority=1,enabled=true)
	public void Tc002_VerifyTriesCounterIncrements() throws InterruptedException {
		/**
		 * Test method verifies that Attempts Counter s incrementing correctly.
		 */
		gamepage=new GamePage(driver);
		int TotalClick=gamepage.GameTriesCount(5);
		int attemptsAfterGame = gamepage.NoOfAttempts();
		Assert.assertEquals(attemptsAfterGame ,TotalClick, "Assert is successfull where in "+TotalClick+ " physical clicks were performed and counter is " +attemptsAfterGame );
	}
	
	@Test(priority=2,enabled=true)
	public void Tc003_VerifyCorrectCounterIncrements() throws InterruptedException {
		/**
		 * Test method verifies that Counter for correct answers is incrementing correctly.
		 */
		gamepage=new GamePage(driver);
		int SuccessClicks=gamepage.GameCorrectCounterCheck(5);
		int CorrectCounterAfterGame = gamepage.NoOfCorrectAttempts();
		Assert.assertEquals(CorrectCounterAfterGame,SuccessClicks, "Assert is successfull where in "+SuccessClicks+" physical clicks were performed and counter is " +CorrectCounterAfterGame);
	}
	@Test(priority=3,enabled=true)
	public void Tc004_VerifyStreakIncrements() throws InterruptedException {
		/**
		 * Test method verifies that streak is getting reset and incrementing correctly.
		 * Hence it cover scope of two Test cases (	counter increment/Multiple Rests)
		 */
		gamepage=new GamePage(driver);
		int StreakCount=gamepage.GameStreakCounterCheck(5);
		int StreakCountFromGame= gamepage.NoOfCorrectStreaks();
		Assert.assertEquals(StreakCountFromGame,StreakCount, "Assert is successfull as " +StreakCount);
	}
	
	@Test(priority=4,enabled=true)
	public void Tc005_Verify_Tries_Correct_CountersTogether() throws InterruptedException {
		/**
		 * Test verifies that Correct & Tries counter are getting incrementing correctly for 10 values.
		 */
		gamepage=new GamePage(driver);
		int[] Tries_Correct_Counter = gamepage.GameTries_CorrectCount(10);
		int[] Game_Counter_Results=new int[2];
		Game_Counter_Results[0]= gamepage.NoOfCorrectAttempts();
		Game_Counter_Results[1] = gamepage.NoOfAttempts();
		Assert.assertEquals(Game_Counter_Results,Tries_Correct_Counter,"Incorrect counters");
		
			}
	

	@AfterMethod
	public void close() throws InterruptedException {
		TearDownBrowser();
	}
}
