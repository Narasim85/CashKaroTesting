package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.JoinFreePage;

public class Utility {
	public static XSSFWorkbook wBook;
	public static XSSFSheet sheet;
	public static FileInputStream fin;
	public static FileOutputStream fout;
	public static XSSFCell cell;
	public static XSSFRow row;
	
	public static String winHandleBefore;

	
	public static XSSFSheet selectExcel(String path, String tempSheet) throws IOException {
	
		fin = new FileInputStream(new File(path));
		wBook = new XSSFWorkbook(fin);
		sheet = wBook.getSheet(tempSheet);

		return sheet;
	}
	
	public static WebDriver initiateDriver(WebDriver tempDriv, String path) {
		System.setProperty("webdriver.chrome.driver", path);
		tempDriv = new ChromeDriver();

		return tempDriv;
		
	}
	
	public static void launchWebPage(WebDriver tempDriv, String url) {
		tempDriv.get(url);
		
		WebDriverWait wait = new WebDriverWait(tempDriv,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(JoinFreePage.joinFree)));
	}
	
	public static void switchToNewWindow(WebDriver tempDriv) {
		winHandleBefore = tempDriv.getWindowHandle();
		for(String winHandle : tempDriv.getWindowHandles()){
			tempDriv.switchTo().window(winHandle);
		}
		
	}
	
	public static void closeCurrentWindow(WebDriver tempDriv) {
		tempDriv.close();
	}
	
	public static void switchBackToMainWindow(WebDriver tempDriv) {
		tempDriv.switchTo().window(winHandleBefore);
	}
	
	public static void updateActualResult(String path, XSSFSheet sheet, ConcurrentHashMap<String, String> hashMap) throws IOException {
		for(int i=1;i<sheet.getLastRowNum();i++) {
			fout = new FileOutputStream(path);
			cell = sheet.getRow(i).getCell(6);
			if(cell==null)
				cell = sheet.getRow(i).createCell(6);
			
			
			Iterator<String> keyIterator = hashMap.keySet().iterator();
			while(keyIterator.hasNext()) {
				String key = keyIterator.next();
				if(key.equals(sheet.getRow(i).getCell(0).toString())) {
					cell.setCellValue(hashMap.get(key).toString());
				}
				
			}
			wBook.write(fout);
			fout.flush();
			fout.close();
		}
		
	}

	public static void populateTestResults(XSSFSheet sheet, String path) throws IOException {
		
		int temp = sheet.getLastRowNum();
		
		for(int i=1;i<sheet.getLastRowNum();i++) {
			fout = new FileOutputStream(path);
			cell = sheet.getRow(i).getCell(7);
			if (cell == null)
				cell = sheet.getRow(i).createCell(7);
			if ((sheet.getRow(i).getCell(6).toString().equals(null)) || (sheet.getRow(i).getCell(6).toString().equals("")))
				cell.setCellValue("SKIPPED");
			else if(sheet.getRow(i).getCell(5).toString().equals(sheet.getRow(i).getCell(6).toString()))
				cell.setCellValue("PASS");
			else  
				cell.setCellValue("FAIL");
			wBook.write(fout);
			fout.flush();
			fout.close();
		}
		
	}
	
	
	
}
