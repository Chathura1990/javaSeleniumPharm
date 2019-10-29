package itsm_pharmacy_test_files.pharmacy_complete_workflow;

import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import itsm_pharmacy_test_files.entry_tab.EntryTabSingleRxWorkflow;
import itsm_pharmacy_test_files.filling_tab.FillingTabWorkflow;
import itsm_pharmacy_test_files.order_tab.OrderTabWorkflow;
import itsm_pharmacy_test_files.qa_tab.QaTabWorkflow;
import itsm_pharmacy_test_files.shipping_tab.ShippingTabWorkflow;
import org.testng.annotations.Test;
import itsm_pharmacy_test_files.verification_tab.VerificationTabWorkflow;

import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;

public class PharmacyCompleteWorkflow extends TestBase{

    private EntryTabSingleRxWorkflow entryTabWf = new EntryTabSingleRxWorkflow();
    private OrderTabWorkflow orderTabWf = new OrderTabWorkflow();
    private VerificationTabWorkflow verificationTabWf = new VerificationTabWorkflow();
    private FillingTabWorkflow fillingTabWf = new FillingTabWorkflow();
    private QaTabWorkflow qaTabWf = new QaTabWorkflow();
    private ShippingTabWorkflow shippingTabWf = new ShippingTabWorkflow();

    /*<---------------Entry Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files.entry_tab\EntryTabSingleRxWorkflow.java
     */
    @Priority(101)
    @Test(priority = 1, groups = "EntryTab")
    public void select_Rx_From_Queues_Test() throws InterruptedException {
        log.info("<--------------------------------------------------------- Entry Tab --------------------------------------------------------->");
        entryTabWf.select_valid_Rx(app);//Go to Queues and Select Single Rx from Pending Rxes/No Allergy Info
    }

    @Priority(102)
    @Test(priority = 2, groups = "EntryTab")
    public void click_Go_To_Next_Stage_Button_Test() {
        entryTabWf.click_Go_To_Next_Stage_Button(app); //Click Go to Next Stage Button in order to continue the process
    }

    @Priority(103)
    @Test(priority = 3, groups = "EntryTab")
    public void change_Bundle_To_Single_If_Rx_is_Bundle_Test() {
        entryTabWf.change_Bundle_To_Single(app); //Go to Queues and Select Rx from Pending Rxes/No Allergy Info
    }

    @Priority(104)
    @Test(priority = 4, groups = "EntryTab")
    public void prepare_Patient_For_DUR_Test() throws InterruptedException, ParseException {
        entryTabWf.change_Patient_Info(app); //Prepare patient information for DUR check
    }

    @Priority(105)
    @Test(priority = 5, groups = "EntryTab")
    public void choose_Medication_from_dropdown_Test() {
        entryTabWf.choose_Medication_from_dropdown(app); //Choose Medication from Medication/DTS dropdown
    }

    @Priority(106)
    @Test(priority = 6, groups = "EntryTab")
    public void enter_QtyPrescribed_DaySupply_QtyPrescribed_Test() {
        entryTabWf.enter_QtyPrescribed_DaySupply_DateWritten(app); //Enter Qty Prescribed, Day supply and Date written
    }

    @Priority(107)
    @Test(priority = 7, groups = "EntryTab")
    public void click_Submit_Button_In_RxTab_Test() throws InterruptedException {
        entryTabWf.click_Submit_Button_In_RxTab(app); //Click Submit Button to Continue And Get Success Message
    }

    /*<---------------Order Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files.order_tab\OrderTabWorkflow.java
     */
    @Priority(108)
    @Test(priority = 8, groups = "OrderTab", dependsOnGroups = "EntryTab")
    public void get_Order_Number_And_Date_Test() throws ParseException{
        log.info("<--------------------------------------------------------- Order Tab --------------------------------------------------------->");
        orderTabWf.checkOrderNumberAndDate(app); //Get Order Number & Check Current Order date is Future date or not
    }

    @Priority(109)
    @Test(priority = 9, groups = "OrderTab", dependsOnGroups = "EntryTab")
    public void verify_QtyDispensed_DaySupply_Test() {
        orderTabWf.verifyQtyPrescribedAndDaySupply(app); //Verify if Qty_Dispensed and Day_Supply Data According to Rx Data or Not
    }

    @Priority(110)
    @Test(priority = 10, groups = "OrderTab", dependsOnGroups = "EntryTab")
    public void Select_NDC_From_Select_NDC_Test() {
        orderTabWf.selectNDCFromWidget(app); //Select a NDC from NDC selector and check amount of characters in NDC code
    }

    @Priority(111)
    @Test(priority = 11, groups = "OrderTab", dependsOnGroups = "EntryTab")
    public void check_Drug_Utilization_Review_Test() {
        orderTabWf.checkDURNotifications(app); //Check Drug Interaction notifications
    }

    @Priority(112)
    @Test(priority = 12, groups = "OrderTab", dependsOnGroups = "EntryTab")
    public void click_Submit_Button_In_OrderTab_Test() throws InterruptedException {
        orderTabWf.clickSubmitButtonToContinue(app); //Click Submit Button if available
    }

    /*<---------------Verification Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files.verification_tab\VerificationTabWorkflow.java
     */
    @Priority(113)
    @Test(priority = 13, groups = "VerificationTab", dependsOnGroups = "OrderTab")
    public void check_Order_Profit_And_RemBalance() throws InterruptedException {
        log.info("<----------------------------------------------------- Verification Tab ------------------------------------------------------>");
        verificationTabWf.getProfitAndPtRespFromOrder(app); //Get Profit of Order and Pt Remaining Balance
    }

    @Priority(114)
    @Test(priority = 14, groups = "VerificationTab", dependsOnGroups = "OrderTab")
    public void click_Accept_Button() {
        verificationTabWf.clickAcceptButtonTest(app); //Click Accept button if available in Verification Tab
    }

    /*<---------------Filling Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files\filling_tab\FillingTabWorkflow.java
     */
    @Priority(115)
    @Test(priority = 15, groups = "FillingTab", dependsOnGroups = "VerificationTab")
    public void enter_RxId_And_Click_Search_Button_Test() {
        log.info("<--------------------------------------------------------- Filling Tab ------------------------------------------------------->");
        fillingTabWf.enterRxIdAndClickSearchBtn(app); //Enter Rx Id into 'Scan Leaflet' Searchbox and click Search button
    }

    @Priority(116)
    @Test(priority = 16, groups = "FillingTab", dependsOnGroups = "VerificationTab")
    public void click_PDF_checkbox_And_print_Order_Test() throws InterruptedException {
        fillingTabWf.printLeaflet(app); //Click PDF checkbox to select and Download leaflet
    }

    /*<---------------QA Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files\qa_tab\QaTabWorkflow.java
     */
    @Priority(117)
    @Test(priority = 17, groups = "QaTab", dependsOnGroups = "FillingTab")
    public void enter_RxId_Test() throws InterruptedException {
        log.info("<----------------------------------------------------------- QA Tab ----------------------------------------------------------->");
        qaTabWf.enter_RxId_And_Press_Enter(app); //Enter RxId into QA tab Searchbox and press Enter key
    }

    @Priority(118)
    @Test(priority = 18, groups = "QaTab", dependsOnGroups = "FillingTab")
    public void Click_Approve_Button_Test() throws InterruptedException {
        qaTabWf.approve_The_Order(app); //Enter Dur comment and Approve the order
    }

    /*<---------------Shipping Tab Workflow--------------->
     *@path src\test\java\itsm_pharmacy_test_files\shipping_tab\ShippingTabWorkflow.java
     */
    @Priority(119)
    @Test(priority = 19, groups = "ShippingTab", dependsOnGroups = "QaTab")
    public void click_Save_Weight_Button_Test() throws InterruptedException {
        log.info("<-------------------------------------------------------- Shipping Tab -------------------------------------------------------->");
        shippingTabWf.enter_RxId_And_click_Save_Weight_Btn(app); //Enter RxId and click Save Weight-Measurement button in Shipping Tab
    }

    @Priority(120)
    @Test(priority = 20, groups = "ShippingTab", dependsOnGroups = "QaTab")
    public void check_OrderHistory_Click_SaveOrder_Button_Test() throws InterruptedException {
        shippingTabWf.check_OrderHist_And_Click_Save_OrderBtn(app); //Check Order History button And Click Save Order Button
    }

    @Priority(121)
    @Test(priority = 21, groups = "ShippingTab", dependsOnGroups = "QaTab")
    public void click_Finalize_Button_And_Get_Success_Test() throws InterruptedException {
        shippingTabWf.select_Shipping_Method_And_Click_Finalize_Btn(app); //Click [Finalize] Button and get Success Message
    }


}
