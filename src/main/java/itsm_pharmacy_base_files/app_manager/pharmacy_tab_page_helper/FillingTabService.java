package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.FillingTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class FillingTabService extends SelectorService {

    private By FillingTabImage = xpath("//*[@src='/bundles/internalsitepharmacy/images/filling.png']");
    private By RxIdInputField = id("rx-filling-rx-id");
    private By SearchBtn = id("rx-filling-search-btn");
    private By SuccessMessage = xpath("//*[contains(text(),'Verified successfully.')]");
    private By Checkbox = xpath("//input[@class='select-all-items']");
    private By PrintLeafletButton = xpath("//button[@class='btn btn-sm btn-primary print-selected']");
    private By DownloadPdfBtn = cssSelector("button[class*='download-pdf-selected']");

    public FillingTabService(WebDriver driver){
        super(driver);
    }

    public void checkFillingTabImage(){
        checkImageIsAvailableOrNot(FillingTabImage, "'images/filling.png'", "Filling Tab");
    }

    public void enterRxId(FillingTabData fillingTabData){
        type(RxIdInputField,String.valueOf(fillingTabData.getRxid()));
    }

    public void clickSearchButton(){
        click(SearchBtn,"[Search] button");
    }

    public String clickPdfCheckbox() {//AndClickDownloadBtn String originalHandle =
        click(Checkbox,"[checkbox] to select Item");
         return driver.getWindowHandle();
    }

    public void clickPrintLeafletButton(){
        click(PrintLeafletButton,"[Print Leaflet] Button");
    }

    public void clickDownloadBtn(String originalHandle) throws InterruptedException {
        invisibilityOfElementLocatedByLocator(SuccessMessage, SECONDS20);
        click(DownloadPdfBtn,"[Download PDF] button");
        sleep(LONG_WAIT+2000);
        for(String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                reportLog("Opened new window and Wait until Download leaflet");
                driver.switchTo().window(handle);
                driver.close();
                reportLog("Closed new tab and go back to old window");
            }
        }
        driver.switchTo().window(originalHandle);
    }


}
