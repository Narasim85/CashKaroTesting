package poc;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class Parallel {
	private WebDriver dr;	
    public static String userName;
    public static String password;
    public static String expResult;
    public static String actResult;
    public static String exeFlag;
    
	private static XSSFWorkbook wBook;
    private static XSSFSheet sheet;
    private static XSSFCell cell;

    private static FileInputStream fin;
    private static FileOutputStream fout;
    public static ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<String,String>(); 
  
  @BeforeSuite
  public void excelRead() throws Exception {
	  try {
          
	    	fin = new FileInputStream("C://Users//a573942//Documents//FBT//excel//temp2.xlsx");
	    	wBook = new XSSFWorkbook(fin);
	    	sheet = wBook.getSheet("Sheet1");
      } catch (Exception e) {
          try {
              throw (e);
          } catch (IOException e1) {
              e1.printStackTrace();
          }
      }
  }

  
  
  @BeforeTest
  public void iniateDriver() {
	  System.setProperty("webdriver.chrome.driver", "c://selenium//ChromeDriver.exe");
		dr = new ChromeDriver();
  }
  

  @Parameters({"userName","password","sequence"})
  @Test
  public void parallelExecution(String userName, String password, String sequence) {
		dr.get("https://loginqa1.fmr.com/ftgw/Fidelity/RtlCust/Login/Init");
		dr.manage().window().maximize();
		dr.findElement(By.id("userId-input")).sendKeys(userName);
		dr.findElement(By.id("password")).sendKeys(password);
		dr.findElement(By.id("fs-login-button")).click();
		WebDriverWait wait = new WebDriverWait(dr,60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pgnb\"]/div[1]/div[3]/ul[2]/li[4]/a")));
		dr.findElement(By.xpath("//*[@id='pgnb']/div[1]/div[3]/ul[2]/li[4]/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Link_1421177011304\"]")));
		hashMap.putIfAbsent(sequence, "value");
		
  }
  
@Parameters({"browser", "driverPath", "movieName","mode" })	
  @AfterTest
  public void afterTest() {  
	  dr.quit();
  }

@Parameters({"userName","password","sequence"})
@AfterSuite
public void afterSuite() {
	try {  
   	 for (int i=1;i<=sheet.getLastRowNum();i++) 
   	 {	 
   	      	fout = new FileOutputStream("C://Users//a573942//Documents//FBT//excel//temp2.xlsx");
   	        expResult = sheet.getRow(i).getCell(6).toString();
   	        
   	        Iterator<String> keyIterator = hashMap.keySet().iterator();
   	        while(keyIterator.hasNext()) {
   	            String key = keyIterator.next();
   	            if((key.equals(expResult)) && (expResult != null) && (!expResult.isEmpty()))
   	            {
   	            	cell = sheet.getRow(i).getCell(4);
   	            	cell.setCellValue(hashMap.get(key));	
   	            }
   	        }
   	  
   	        wBook.write(fout);
   	        fout.flush();
   			fout.close();
   	 }
   	      } catch (Exception e) {
   	          try {
   	              throw (e);
   	          } catch (IOException e1) {
   	              e1.printStackTrace();
   	          }
   	      }
}

}



	