package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CashKaroHomePage {
	public static String myAccount = "link_myaccount";
	public static String logOut = "idMenuLogout";
	public static String text;
	
	public static void waitUntilHomePageAppears(WebDriver tempDriv) {
		WebDriverWait wait = new WebDriverWait(tempDriv,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(myAccount)));
		
	}
	public static String getMyAccountMenuText(WebDriver tempDriv) {
		
		return tempDriv.findElement(By.id(myAccount)).getText();
		
	}
	
	public static void clickMyAccountMenu(WebDriver tempDriv) {
		WebDriverWait wait = new WebDriverWait(tempDriv,30);
		tempDriv.findElement(By.id(myAccount)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id(logOut)));
	}
	
	public static void clickOnLogout(WebDriver tempDriv) {
		tempDriv.findElement(By.id(logOut)).click();
	}
}
