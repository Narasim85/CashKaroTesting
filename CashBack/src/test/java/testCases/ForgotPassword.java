package testCases;

import org.testng.annotations.Test;

import resources.CashKaroHomePage;
import resources.FaceBookPage;
import resources.JoinFreePage;
import resources.Utility;
import resources.ForgotPasswordPage;
import resources.LoginPage;
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

public class ForgotPassword {

public static XSSFWorkbook wBook;
public static XSSFSheet sheet;
public static FileInputStream fin;
public static FileOutputStream fout;
public static ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<String, String>();
public static WebDriver dr;
public static String excelPath = "/Users/mastero/Desktop/Narasim/Selenium/CashKaroUAT.xlsx";
public static String chromeDriverPath = "/Users/mastero/Desktop/Narasim/Drivers/chromedriver";
public static String tempSheet = "ForgotPassword";

@BeforeSuite
public void beforeSuite() throws IOException {
	sheet = Utility.selectExcel(excelPath,tempSheet);
}
	
@Parameters({"instance"})	
@Test
  public void joinUsFromFaceBook(String instance) {
		System.out.println("@Test");
		Utility.launchWebPage(dr, "https://cashkaro.iamsavings.co.uk/");
		LoginPage.clickSignIn(dr);
		SignInPage.clickOnForgotPassword(dr);
		ForgotPassword.waitUntilPageLoad(dr);
		switch (Integer.parseInt(instance)) 
		{
		case 1 :
			ForgotPasswordPage.enterEmail(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			ForgotPasswordPage.clickSubmit(dr);
			if (ForgotPasswordPage.getErrorMessage(dr,"you have got a mail").equals("You’ve got mail! Please check your inbox for your new password. You can now login and start shopping.Check your email now!"))
				hashMap.putIfAbsent(instance, "reset password sent to mail id");
			else
				hashMap.putIfAbsent(instance, "reset password is not working");
			break;	
			
		case 2 :
			ForgotPasswordPage.enterEmail(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			ForgotPasswordPage.clickSubmit(dr);
			if (ForgotPasswordPage.getErrorMessage(dr,"you have got a mail").equals("Please enter valid email address."))
				hashMap.putIfAbsent(instance, "enter valid email id error message is working");
			else
				hashMap.putIfAbsent(instance, "valid email id error message is not showing");
			break;	
			
		case 3 :
			ForgotPasswordPage.enterEmail(dr,sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			ForgotPasswordPage.clickSubmit(dr);
			if (ForgotPasswordPage.getErrorMessage(dr,"you have got a mail").equals("Please enter registerd email"))
				hashMap.putIfAbsent(instance, "registered email id error message is working");
			else
				hashMap.putIfAbsent(instance, "registered email id error message is not working");
			break;	
			
			
		}
		
		
  }
  
  private static void waitUntilPageLoad(WebDriver dr2) {
	// TODO Auto-generated method stub
	
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
