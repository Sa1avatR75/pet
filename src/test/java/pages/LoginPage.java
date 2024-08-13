package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {

    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement loginInput;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;
    @FindBy(xpath = "//input[@class='submit-button btn_action']")
    private WebElement loginBtn;


    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public void enterLogin(String login){
        loginInput.sendKeys(login);
    }

    public void enterPassword(String password){
        passwordInput.sendKeys(password);
    }
    public void clickLogin(){
        loginBtn.click();
    }
}
