package ui;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

public class MultiBrowser {
	public WebDriver dr;
	public static String url = "https://oltxqa1.fmr.com/ftgw/fbc/oftop/portfolio#summary";
	public static String chromeDriverPath = "C://selenium//chromedriver.exe";
	public static String fireFoxDriverPath = "";

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
	public static String excelPath = "C://Users//a573942//Documents//workspace-sts-3.9.2.RELEASE//selenium//src//resources//ParallelExecution.xlsx";
	public static String sheetName = "BrowserTestData";

	public static ConcurrentHashMap<String, String> excelOutPut = new ConcurrentHashMap<String, String>();
	public static Map<String, HashMap<String, String>> excelRecords = new HashMap<String, HashMap<String, String>>();

	@BeforeSuite
	public void beforeSuite() throws IOException {

		fin = new FileInputStream(excelPath);
		wBook = new XSSFWorkbook(fin);
		sheet = wBook.getSheet(sheetName);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			HashMap<String, String> singleRow = new HashMap<String, String>();
			for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
				if (sheet.getRow(i).getCell(j).toString() != null) {
					singleRow.putIfAbsent(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i).getCell(j).toString());
				} else {
					singleRow.put(sheet.getRow(0).getCell(j).toString(), "Empty");
				}
			}
			excelRecords.putIfAbsent(sheet.getRow(i).getCell(0).toString(), singleRow);
		}

	}

	@Parameters({ "browser", "instance" })
	@BeforeTest
	public void beforeTest(@Optional("") String browser, @Optional("") String driverPath) throws Exception {

		browser = browser.toLowerCase();
		if (!driverPath.equals("")) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		}
		if (browser.equals("chrome")) {
			dr = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			dr = new FirefoxDriver();
		} else {
			throw new RuntimeException("Please create a driver for " + browser);
		}

	}

	@Parameters({ "instance" })
	@Test
	public void loginFunctionality(String instance) {
		dr.get(url);
		WebDriverWait wait = new WebDriverWait(dr, 30);
		if (getCellValue(instance, "Execution Flag").equalsIgnoreCase("Y")) {
			switch (instance) {
			case "1":
				dr.findElement(By.id("userId-input")).sendKeys(getCellValue(instance, "UserName"));
				dr.findElement(By.id("password")).sendKeys(getCellValue(instance, "Password"));
				dr.findElement(By.id("fs-login-button")).click();
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"pgnb\"]/div[1]/div[3]/ul[2]/li[4]/a")));
				excelOutPut.putIfAbsent(instance,
						dr.findElement(By.xpath("//*[@id=\"pgnb\"]/div[1]/div[3]/ul[2]/li[4]/a")).getText());
				break;

			case "2":
				dr.findElement(By.id("password")).sendKeys(getCellValue(instance, "Password"));
				dr.findElement(By.id("fs-login-button")).click();
				excelOutPut.putIfAbsent(instance,
						dr.findElement(By.xpath("//*[@id=\"user-error-msg\"]/label")).getText());
				break;

			case "3":
				dr.findElement(By.id("userId-input")).sendKeys(getCellValue(instance, "UserName"));
				dr.findElement(By.id("fs-login-button")).click();
				excelOutPut.putIfAbsent(instance,
						dr.findElement(By.xpath("//*[@id=\"password-error-msg\"]/label")).getText() + " temp");
				break;
			}

		} else
			excelOutPut.putIfAbsent(instance, "Not Executed");

		/*
		 * Iterator<String> keyIterator = excelOutPut.keySet().iterator(); while
		 * (keyIterator.hasNext()) { String key = keyIterator.next();
		 * System.out.println("Row ID: " + key + " Execution results: " +
		 * excelOutPut.get(key)); }
		 */
	}

	public String getCellValue(String rowNum, String fieldName) {
		String valueTemp = null;
		boolean flag = false;
		for (Map.Entry<String, HashMap<String, String>> mapRowNum : excelRecords.entrySet()) {
			String key = mapRowNum.getKey();
			if (key.equals(rowNum)) {
				HashMap<String, String> values = mapRowNum.getValue();
				for (Map.Entry<String, String> innerMap : values.entrySet()) {
					String headerName = innerMap.getKey();
					if (headerName.equals(fieldName)) {
						valueTemp = innerMap.getValue();
						flag = true;
						break;
					}
				}
			} else
				valueTemp = "Given row and field name is incorrect";
			if (flag == true)
				break;
		}
		return valueTemp;
	}

	@AfterTest
	public void afterTest() {
		dr.quit();
	}

	@AfterSuite
	public void afterSuite() throws IOException {
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			fout = new FileOutputStream(excelPath);
			String index = sheet.getRow(i).getCell(0).toString();
			Iterator<String> keyIterator = excelOutPut.keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				if (key.equals(index)) {
					cell = sheet.getRow(i).getCell(6);
					cell.setCellValue(excelOutPut.get(key));
					if (sheet.getRow(i).getCell(5).toString().equalsIgnoreCase(sheet.getRow(i).getCell(6).toString())) {
						cell = sheet.getRow(i).getCell(7);
						cell.setCellValue("PASS");
					}
					else if (sheet.getRow(i).getCell(6).toString().equalsIgnoreCase("Not Executed")) {
						cell = sheet.getRow(i).getCell(7);
						cell.setCellValue("SKIPPED");		
					}
					else {
						cell = sheet.getRow(i).getCell(7);
						cell.setCellValue("FAIL");
					}
				}
			}
			wBook.write(fout);
			fout.flush();
			fout.close();
		}
	}
}
