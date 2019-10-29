package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class ReturnsTabService extends SelectorService {

    private By PatientId = id("wdg_3_pat_search_id");
    private By SearchBtn = id("wdg_3_btn_search");
    private By SaveBtn = id("rx_return_item_type_save");
    private By InputsInReturnsTab = xpath("//input[starts-with(@id,'rx_return_item_type_items')]");
    private By Notification = xpath("//div[@class='ui-pnotify-text']");
    private By ConfirmationNeeded = xpath("//h4[contains(text(),'Confirmation Needed')]");
    private By OkButton = xpath("//button[contains(text(),'OK')]");

    public ReturnsTabService(WebDriver driver) {
        super(driver);
    }

    public void goToReturnsTab(ApplicationManager app){
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToManagementTab();
        app.getNavigationServicePharmMain().goToReturnsTab();
    }

    public void goToReturnsQueueUsingQueuesTab(ApplicationManager app){
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToQueuesTab();
        app.getNavigationServiceQueuesTabs().goToReturnsQueue();
    }

    public void searchForAPatient(PatientsTabData pat) {
        type(PatientId, String.valueOf(pat.getPatientId()));
        click(SearchBtn,"Search button");
        click(xpath("//td[contains(text(),'"+pat.getPatientId()+"')]"),"Search result: "+pat.getPatientId()+"");
        click(xpath("//tr[@data-patient-id='"+pat.getPatientId()+"']"),"Shipment");
    }

    public void selectAction(int itemNumber,int value) {
        selectAnOptionFromDropdown(id("rx_return_item_type_items_"+itemNumber+"_return_action"),value);
        reportLog("Selected item in the panel: "+itemNumber+" & option: "+value+" from Actions");
    }

    public void selectReason(int itemNumber, int value) {
        selectAnOptionFromDropdown(id("rx_return_item_type_items_"+itemNumber+"_return_reason"),value);
        if(value == 9){
            type(id("rx_return_item_type_items_"+itemNumber+"_return_reasonDetails"),"Automation Test");
        }
        reportLog("Selected item in the panel: "+itemNumber+" & option: "+value+" from Reasons");
    }

    public String enterRestockedValue(int itemNumber) throws InterruptedException {
        By discardedInputLocator = id("rx_return_item_type_items_"+itemNumber+"_return_rxReturnLots_0_quantityDiscarded");
        By restockedInputLocator = id("rx_return_item_type_items_"+itemNumber+"_return_rxReturnLots_0_quantityRestocked");

        String discardedInputText = getAttribute(discardedInputLocator,"value");
        type(restockedInputLocator, discardedInputText);
        sleep(MILLISEC1000);
        type(discardedInputLocator,"0");
        return discardedInputText;
    }

    public int checkAvailabilityOfRestockedAndDiscardedFields(){
        List<WebElement> inputs = driver.findElements(InputsInReturnsTab);
        List<WebElement> disabledInputs = new ArrayList<WebElement>();
        for(WebElement input : inputs) {
            if (input.getAttribute("id") != null && input.getAttribute("readonly") != null) {
                disabledInputs.add(input);
            }
        }
        reportLog("Total amount of disabled inputs: "+disabledInputs.size());
        return disabledInputs.size();
    }

    public void clickSaveButton(){
        click(SaveBtn,"[Save] button");
    }

    public String getSuccessMessage(){
        return getText(Notification);
    }

    public void chooseAPatientUsingPatId(PatientsTabData pat) throws InterruptedException {
        sleep(MILLISEC1000);
        click(xpath("//p[@data-brief-value='"+pat.getPatientId()+"']/parent::td/following::td/button[1]"),"Patient Id");

    }

    public String assertDataInReturnedItem(int itemNumber) {
        By restockedInputLocator = id("rx_return_item_type_items_"+itemNumber+"_return_rxReturnLots_0_quantityRestocked");
        return getAttribute(restockedInputLocator,"value");
    }

    public void deleteReturnItem(PatientsTabData pat) throws InterruptedException {
        sleep(MILLISEC1000);
        click(xpath("//p[@data-brief-value='"+pat.getPatientId()+"']/parent::td/following-sibling::td/button[2]"),"Item delete");
        sleep(MILLISEC1000);
        ifElementIsDisplayedByLocator(ConfirmationNeeded,OkButton,"Confirmation needed window");
    }
}

