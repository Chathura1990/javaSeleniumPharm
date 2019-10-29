package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.EntryTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class EntryTabService extends SelectorService {

    //Rx Tab Main Menu Elements
    private By GetRxId = id("rx_type_panel_container");

    private By StepForwardBtn = xpath("//a[@class='nav-item pointer']/span");
    //Rx Tab Panel-Heading Container Elements
    private By BundleRadioBtn = id("bundle");
    private By SingleRadioBtn = id("single");
    //Patient info Section
    private By PatientName = xpath("//*[@id='single_rx_type_patName']/p/a/span");
    private By BriefPatInfo = xpath("//*[contains(text(), 'Brief Patient Info')]");
    private By EyeOpenIcon = xpath("//*[@id='single_rx_type_patName']/p/a/span");
    //Medication
    private By TypeE119RadioBtn = id("single_rx_type_icd10_icd10_0");
    private By TypeE109RadioBtn = id("single_rx_type_icd10_icd10_1");
    private By InsulinRadioBtnYes = id("single_rx_type_insulin_0");
    private By InsulinRadioBtnNo = id("single_rx_type_insulin_1");
    private By MedicationDtsInput = id("single_rx_type_productSets_0_products_0_productName");
    private By QtyPrescribed = id("single_rx_type_productSets_0_products_0_quantityPrescribed");
    private By DaysSupply = id("single_rx_type_productSets_0_products_0_daysSupply");
    private By DateWritten = id("single_rx_type_dateWritten");
    private By MinimizeHistoryBtn = id("footprints-header");
    private By Refills = id("single_rx_type_productSets_0_products_0_numberOfRefillsAuthorized");
    private By DAWritten = id("single_rx_type_productSets_0_products_0_dispenseAsWritten");
    private By RxOrigins = id("single_rx_type_rxOriginCode");
    //Success Message
    private By SuccessMessage = xpath("//div[@class='ui-pnotify-text']");
    //Rx Tab Bottom Elements
    private By RxSubmitBtn = id("single_rx_type_submit");

    public EntryTabService(WebDriver driver) {
        super(driver);
    }

    public void selectValidRxFromQueues(ApplicationManager app) throws InterruptedException {
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToQueuesTab();
        app.getNavigationServiceQueuesTabs().goToPendingRxes();
        sleep(LONG_WAIT);
        app.getNavigationServiceQueuesTabs().selectValidRxFromQueues();
    }

    public int getRxId() {
        int rxId = Integer.valueOf(visibilityOfElementLocatedBylocator(GetRxId, SECONDS20).getAttribute("data-rxid"));
        reportLog("RX #Id by ('data-rxid') attribute --> " + "#" + rxId);
        return rxId;
    }

    public void clickGoToNextStageButton() {
        String attribute = getAttribute(StepForwardBtn,"class");
        reportLog("Get attribute 'class' of the element 'go-to-next-stage' "+ StepForwardBtn);
        reportLog("Change if 'go-to-next-stage' mood is 'glyphicon glyphicon-stop' to 'forward'");
        if (attribute.equals("glyphicon glyphicon-stop")|| attribute.equals("glyphicon glyphicon-retweet")) {
            click(StepForwardBtn, "[go to Next stage] button");
            reportLog("Go to next stage button mode-> [" + visibilityOfElementLocatedBylocator(StepForwardBtn, SECONDS20).getAttribute("class") + "]");
        }
    }

    public void changeBundleToSingle() {
        implicit_Wait(SECONDS20);
        WebElement Bundle = driver.findElement(BundleRadioBtn);
        WebElement Single = driver.findElement(SingleRadioBtn);
        reportLog("Check if Single or Bundle selected in");
        if (Bundle.isSelected()) {
            click(SingleRadioBtn, "Rx template type [Single]");
        } else if (Single.isSelected()) {
            reportLog("Rx type is Single");
        }
    }

    public void goToPatientProfile() throws InterruptedException {
        actionSelectElementByLocator(EyeOpenIcon,"[glyphicon-eye-open] icon", SECONDS20);
        sleep(MILLISEC2000);
    }

    public void hoverToCheckBriefInfo(By locator, By locator2){
        hoverOnElement(locator,locator2,"Brief Info section");
    }

    public void chooseInsulinRadioButton(boolean answer){
        if(answer){
            click(InsulinRadioBtnYes, "Insulin = 'YES'");
        }else{
            click(InsulinRadioBtnNo, "Insulin = 'NO'");
        }
    }

    public void chooseICD10RadioButton(boolean icd10){
        if(icd10){
            click(TypeE119RadioBtn, "ICD-10 = 'E119'");
        }else{
            click(TypeE109RadioBtn, "ICD-10 = 'E109'");
        }
    }

    public void chooseMedicationFromDropdrown(EntryTabData entryTabData){
        click(MedicationDtsInput, "Medication/DTS input field");
        typeAndPressEnter(MedicationDtsInput, entryTabData.getMedicationDts());
        implicit_Wait(SECONDS20);
        click(xpath("//div[starts-with(text(), '"+entryTabData.getMedicationDts()+"')]"),
                driver.findElement(xpath("//div[starts-with(text(), '"+entryTabData.getMedicationDts()+"')]")).getText());
    }

    public void fillEntryTabData(EntryTabData entryTabData){
        //Enter Qty Prescribed
        implicit_Wait(SECONDS20);
        type(QtyPrescribed,String.valueOf(entryTabData.getQtyPrescribed()));
        //Enter Days Supply
        implicit_Wait(SECONDS20);
        typeAndPressEnter(DaysSupply,String.valueOf(entryTabData.getDaySupply()));
    }

    public void EnterDateWritten(){
        //Enter Date Written
        implicit_Wait(SECONDS20);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        type(DateWritten, date1);
        reportLog("Rx Date Written: " + driver.findElement(DateWritten).getAttribute("value"));
    }

    public void checkDispenseAsWritten(){
        assertNumberOfOptionsInDropdown(DAWritten, "Dispense As Written", 10);
    }

    public void checkAvailableRxOrigins(){
        assertNumberOfOptionsInDropdown(RxOrigins, "Rx Origin", 6);
    }

    public void clearRequiredFields() {
        driver.findElement(MinimizeHistoryBtn).click();
        if(isELementPresent(QtyPrescribed) && isELementPresent(DaysSupply) && isELementPresent(Refills)) {
            clearField(QtyPrescribed);
            clearField(DaysSupply);
            clearField(Refills);
        }
    }

    public void assertRequiredFieldsMessage(){
        if(driver.findElement(QtyPrescribed).getText().isEmpty()
                && driver.findElement(DaysSupply).getText().isEmpty()
                && driver.findElement(Refills).getText().isEmpty()
                && getSuccessMessage().equals("Please, check your input in highlighted fields!")) {
            reportLog("Sites Expected Error Message-->" + " " + "Please, check your input in highlighted fields!");
            reportLog("Site Actual Error Message-->" + " " + getSuccessMessage());
            Assert.assertEquals(getSuccessMessage(), "Please, check your input in highlighted fields!");
        }
    }

    public void clickSubmitButton(){
        invisibilityOfElementLocatedByLocator(SuccessMessage,IMPLICIT_WAIT);
        click(RxSubmitBtn, "[Submit] button");
    }

    public String getSuccessMessage(){
        implicit_Wait(SECONDS20);
        try {
            return getText(SuccessMessage);
        }catch (NoSuchElementException e){
            return reportLog("*** ELEMENT NOT FOUND ***");
        }
    }
}
