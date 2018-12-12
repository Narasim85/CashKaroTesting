package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JoinFreePage {

	public static String joinFree = "link_join";
	public static String joinWithFB = "close_and_go_fb";
	
	
	public static void clickJoinFree(WebDriver tempDriv)
	{	
		WebDriverWait wait = new WebDriverWait(tempDriv,30);
		tempDriv.findElement(By.id(joinFree)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id(joinWithFB)));
		tempDriv.findElement(By.id(joinWithFB)).click();
		
	}
	
	public static void clickJoinWithFB(WebDriver tempDriv) {
		tempDriv.findElement(By.id(joinWithFB)).click();
		tempDriv.findElement(By.id(joinWithFB)).click();
	}

	public static void enterName(WebDriver dr, String string) {
		// TODO Auto-generated method stub
		
	}

	public static void enterEmail(WebDriver dr, String string) {
		// TODO Auto-generated method stub
		
	}

	public static void enterPassword(WebDriver dr, String string) {
		// TODO Auto-generated method stub
		
	}

	public static void enterMobile(WebDriver dr, String string) {
		// TODO Auto-generated method stub
		
	}

	public static void waitUntilCaptchFieldPopulate(WebDriver dr) {
		// TODO Auto-generated method stub
		
	}

	public static void clickOnSubmit(WebDriver dr) {
		// TODO Auto-generated method stub
		
	}

	public static void verifyErrorMessage(WebDriver dr, String string) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
}
