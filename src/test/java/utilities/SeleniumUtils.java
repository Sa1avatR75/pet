package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.*;

public class SeleniumUtils {
    public static void waitForSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e){}
    }
    public static void waitForElementTobeVisible(WebElement element, WebDriver driver, int duration ){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
