package itsm_pharmacy_base_files.app_manager.test_base;

import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.VALID_PASSWORD;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.VALID_USERNAME;
import static org.openqa.selenium.By.xpath;

public class SessionHelper extends SelectorService {

    private By UserName = xpath("//input[@id='login_form_username']");
    private By Password = xpath("//input[@id='login_form_password']");
    private By SubmitButton = xpath("//button[@type='submit']");

    public SessionHelper(WebDriver driver) {
        super(driver);
    }

    public void login_To_Website() {
        log.info("");
        type(UserName, VALID_USERNAME);
        type(Password, VALID_PASSWORD);
        click(SubmitButton, "[Login] button");

        if (driver.getCurrentUrl().endsWith("app.php")) {
            reportLog("You have entered valid credentials");
        } else if (driver.getCurrentUrl().endsWith("/security/login")) {
            reportLog("Please Check your Credentials and try to signin again");
            Assert.assertEquals(driver.getCurrentUrl(), driver.getCurrentUrl().endsWith("app.php"));
        }
        log.info("");
    }
}
