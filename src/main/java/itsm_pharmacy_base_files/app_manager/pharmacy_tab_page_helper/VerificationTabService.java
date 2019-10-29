package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class VerificationTabService extends SelectorService {

    private By VerificationTabImage = xpath("//*[@src='/bundles/internalsitepharmacy/images/verification.png']");
    private By ChooseOrderDropdown = id("rx-verification-select-item");
    private By SelectItemContainer = id("select2-rx-verification-select-item-container");
    private By FirstOrderDropdown = xpath("//*[@id='select2-rx-verification-select-item-results']/li");
    private By Warnings = xpath("//*[contains(text(),'Warning!')]");
    private By DateWritten = id("rx-verification-rx-dateWritten");
    private By SelectShippingMethod = xpath("//*[@id='verification_shipping_method_saver_container']/div/div/div[1]/select");
    private By ShippingMethodSaveBtn = xpath("//*[@id='verification_shipping_method_saver_container']/div/div/div[1]/span/button");
    private By ShippingMethodMessage = xpath("//*[contains(text, 'Shipping method saved')]");
    private By OrdrProfit = xpath("//p[@title='Remaining balance']/parent::div/p[1]");
    private By PatientResp = xpath("//p[@title='Remaining balance']");
    private By OrderTabSuccessMsg = xpath("//*[contains(text, 'Orders submitted. Now billing will be performed.')]");
    private By AcceptBtn = xpath("//*[@class='btn btn-success btn-xs rx-verification-accept ']");
    private By SuccessMessage = xpath("//div[@class='ui-pnotify-text']");

    public VerificationTabService(WebDriver driver){
        super(driver);
    }

    public void checkVerificationTabImage(){
        checkImageIsAvailableOrNot(VerificationTabImage, "'images/verification.png'", "Verification Tab");
    }

    public void selectOrderFromDropDownContainer(){
        Select Orders = new Select(driver.findElement(ChooseOrderDropdown));
        List<WebElement> AmountOfOrders = Orders.getOptions();
        int countOrders = AmountOfOrders.size();
        reportLog("Amount of available Orders in Order dropdown: " + countOrders);
        driver.findElement(SelectItemContainer).click();
        driver.findElement(FirstOrderDropdown).click();
        reportLog("Clicked First Result from Order dropdown: "+ driver.findElement(SelectItemContainer).getAttribute("title").replaceAll("\\D", ""));
        int i = 2;
        do {
            implicit_Wait(SECONDS20);
            if (driver.findElement(Warnings).isDisplayed()) {
                reportLog("Contains a Warning Message in Order : " + driver.findElement(SelectItemContainer).getAttribute("title").replaceAll("\\D", ""));
                driver.findElement(SelectItemContainer).click();
                driver.findElement(By.xpath("//*[@id='select2-rx-verification-select-item-results']/li[" + i + "]")).click();
                reportLog("Clicked " + i + " Result from Order dropdown");
            } else {
                break;
            }
            i++;
        } while (i < AmountOfOrders.size());
    }

    public void getOrderProfitAndPtResp() throws InterruptedException {
        sleep(MILLISEC1000);
        pageLoad_Timeout(PAGE_LOAD_TIMEOUT);
        reportLog("Waiting for Page load");
        String OrderProfit = driver.findElement(OrdrProfit).getText();
        reportLog("Getting Order profit "+PatientResp);
        String PtResponsibility = driver.findElement(PatientResp).getText();
        reportLog("Getting Order Patient Responsibility "+OrdrProfit);
        reportLog("Order profit is: "+ OrderProfit);
        reportLog("Order Patient Responsibility is: "+ PtResponsibility);
    }

    public void checkOrderDateIsFutureDateOrNot() throws ParseException {
        WebElement orderDate = driver.findElement(DateWritten);
        String ODate = orderDate.getAttribute("value");
        Assert.assertFalse(checkDateIsFutureDate(ODate, "Order"));//Check Order Date is a Future date or not
    }

    public void selectShippingMethod(int index){
        Select ShippingMethod = new Select(driver.findElement(SelectShippingMethod));
        List<WebElement> options = ShippingMethod.getOptions();
        options.get(index).click();
        reportLog("Selected shipping method: "+options.get(index).getText());
        driver.findElement(ShippingMethodSaveBtn).click();
    }

    public void clickAcceptButton() {
        invisibilityOfElementLocatedByLocator(ShippingMethodMessage, SECONDS20);
        List<WebElement> AllButtons = driver.findElements(By.tagName("button"));
        List<WebElement> acceptButtons = new ArrayList<>();
        boolean breakIt;
        for (WebElement button : AllButtons) {
            breakIt = true;
            try {
                if (button.getText().contains("accept")) {
                    acceptButtons.add(button);
                    button.click();
                    reportLog("Clicked Accept button: " + acceptButtons.size());
                    sleep(MILLISEC2000);
                    getSuccessMessageCommon();
                } else {
                    breakIt = false;
                }
            } catch (StaleElementReferenceException | InterruptedException ex) {
                breakIt = false;
            }
            if (breakIt) {
                continue;
            }
        }
    }

}
