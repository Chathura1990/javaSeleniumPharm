package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.EntryTabData;
import itsm_pharmacy_base_files.app_manager.model_data.OrderTabData;
import itsm_pharmacy_base_files.app_manager.navigate_to_tabs.NavigationServiceInternalsite;
import itsm_pharmacy_base_files.app_manager.navigate_to_tabs.NavigationServicePharmMain;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static itsm_pharmacy_test_files.order_tab.OrderTabWorkflow.OrderNumberById;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class OrderTabService extends SelectorService {

    private int orderId;
    private By GetOrderNumber = xpath("//*[@name='rx_order']");
    private By PutOnHoldBtn = id("rx_order_putOnHold");
    private By TitlePutOnHold = xpath("//*[contains(text(),'Put on hold')]");
    private By CancelBtn = xpath("//button[contains(text(),'Cancel')][@class='btn btn-default']");
    private By RestoreItemBtn = id("rx_order_restoreItems");
    private By RestoreBtn = xpath("//*[@id='restore_order_items']");
    private By CloseBtn = xpath("//*[@id='_Modal_0']/div/div/div[1]/button/span[1]");
    private By AddMiscBtn = id("rx_order_addMisc");
    private By AddMiscItemsBtn = id("add_miscellaneous_items");
    private By OrderDate = id("rx_order_date");
    private By OrderDateBtn = id("rx_order_date_button");
    private By ShippingMethodDropdown = id("shipment_method_shippingMethod");
    private By SaveShippingMethodsBtn = xpath("//button[@class='btn btn-success saveShippingMethod']");
    private By QtyDispensed = xpath("//label[contains(text(),'Qty Dispensed')]/parent::div/div/input");
    private By DaySupply = xpath("//label[contains(text(),'Day Supply')]/parent::div/div/p/input");
    private By NDCNumberInputField = xpath("//input[@class='form-control brief-product-information form-control']");
    private By SelectNDCBtn = xpath("//button[contains(text(),'Select NDC')]");
    private By ChooseNDCBtn = xpath("//button[@class='btn-xs btn btn-info rx_ordertemplate_templateitems_choose_ndc']");
    private By SelectBtn = xpath("//button[@class='btn-xs btn btn-info rx_ordertemplate_templateitems_choose_ndc']");
    //D.U.R Drug Interactions
    private By DrugDrug = xpath("//*[@id='headingOne_40085071645_1_0']/div/div[1]/h4/a");
    private By DrugCategory = xpath("//*[@id='drug-category-0']");
    private By DrugTitle = xpath("//a[@id='drug-title-0']");
    private By DrugAllergy = xpath("//*[@id='accordion_40085071645_0']/div[1]/div[1]/div[1]");
    private By Allergy = xpath("//*[@id='accordion_40085071645_0']/div[1]/div[1]/div[2]/span");
    private By DrugDisease = xpath("//*[@id='accordion_40085071645_0']/div[2]/div[1]/div[1]");
    private By Disease = xpath("//*[@id='accordion_40085071645_0']/div[2]/div[1]/div[2]/span");
    //footer line buttons
    private By ChangeRxConditionBtn = id("rx_order_changeCondition");
    private By PrescriptionCondLabel = xpath("//*[contains(text(),'Prescription condition:')]");
    private By ConditionsDropdown = xpath("//select[@id='rx_change_condition_rx_condition']");
    private By CallPatientBtn = id("rx_order_callPatient");
    private By CallPatientSaveBtn = xpath("//button[@id='call_patient_save']");
    private By AllConditionLabels = xpath("//form[@id='change-condition-form']");
    private By CallPatCancelBtn = id("call_patient_cancel");
    private By TestBillingConfBtn = id("test_billing_configure_order");
    private By BillingConfSaveBtn = xpath("//*[contains(text(),'Save')]");
    //Order Tab Bottom line Elements
    private By ConfirmationNeeded = cssSelector("button[class*='send-order-to-cs-button']");
    private By ApplyBtn = xpath("//*[@class='btn-success apply-oi-btn btn btn-sm']");
    private By OrderSubmitBtn = id("rx_order_submit");
    private By BillingStatus = xpath("//*[@id='_Modal_0']/div/div/div[2]/table/tbody/tr[2]/td[5]");
    private By ProceedBtn = xpath("//*[@id='bi-proceed']");
    //Batch Pharmacy Confirmation
    private By OrderIdsInput = id("batch_pharmacy_confirmation_form_orders_ids");
    private By ChangeBtn = id("batch_pharmacy_confirmation_form_submit");
    private By SuccessMsg = xpath("//div[@id='result']/p[2]/b");
    private By SuccessMsgCommon = xpath("//div[@class='ui-pnotify-text']");

    public OrderTabService(WebDriver driver){ super(driver); }

    private NavigationServiceInternalsite naviInternal = new NavigationServiceInternalsite(driver);
    private NavigationServicePharmMain naviPharmacy = new NavigationServicePharmMain(driver);

    public int getOrderId(){
        pageLoad_Timeout(PAGE_LOAD_TIMEOUT);
        orderId = Integer.valueOf(visibilityOfElementLocatedBylocator(GetOrderNumber, SECONDS20)
                .getAttribute("action").replaceAll("\\D", ""));
        reportLog("Get Order Id by ('action') attribute --> " + "#" + orderId);
        return orderId;
    }

    public void checkPanelHeadingButtons() throws InterruptedException {
        if(driver.findElement(PutOnHoldBtn).isEnabled() && driver.findElement(RestoreItemBtn).isEnabled()) {
            pageLoad_Timeout(PAGE_LOAD_TIMEOUT);
            click(PutOnHoldBtn, "[Put on Hold] Button");
            //Check Availability of put On Hold Button & widget
            ifElementIsDisplayedByLocator(TitlePutOnHold, CancelBtn, "Put on Hold Widget");

            //Check Availability of Restore Items Button & widget
            sleep(LONG_WAIT);
            actionSelectElementByLocator(RestoreItemBtn, "[Restore Items] Button", SECONDS20);
            ifElementIsDisplayedByLocator(RestoreBtn, CloseBtn, "Restore Items Widget");
        }
    }

    public String getOrderDate(){
        WebElement orderDate = driver.findElement(OrderDate);
        return orderDate.getAttribute("value");
    }

    public void checkOrderDate() throws InterruptedException, ParseException {
        sleep(LONG_WAIT);
        reportLog("Expected Date Format--> " + "mm/dd/yyyy");
        reportLog("Actual Date Format--> " + getAttribute(OrderDate,"value"));
        if(checkDate(getOrderDate()) && checkDateIsFutureDate(getOrderDate(), "Order") == true) {
            click(OrderDateBtn, "[rx order date] button");
        }else{
            type(OrderDate, new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
            click(OrderDateBtn, "[rx order date] button");
        }
    }

    public void checkShippingMethods(){
        assertNumberOfOptionsInDropdown(ShippingMethodDropdown, "Shipping Methods", 7);
    }

    public void clickSaveShippingMethodsButton(){
        driver.findElement(SaveShippingMethodsBtn).click();
    }

    public void checkQtyDispAndDaysSup(EntryTabData entryTabData){
        WebElement QtyDisp = driver.findElement(QtyDispensed);
        String QtyDispValue = QtyDisp.getAttribute("value");
        WebElement DaySup = driver.findElement(DaySupply);
        String DaySupplyValue = DaySup.getAttribute("value");
        if(QtyDispValue.equals(String.valueOf(entryTabData.getQtyPrescribed()))
                && DaySupplyValue.equals(String.valueOf(entryTabData.getDaySupply()))){
            reportLog("Amount of Qty Prescribed: "+QtyDispValue);
            reportLog("Day Supply: "+DaySupplyValue);
        }else {
            reportLog("Qty Prescribed and Day Supply values are not equals to Rx data");
        }
    }

    public void selectNDC() throws InterruptedException {
        String NDCValue = driver.findElement(NDCNumberInputField).getAttribute("data-brief-value");
        int separated = NDCValue.length();
        WebElement SelectNDC = driver.findElement(SelectNDCBtn);
        driver.findElement(SelectNDCBtn).sendKeys(Keys.PAGE_DOWN);
        sleep(MILLISEC1000);
        //Check if NDC Value is empty or not
        if(NDCValue.isEmpty()){
            reportLog("No NDC code in the Dispense NDC field");
            SelectNDC.click();
            reportLog("Clicked 'Select NDC' button ");
            WebElement Select = visibilityOfElementLocatedBylocator(ChooseNDCBtn, SECONDS20);
            reportLog("Wait for the 'Select NDC' widget to Select a NDC");
            String NDCValue2 = Select.getAttribute("data-ndc");
            Select.click();
            reportLog("Selected a NDC from the 'select NDC' widget");
            int separated2 = NDCValue2.length();
            reportLog("NDC code--> "+NDCValue2+" Amount of numbers in NDC code--> "+ separated2);
            Assert.assertEquals(separated2,11);
        }else{
            reportLog("NDC code--> "+NDCValue+" Amount of numbers in NDC code--> "+ separated);
            Assert.assertEquals(separated,11);
        }
    }

    public void checkDrugInteractionReviews(){
        if(driver.findElements(DrugDrug).size() > 0) {
            click(DrugDrug, "[Drug Drug] Interactions");
            reportLog("[Drug Drug] Interaction: (" + getText(DrugCategory) + "): " + getText(DrugTitle));
        }
        if(driver.findElements(DrugAllergy).size() >0) {
            reportLog("[" + getText(DrugAllergy).replaceAll("\\s", " ") + "] Interaction: " + getText(Allergy));
        }
        if(driver.findElements(DrugDisease).size() > 0) {
            reportLog("[" + getText(DrugDisease).replaceAll("\\s", " ") + "] Interaction: " + getText(Disease));
        }
    }

    public void clickRxConditionButton() throws InterruptedException {
        sleep(LONG_WAIT);
        scrollIntoViewByLocatorAndClick(ChangeRxConditionBtn,"[Change Rx Condition] button");
    }

    public void clickCallPatientButton() throws InterruptedException {
        sleep(LONG_WAIT);
        scrollIntoViewByLocatorAndClick(CallPatientBtn, "[Call Patient] button");
    }

    public void clickBillingConfigureOrderButton() throws InterruptedException {
        sleep(LONG_WAIT);
        scrollIntoViewByLocatorAndClick(TestBillingConfBtn,"[billing configuration] button");
    }

    public void checkRxConditionButton(){
        if(visibilityOfElementLocatedBylocator(PrescriptionCondLabel, SECONDS20).isDisplayed()) {
            assertNumberOfOptionsInDropdown(ConditionsDropdown, "Rx Condition", 18);
        }
        driver.findElement(id("rx_change_condition_back")).click();
    }

    public void checkCallPatientButton() {
        if(visibilityOfElementLocatedBylocator(CallPatientSaveBtn, SECONDS20).isDisplayed())
        {
            List<WebElement> reasons = driver.findElement(AllConditionLabels).findElements(By.tagName("label"));
            int AllReasons = reasons.size();
            reportLog("Amount of available Call Reasons--> "+ (AllReasons-4));
            int j=1;
            for(WebElement label : reasons)
            {
                reportLog("Call Patient reasons-->" + (j) + " " + label.getText());
                j++;
            }
            Assert.assertEquals(AllReasons-4, 20);
            driver.findElement(CallPatCancelBtn).click();
        }
    }

    public void checkBillingConfigureOrderButton() {
        implicit_Wait(SECONDS20);
        if(visibilityOfElementLocatedBylocator(CloseBtn, SECONDS20).isDisplayed())
        {
            reportLog("Opened Test billing configuration widget");
            click(BillingConfSaveBtn, "[Save] button");
        }
    }

    /**
     *This method will check whether Order needs a confirmation or not. If order needs a confirmation then it will go to
     * ('sitename'/app.php/tools/operations/batch/rx_confirm_status_change) page and confirm the order.
     */
    public void clickSubmitButton(OrderTabData otd) throws InterruptedException {
        sleep(LONG_WAIT);
        if (0 < driver.findElements(ConfirmationNeeded).size() || !driver.findElement(OrderSubmitBtn).isEnabled()) {
            reportLog("This order #" + OrderNumberById + " needs confirmation.");
            naviInternal.goToRxConfirmation(); //link to the Rx Confirmation page
            implicit_Wait(SECONDS20);
            type(OrderIdsInput, String.valueOf(otd.getOrderId()));
            click(ChangeBtn,"[Change] button");
            String successMsg = getText(SuccessMsg);
            reportLog(successMsg);
            if(successMsg.equals("Operation completed successfully!")){
                naviPharmacy.goToPharmacyMainPage(); //link to the Pharmacy Main Page
                naviPharmacy.goToOrderTab(); //link to the Pharmacy Order Tab
                implicit_Wait(SECONDS20);
                selectNDC();
                submitAndProceed();
            }
        } else {
            submitAndProceed();
        }
    }

    private void submitAndProceed() throws InterruptedException {
        sleep(LONG_WAIT + 2000);//Wait until scroll down to the element
        click(ApplyBtn,"[Apply] button");
        invisibilityOfElementLocatedByLocator(SuccessMsgCommon,IMPLICIT_WAIT);
        click(OrderSubmitBtn, "[Submit] button");
        sleep(LONG_WAIT + 2000);//Wait until get Ajax response.
        invisibilityOfElementLocatedByLocator(SelectBtn, SECONDS20);
        if (isELementPresent(BillingStatus) || visibilityOfElementLocatedBylocator(BillingStatus, SECONDS20)
                .getText().equals("Paid")) {
            reportLog("Wait until status get 'Success'");
            click(ProceedBtn, "[Proceed] button");
        }
    }

}
