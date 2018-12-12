package testCases;

import org.testng.annotations.Test;

import resources.CashKaroHomePage;
import resources.FaceBookPage;
import resources.JoinFreePage;
import resources.Utility;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

public class JoinUsFromFB {

public static XSSFWorkbook wBook;
public static XSSFSheet sheet;
public static FileInputStream fin;
public static FileOutputStream fout;
public static ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<String, String>();
public static WebDriver dr;
public static String excelPath = "/Users/mastero/Desktop/Narasim/Selenium/CashKaroUAT.xlsx";
public static String chromeDriverPath = "/Users/mastero/Desktop/Narasim/Drivers/chromedriver";
public static String tempSheet = "JoinUsFromFB";

@BeforeSuite
public void beforeSuite() throws IOException {
	sheet = Utility.selectExcel(excelPath, tempSheet);
}
	
@Parameters({"instance"})	
@Test
  public void joinUsFromFaceBook(String instance) {
		System.out.println("@Test");
		Utility.launchWebPage(dr, "https://cashkaro.iamsavings.co.uk/");
		JoinFreePage.clickJoinFree(dr);
		JoinFreePage.clickJoinWithFB(dr);
		Utility.switchToNewWindow(dr);
		 
		switch (Integer.parseInt(instance)) 
		{
		case 1 :
		case 2 :
			FaceBookPage.enterUserID(dr, sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			FaceBookPage.enterPasswordID(dr, sheet.getRow(Integer.parseInt(instance)).getCell(4).toString());
			FaceBookPage.clickLoginButton(dr);
			Utility.switchBackToMainWindow(dr);
			CashKaroHomePage.waitUntilHomePageAppears(dr);
			if (CashKaroHomePage.getMyAccountMenuText(dr).equals("My Account"))
				hashMap.putIfAbsent(instance, "My Account menu should appear");
			else 
				hashMap.putIfAbsent(instance, "In correct text appeared");
			
			CashKaroHomePage.clickMyAccountMenu(dr);
			CashKaroHomePage.clickOnLogout(dr);
			break;
		
		case 3 :
			FaceBookPage.clickLoginButton(dr);
			if(FaceBookPage.getFBErrorMessage(dr).equals("Incorrect email address or phone number"))
				hashMap.putIfAbsent(instance, "Incorrect email address or phone number  error message");
			else 
				hashMap.putIfAbsent(instance, "In correct text appeared");
			break;
		
			
		case 4 : 
			FaceBookPage.enterUserID(dr, sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			FaceBookPage.clickLoginButton(dr);
			if(FaceBookPage.getFBErrorMessage(dr).equals("Please re-enter your password"))
				hashMap.putIfAbsent(instance, "Please re-enter your password");
			else 
				hashMap.putIfAbsent(instance, "In correct text appeared");
			break;
		}
		
		
  }
  
  @BeforeTest
  public void beforeTest() {
		dr = Utility.initiateDriver(dr, chromeDriverPath);

  }

  @AfterTest
  public void afterTest() {
	  dr.quit();
  }

  

  @AfterSuite
  public void afterSuite() throws IOException {
	  Utility.updateActualResult(excelPath, sheet, hashMap);
	  Utility.populateTestResults(sheet, excelPath);
	    
	  
  }

}
