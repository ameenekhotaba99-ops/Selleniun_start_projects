package Selleniun_start_projects.Selleniun_start_projects;

	import static org.junit.Assert.assertEquals;

	import java.util.List;
	import java.util.concurrent.TimeUnit;

	import org.junit.After;
	import org.junit.Before;
	import org.junit.Test;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.firefox.FirefoxDriver;

	public class CalculatorTests {

	    static WebDriver browser;

	    @Before
	    public void setup() {

	    	System.setProperty("webdriver.gecko.driver",
	    	        "C:\\Users\\HP\\Desktop\\geckodriver-v0.36.0-win64\\geckodriver.exe");



	        browser = new FirefoxDriver();
	        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	        browser.get("https://testpages.eviltester.com/apps/button-calculator/");
	    }

	    @After
	    public void tearDown() {
	        if (browser != null) {
	            browser.quit();
	        }
	    }

	    // ======= קריאת הערך מהמסך =======
	    private String readCalculatorDisplayValue() {
	        List<WebElement> inputs = browser.findElements(By.cssSelector("input"));
	        for (WebElement in : inputs) {
	            String type = in.getAttribute("type");
	            if (type == null) type = "";
	            type = type.trim().toLowerCase();

	            // המסך הוא input שמוצג למשתמש
	            if ((type.equals("text") || type.equals("tel") || type.equals("number")) && in.isDisplayed()) {
	                return in.getAttribute("value");
	            }
	        }
	        // fallback אם המבנה השתנה
	        WebElement fallback = browser.findElement(
	                By.cssSelector("input[id*='display'], input[class*='display']"));
	        return fallback.getAttribute("value");
	    }

	    // ======= לחיצה יציבה לפי טקסט הכפתור =======
	    private void press(String buttonText) {
	        browser.findElement(By.xpath("//button[normalize-space(.)='" + buttonText + "']")).click();
	    }

	    // ----------- PLUS (2 tests) -----------
	    @Test
	    public void Test_Add_1() {
	        press("AC");
	        press("1");
	        press("2");
	        press("+");
	        press("3");
	        press("5");
	        press("=");

	        String result = readCalculatorDisplayValue();
	        assertEquals("47", result);
	    }

	    @Test
	    public void Test_Add_2() {
	        press("AC");
	        press("9");
	        press("+");
	        press("7");
	        press("=");

	        String result = readCalculatorDisplayValue();
	        assertEquals("16", result);
	    }

	    // ----------- MINUS (2 tests) -----------
	    @Test
	    public void Test_Sub_1() {
	        press("AC");
	        press("2");
	        press("0");
	        press("-");
	        press("7");
	        press("=");

	        String result = readCalculatorDisplayValue();
	        assertEquals("13", result);
	    }

	    @Test
	    public void Test_Sub_2() {
	        press("AC");
	        press("9");
	        press("-");
	        press("8");
	        press("=");

	        String result = readCalculatorDisplayValue();
	        assertEquals("1", result);
	    }

	    // ----------- MULTIPLY (2 tests) -----------
	    @Test
	    public void Test_Mul_1() {
	        press("AC");
	        press("6");
	        press("*");
	        press("7");
	        press("=");

	        String result = readCalculatorDisplayValue();
	        assertEquals("42", result);
	    }

	    @Test
	    public void Test_Mul_2() {
	        press("AC");
	        press("9");
	        press("*");
	        press("9");
	        press("=");

	        String result = readCalculatorDisplayValue();
	        assertEquals("81", result);
	    }
	}



