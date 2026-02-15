package Selleniun_start_projects.Selleniun_start_projects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class PasswordFieldTests {

    static WebDriver browser;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver",
                "C:\\Users\\HP\\Desktop\\geckodriver-v0.36.0-win64\\geckodriver.exe");

        browser = new FirefoxDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        browser.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        browser.get("https://testpages.eviltester.com/apps/7-char-val/");
        System.out.println("דף נטען: " + browser.getTitle());
    }

    @After
    public void tearDown() {
        if (browser != null) {
            browser.quit();
        }
    }

    private String enterPasswordAndValidate(String password) throws InterruptedException {
        // נקה וממלא את שדה הסיסמה
        WebElement input = browser.findElement(By.name("characters"));
        input.clear();
        input.sendKeys(password);
        System.out.println("הוזן: " + password);

        // לחץ על כפתור Validate
        WebElement validateButton;
        try {
            validateButton = browser.findElement(By.name("validate"));
            System.out.println("נמצא כפתור עם name='validate'");
        } catch (Exception e) {
            System.out.println("לא נמצא name='validate' – מנסה xpath...");
            validateButton = browser.findElement(By.xpath("//button[contains(.,'Validate') or contains(.,'Check')] | //input[@value='Validate' or contains(@value,'Validate')]"));
        }
        validateButton.click();
        System.out.println("לחץ על Validate");

        // חכה קצת לעדכון
        Thread.sleep(1000);

        // קרא את ההודעה מהשדה readonly
        WebElement messageElement = browser.findElement(By.name("validation_message"));
        String message = messageElement.getAttribute("value").trim();
        System.out.println("הודעה שהתקבלה: [" + message + "]");

        return message;
    }

    // תקין - בדיוק 7 תווים מותרים
    @Test
    public void valid_exactly7_mixedAllowed() throws Exception {
        String result = enterPasswordAndValidate("Ameen*9");
        assertEquals("Valid Value", result);
    }

   
    

    // לא תקין - קצר מדי
    @Test
    public void invalid_tooShort() throws Exception {
        String result = enterPasswordAndValidate("Amee");
        assertEquals("Invalid Value", result);
    }

    // לא תקין - ארוך מדי (10 תווים כדי להיות בטוח)
    @Test
    public void invalid_tooLong() throws Exception {
        String result = enterPasswordAndValidate("Ameen12345");
        assertEquals("Invalid Value", result);
    }

    // לא תקין - מכיל תו אסור
    @Test
    public void invalid_illegalCharacter() throws Exception {
        String result = enterPasswordAndValidate("Ameen@9");
        assertEquals("Invalid Value", result);
    }

   
}