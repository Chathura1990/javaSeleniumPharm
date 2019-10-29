package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.WarehouseData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.*;

public class FedexInventoryService extends SelectorService {

    //Fedex & Inventory Tracking nav tab buttons
    private By FindPackageBtn = xpath("//a[contains(text(),'Find Package')]");
    //Field on the 'Find Package' page
    private By SelectMethodFromDropdown = id("find-package-field");
    private By FindPackageValue = id("find-package-value");
    private By FindBtn = id("find-package-button");
    private By TrackingNum = xpath("//*[@id='fedex-package-table']/tbody/tr/td[5]/ul/li/a");

    public FedexInventoryService(WebDriver driver) {
        super(driver);
    }

    public void clickFindPackageBtn(){
        click(FindPackageBtn,"[Find Package] button");
    }

    public void chooseMethodToFindPackage(WarehouseData whd){
        selectAnOptionFromDropdown(SelectMethodFromDropdown,whd.getIndex());
    }

    public void EnterPackageDetails(WarehouseData whd){
        type(FindPackageValue, String.valueOf(whd.getPatientId()));
    }

    public void clickFindBtn(){
        click(FindBtn,"[Find] button");
    }

    public String checkTrackingNumber() throws IOException {
        String trackingNumber = getText(TrackingNum).replaceAll("\\s", " ");
        if (!trackingNumber.isEmpty()) {
            String url = "https://www.fedex.com/apps/fedextrack/?tracknumbers=" + trackingNumber + "";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            int code = connection.getResponseCode();
            connection.disconnect();
            if (code == 200) {
                reportLog("Link: " + url + " code status is: " + code);
                assertEquals(code, 200);
            } else {
                reportLog("This link is corrupted: " + url);
                assertFalse(false);
            }
        }else{
            reportLog("Unable to get Tracking details!");
            assertFalse(false);
        }
        return trackingNumber;
    }

}
