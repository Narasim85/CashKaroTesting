package testCases;

import org.testng.annotations.Test;

import resources.CashKaroHomePage;
import resources.FaceBookPage;
import resources.JoinFreePage;
import resources.Utility;
import resources.SignInPage;

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

public class SignIn {

public static XSSFWorkbook wBook;
public static XSSFSheet sheet;
public static FileInputStream fin;
public static FileOutputStream fout;
public static ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<String, String>();
public static WebDriver dr;
public static String excelPath = "/Users/mastero/Desktop/Narasim/Selenium/CashKaroUAT.xlsx";
public static String chromeDriverPath = "/Users/mastero/Desktop/Narasim/Drivers/chromedriver";
public static String tempSheet = "SignIn";

@BeforeSuite
public void beforeSuite() throws IOException {
	sheet = Utility.selectExcel(excelPath, tempSheet);
}
	
@Parameters({"instance"})	
@Test
  public void joinUsFromFaceBook(String instance) {
		System.out.println("@Test");
		Utility.launchWebPage(dr, "https://cashkaro.iamsavings.co.uk/");
		
		SignInPage.clickSignIn(dr);
		SignInPage.waitUntilPageLoad(dr);
		 
		switch (Integer.parseInt(instance)) 
		{
		case 1 :
			SignInPage.enterUserName(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			SignInPage.enterPassword(dr,sheet.getRow(Integer.parseInt(instance)).getCell(4).toString());
			SignInPage.clickSubmit(dr);
			CashKaroHomePage.waitUntilHomePageAppears(dr);
			if (CashKaroHomePage.getMyAccountMenuText(dr).equals("My Account"))
				hashMap.putIfAbsent(instance, "My Account menu should appear");
			else 
				hashMap.putIfAbsent(instance, "Home page is not appear");
			
			break;
		case 2 :
			SignInPage.enterUserName(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			SignInPage.clickSubmit(dr);
			
			if (SignInPage.getErrorMessage(dr,"userName").equals("Please enter valid email"))
				hashMap.putIfAbsent(instance, "Password Error message appeared");
			else 
				hashMap.putIfAbsent(instance, "incorrect password error message");
			
			break;
			
		case 3 :
			SignInPage.enterUserName(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			SignInPage.clickSubmit(dr);
			
			if (SignInPage.getErrorMessage(dr,"password").equals("Please enter valid password"))
				hashMap.putIfAbsent(instance, "UserName Error message appeared");
			else 
				hashMap.putIfAbsent(instance, "incorrect user name error message");
			break;
		
			
		case 4 : 
			SignInPage.enterUserName(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			SignInPage.clickSubmit(dr);
			
			if ((SignInPage.getErrorMessage(dr,"userName").equals("Please enter valid email")) && (SignInPage.getErrorMessage(dr,"password").equals("Please enter valid password")))
				hashMap.putIfAbsent(instance, "Both error message are appearing");
			else 
				hashMap.putIfAbsent(instance, "incorrect user name/password error message");
			break;
			
		case 5 : 
			SignInPage.enterUserName(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			SignInPage.clickSubmit(dr);
			
			if (SignInPage.getErrorMessage(dr,"already registered").equals("Already registered error message"))
				hashMap.putIfAbsent(instance, "Already registered message appeared");
			else 
				hashMap.putIfAbsent(instance, "server side error message is not appearing for registred mail id");
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
