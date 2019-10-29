package itsm_pharmacy_test_files.pharmacy_complete_workflow;

import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import itsm_pharmacy_test_files.entry_tab.EntryTabSingleRxWorkflow;
import itsm_pharmacy_test_files.filling_tab.FillingTabWorkflow;
import itsm_pharmacy_test_files.operations_warehouse.FedexAndInventoryTrackingNew;
import itsm_pharmacy_test_files.order_tab.OrderTabWorkflow;
import itsm_pharmacy_test_files.qa_tab.QaTabWorkflow;
import itsm_pharmacy_test_files.shipping_tab.ShippingTabWorkflow;
import itsm_pharmacy_test_files.verification_tab.VerificationTabWorkflow;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;

public class PharmCompleteWorkflow extends TestBase {

    public static int RxId;
    public static int PatientId;
    public static int PatientAge;
    public static String TrackingNumb;

    private EntryTabSingleRxWorkflow entryTabWf = new EntryTabSingleRxWorkflow();
    private OrderTabWorkflow orderTabWf = new OrderTabWorkflow();
    private VerificationTabWorkflow verificationTabWf = new VerificationTabWorkflow();
    private FillingTabWorkflow fillingTabWf = new FillingTabWorkflow();
    private QaTabWorkflow qaTabWf = new QaTabWorkflow();
    private ShippingTabWorkflow shippingTabWf = new ShippingTabWorkflow();
    private FedexAndInventoryTrackingNew fedexInventory = new FedexAndInventoryTrackingNew();

    /*<---------------Entry Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files.entry_tab\EntryTabSingleRxWorkflow.java
     */
    @Priority(1)
    @Test(priority = 1, groups = "EntryTab")
    public void selectRxFromQueuesAndEntryTabWorkflowTest() throws InterruptedException, ParseException{
        log.info("<--------------------------------------------------------- Entry Tab --------------------------------------------------------->");
        entryTabWf.select_valid_Rx(app);//Go to Queues and Select Single Rx from Pending Rxes/No Allergy Info
        entryTabWf.click_Go_To_Next_Stage_Button(app); //Click Go to Next Stage Button in order to continue the process
        entryTabWf.change_Bundle_To_Single(app); //Go to Queues and Select Rx from Pending Rxes/No Allergy Info
        entryTabWf.change_Patient_Info(app); //Prepare patient information for DUR check
        entryTabWf.choose_Medication_from_dropdown(app); //Choose Medication from Medication/DTS dropdown
        entryTabWf.enter_QtyPrescribed_DaySupply_DateWritten(app); //Enter Qty Prescribed, Day supply and Date written
        entryTabWf.click_Submit_Button_In_RxTab(app); //Click Submit Button to Continue And Get Success Message
    }

    /*<---------------Order Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files.order_tab\OrderTabWorkflow.java
     */
    @Priority(2)
    @Test(priority = 2, groups = "OrderTab", dependsOnGroups = "EntryTab")
    public void OrderTabWorkflowTest() throws ParseException, InterruptedException {
        log.info("<--------------------------------------------------------- Order Tab --------------------------------------------------------->");
        orderTabWf.checkOrderNumberAndDate(app); //Get Order Number & Check Current Order date is Future date or not
        orderTabWf.verifyQtyPrescribedAndDaySupply(app); //Verify if Qty_Dispensed and Day_Supply Data According to Rx Data or Not
        orderTabWf.selectNDCFromWidget(app); //Select a NDC from NDC selector and check amount of characters in NDC code
        orderTabWf.checkDURNotifications(app); //Check Drug Interaction notifications
        orderTabWf.clickSubmitButtonToContinue(app); //Click Submit Button if available
    }

    /*<---------------Verification Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files.verification_tab\VerificationTabWorkflow.java
     */
    @Priority(3)
    @Test(priority = 3, groups = "VerificationTab", dependsOnGroups = "OrderTab")
    public void VerificationTabWorkflowTest() throws InterruptedException {
        log.info("<----------------------------------------------------- Verification Tab ------------------------------------------------------>");
        verificationTabWf.getProfitAndPtRespFromOrder(app); //Get Profit of Order and Pt Remaining Balance
        verificationTabWf.clickAcceptButtonTest(app); //Click Accept button if available in Verification Tab
    }

    /*<---------------Filling Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files\filling_tab\FillingTabWorkflow.java
     */
    @Priority(4)
    @Test(priority = 4, groups = "FillingTab", dependsOnGroups = "VerificationTab")
    public void FillingTabWorkflowTest() throws InterruptedException {
        log.info("<--------------------------------------------------------- Filling Tab ------------------------------------------------------->");
        fillingTabWf.enterRxIdAndClickSearchBtn(app); //Enter Rx Id into 'Scan Leaflet' Searchbox and click Search button
        fillingTabWf.printLeaflet(app); //Click PDF checkbox to select and Download leaflet
    }

    /*<---------------QA Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files\qa_tab\QaTabWorkflow.java
     */
    @Priority(5)
    @Test(priority = 5, groups = "QaTab", dependsOnGroups = "FillingTab")
    public void QaTabWorkflowTest() throws InterruptedException {
        log.info("<----------------------------------------------------------- QA Tab ----------------------------------------------------------->");
        qaTabWf.enter_RxId_And_Press_Enter(app); //Enter RxId into QA tab Searchbox and press Enter key
        qaTabWf.approve_The_Order(app); //Enter Dur comment and Approve the order
    }

    /*<---------------Shipping Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files\shipping_tab\ShippingTabWorkflow.java
     */
    @Priority(6)
    @Test(priority = 6, groups = "ShippingTab", dependsOnGroups = "QaTab")
    public void ShippingTabWorkflowTest() throws InterruptedException {
        log.info("<-------------------------------------------------------- Shipping Tab -------------------------------------------------------->");
        shippingTabWf.enter_RxId_And_click_Save_Weight_Btn(app); //Enter RxId and click Save Weight-Measurement button in Shipping Tab
        shippingTabWf.check_OrderHist_And_Click_Save_OrderBtn(app); //Check Order History button And Click Save Order Button
        shippingTabWf.select_Shipping_Method_And_Click_Finalize_Btn(app); //Click [Finalize] Button and get Success Message
    }

    /*<---------------Warehouse tabs--------------->
     *@path src\main\java\itsm_pharmacy_test_files\operations_warehouse\FedexAndInventoryTrackingNew.java
     */
    @Priority(7)
    @Test(priority = 7)
    public void checkShippingStatusTest() throws IOException {
        log.info("<-------------------------------------------------------- Warehouse Tabs -------------------------------------------------------->");
        fedexInventory.checkAvailabilityOfShipment(app);//Check availability of shipment by entering patient Id in Fedex and Inventory tracking new page
    }

    @Priority(8)//Saving patient id and rx id in a json file for later use.
    @Test(priority = 8, dependsOnMethods = "checkShippingStatusTest")
    public void writeDataInJsonFile() {
        log.info("<-------------------------------------------------------- Write data in a json file -------------------------------------------------------->");
        JSONObject obj = new JSONObject();
        obj.put("PatientId",PatientId);
        obj.put("RxId",RxId);
        obj.put("TrackingNumber", TrackingNumb);

        try (FileWriter file = new FileWriter("./src/main/resources/testData.json")){
            file.write(obj.toJSONString());
            reportLog("Patient details: "+obj.toJSONString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
