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

public class JoinUsFromNormalFlow {

public static XSSFWorkbook wBook;
public static XSSFSheet sheet;
public static FileInputStream fin;
public static FileOutputStream fout;
public static ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<String, String>();
public static WebDriver dr;
public static String excelPath = "/Users/mastero/Desktop/Narasim/Selenium/CashKaroUAT.xlsx";
public static String chromeDriverPath = "/Users/mastero/Desktop/Narasim/Drivers/chromedriver";
public static String tempSheet = "JoinUsFromNormalFlow";

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
		
		 
		switch (Integer.parseInt(instance)) 
		{
		case 1 :
			JoinFreePage.enterName(dr, sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			JoinFreePage.enterEmail(dr, sheet.getRow(Integer.parseInt(instance)).getCell(4).toString());
			JoinFreePage.enterPassword(dr, sheet.getRow(Integer.parseInt(instance)).getCell(5).toString());
			JoinFreePage.enterMobile(dr, sheet.getRow(Integer.parseInt(instance)).getCell(6).toString());
			JoinFreePage.waitUntilCaptchFieldPopulate(dr);
			JoinFreePage.clickOnSubmit(dr);
			if (CashKaroHomePage.getMyAccountMenuText(dr).equals("My Account"))
				hashMap.putIfAbsent(instance, "successfully sign up message should appear");
			else 
				hashMap.putIfAbsent(instance, "Signup was not successfull");
			break;
		
		case 2 :
			JoinFreePage.enterName(dr, sheet.getRow(Integer.parseInt(instance)).getCell(3).toString());
			JoinFreePage.enterEmail(dr, sheet.getRow(Integer.parseInt(instance)).getCell(4).toString());
			JoinFreePage.enterPassword(dr, sheet.getRow(Integer.parseInt(instance)).getCell(5).toString());
			JoinFreePage.enterMobile(dr, sheet.getRow(Integer.parseInt(instance)).getCell(6).toString());
			JoinFreePage.waitUntilCaptchFieldPopulate(dr);
			JoinFreePage.clickOnSubmit(dr);
			JoinFreePage.verifyErrorMessage(dr, "Email already exist");
			
			hashMap.putIfAbsent(instance, "Email already registered error message should appear");
			
			break;
		
		case 3 :
			JoinFreePage.clickOnSubmit(dr);
			JoinFreePage.verifyErrorMessage(dr, "Please type your name");
			JoinFreePage.verifyErrorMessage(dr, "Please enter a valid email");
			JoinFreePage.verifyErrorMessage(dr, "Please choose password");
			JoinFreePage.verifyErrorMessage(dr, "Please enter valid mobile number");
			JoinFreePage.verifyErrorMessage(dr, "Please enter captch");
			hashMap.putIfAbsent(instance, "Validate each field error message");
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
