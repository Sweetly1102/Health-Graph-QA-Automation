package gitProject.Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNg {


		@Test
		public static void main1(String[] args) throws IOException {
			
			Properties prop = new Properties();
			FileInputStream input = new FileInputStream("TestData.properties");
			prop.load(input);
			
			System.setProperty("webdriver.chrome.driver","/Users/sweetlypriya/Documents/Selenium/Selenium ChromeDriver/chromedriver");
			WebDriver driver = new ChromeDriver();
			
			driver.get(prop.getProperty("website.url"));
			
			driver.findElement(By.xpath("//font[contains(text(),'Sign In')]")).click();
			
			// try login with incorrect credentials
	        driver.findElement(By.name("uid")).sendKeys(prop.getProperty("incorrect.username"));
	        driver.findElement(By.name("passw")).sendKeys(prop.getProperty("incorrect.password"));
	        driver.findElement(By.name("btnSubmit")).click();
	        System.out.println(driver.findElement(By.xpath("//span[@id='_ctl0__ctl0_Content_Main_message']")));
	        
	        // retry login with correct credentials
	        driver.findElement(By.name("uid")).sendKeys(prop.getProperty("correct.username"));
	        driver.findElement(By.name("passw")).sendKeys(prop.getProperty("correct.password"));
	        driver.findElement(By.name("btnSubmit")).click();
	        driver.switchTo().alert().accept();
	        
	        //Validate that you are signed in after trying with correct credentials
	        String welcomeText = driver.findElement(By.xpath("//p[contains(text(),'Welcome to Altoro Mutual Online.')]")).getText();
	        System.out.println(welcomeText);
	        
	        //Click on the View Account Summary option in the left pane
	        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/ul[1]/li[1]")).click();
	        
	        //Select the 800001 Checking account option and click on the Go button
	        Select accountDropdown = new Select(driver.findElement(By.name("accountno")));
	        accountDropdown.selectByValue("800001");
	        driver.findElement(By.id("btnGetAccount")).click();
	        
	        //Assert the Available Balance in the account
	        String balance = driver.findElement(By.xpath("//td[contains(text(),'Available Balance')]/following-sibling::td")).getText();
	        Assert.assertTrue(!balance.equals("$0"));
	        
	        //Click on the Transfer Funds option in the Left Pane
	        driver.findElement(By.id("MenuHyperLink3")).click();
	        
	        //Enter the following data & click on the Transfer Funds button
	        Select fromAccountDropdown = new Select(driver.findElement(By.id("fromAccount")));
	        fromAccountDropdown.selectByValue("800000");
	        Select toAccountDropdown = new Select(driver.findElement(By.id("toAccount")));
	        toAccountDropdown.selectByValue("800001");
	        driver.findElement(By.id("transferAmount")).sendKeys("9876");
	        driver.findElement(By.id("transfer")).click();
	        
	        //Validate the Transaction Details message is shown below with correct amount
	        String transactionDetails = driver.findElement(By.cssSelector("td.bb div.fl:nth-child(2) tbody:nth-child(1) tr:nth-child(6) td:nth-child(1) span:nth-child(1) > span:nth-child(1)")).getText();
	        Assert.assertTrue(transactionDetails.contains("9876"));
	        
	        //Click on the View Recent Transactions option in the left pane
	        driver.findElement(By.id("MenuHyperLink2")).click();

	        //Validate that the first two transactions being shown is as per the previous transaction
	        List<WebElement> transactions = driver.findElements(By.xpath("//table[@id='_ctl0__ctl0_Content_Main_MyTransactions']//tr"));
	        Assert.assertTrue(transactions.size() >= 2);
	        String firstTransaction = transactions.get(1).getText();
	        Assert.assertTrue(firstTransaction.contains("9876"));

	        //Click on Contact Us on Top Right
	        driver.findElement(By.id("HyperLink3")).click();

	        //Enter some details here and click on Submit button. Validate that on clicking Submit, a Thank You message is displayed
	        driver.findElement(By.xpath("//a[contains(text(),'online form')]")).click();
	        driver.findElement(By.name("name")).sendKeys("Test User");
	        driver.findElement(By.name("email_addr")).sendKeys("testuser@example.com");
	        driver.findElement(By.name("subject")).sendKeys("Test Subject");
	        driver.findElement(By.name("comments")).sendKeys("Test Message");
	        driver.findElement(By.name("submit")).click();
	        String thankYouMessage = driver.findElement(By.xpath("//p[contains(text(),'Thank you')]")).getText();
	        Assert.assertTrue(thankYouMessage.contains("Thank you"));
	        
	        //Click on the Sign Off button on top right. Validate that the user is Signed Off.
	        driver.findElement(By.xpath("//a[contains(text(),'Sign Off')]")).click();
	        String loginText = driver.findElement(By.xpath("//td[contains(text(),'Welcome')]")).getText();
	        Assert.assertTrue(!loginText.contains(prop.getProperty("correct.username")));

	        
	        
		
	}

}
