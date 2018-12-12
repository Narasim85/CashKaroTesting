package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FaceBookPage {

	public static String userID = "email";
	public static String password = "pass";
	public static String loginButton = "u_0_0";
	public static String errorMessage = "//*[@id=\'error_box\']/div[1]"; 
	
	
	public static void enterUserID(WebDriver tempDriver, String userName) 
	{
		WebDriverWait wait = new WebDriverWait(tempDriver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(userID)));
		tempDriver.findElement(By.id(userID)).sendKeys(userName);
		
		
		
	}
	
	public static String getFBErrorMessage(WebDriver tempDriv) {
		
		return tempDriv.findElement(By.xpath(errorMessage)).getText();
	}
	
	public static void enterPasswordID(WebDriver tempDriver, String pass) 
	{
		tempDriver.findElement(By.id(password)).sendKeys(pass);
	
	}
	
	
	public static void clickLoginButton(WebDriver tempDriver) 
	{
		tempDriver.findElement(By.id(loginButton)).click();
	
	}
	
	
}
