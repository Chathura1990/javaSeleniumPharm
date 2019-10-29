package itsm_pharmacy_test_files.order_tab;

import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.LONG_WAIT;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.xpath;

public class OrderTabValidationTest extends TestBase {

    private int orderId;
    private By OrdersTabImage = xpath("//*[@src='/bundles/internalsitepharmacy/images/orders.png']");


    @Priority(1)
    @Test(priority = 1)
    public void select_New_Order_From_Queues_Tab_Test() throws InterruptedException {
        reportLog("****** Select New Order From Queues Tab *****");
        selectAValidPendingOrder();
        sleep(LONG_WAIT);
        app.getNavigationServiceQueuesTabs().selectNewOrderFromQueues();
        orderId = app.getOrderTabService().getOrderId();
    }

    @Priority(2)
    @Test(priority = 2)
    public void check_Availability_Of_EntryTab_Image_Test() throws InterruptedException {
        log.info("");
        reportLog("**** Check Availability of Order Tab Image *****");
        selectAValidPendingOrder();
        sleep(LONG_WAIT);
        app.getNavigationServiceQueuesTabs().selectNewOrderFromQueues();
        Assert.assertTrue(app.getSelectorService().checkImageIsAvailableOrNot(OrdersTabImage, "'images/entry.png'", "Order tab"));
    }

    @Priority(3)
    @Test(priority = 3)
    public void check_Panel_Heading_Buttons_Test() throws InterruptedException {
        log.info("");
        reportLog("****** Check Availability of Order tab icon and Buttons in panel-heading *****");
        selectAValidPendingOrder();
        sleep(LONG_WAIT);
        app.getNavigationServiceQueuesTabs().selectNewOrderFromQueues();
        app.getOrderTabService().checkPanelHeadingButtons();
    }

    private void selectAValidPendingOrder() {
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToQueuesTab();
        app.getNavigationServiceQueuesTabs().goToTotalPendingOrders();
    }

    //These tests are not working due to the bug in Order tab. Ticket number #476173

//    @Priority(4)
//    @Test(priority = 4)
//    public void check_Order_Date_Test() throws InterruptedException, ParseException {
//        log.info("");
//        reportLog("****** Check Order Date is in correct format or not *****");
//        app.getNavigationServicePharmMain().goToPharmacyMainPage();
//        app.getNavigationServicePharmMain().goToQueuesTab();
//        app.getNavigationServiceQueuesTabs().goToTotalPendingOrders();
//        sleep(LONG_WAIT);
//        app.getNavigationServiceQueuesTabs().selectNewOrderFromQueues();
//        app.getOrderTabService().checkOrderDate();
//        app.getSelectorService().assertSuccessMessage("Date updated!");
//    }
//
//    @Priority(5)
//    @Test(priority = 5)
//    public void check_Shipping_Methods_Test() throws InterruptedException {
//        log.info("");
//        reportLog("****** Check availability of Shipping Methods *****");
//        app.getOrderTabService().checkShippingMethods();
//        app.getOrderTabService().clickSaveShippingMethodsButton();
////        app.getSelectorService().closeNotification();
//        sleep(LONG_WAIT);
//        app.getSelectorService().assertSuccessMessage("Shipping method saved");
//    }
//
//    @Priority(6)
//    @Test(priority = 6)
//    public void check_NDC_Field_And_Select_NDC_Test() throws InterruptedException {
//        log.info("");
//        reportLog("****** Check NDC field and select NDC from Select NDC widget *****");
//        app.getOrderTabService().selectNDC();
//        app.getOrderTabService().clickSaveShippingMethodsButton();
//    }
//
//    @Priority(7)
//    @Test(priority = 7)
//    public void check_RxCondition_Buttons_Test() throws InterruptedException {
//        log.info("");
//        reportLog("****** Check Rx Condition Button & it's availability *****");
//        app.getOrderTabService().clickRxConditionButton();
//        app.getOrderTabService().checkRxConditionButton();
//        reportLog("");
//        app.getOrderTabService().clickCallPatientButton();
//        app.getOrderTabService().checkCallPatientButton();
//        reportLog("");
//        app.getOrderTabService().clickBillingConfigureOrderButton();
//        app.getOrderTabService().checkBillingConfigureOrderButton();
//        app.getSelectorService().assertSuccessMessage("Bill configuration are saved!");
//    }
//
//    @Priority(8)
//    @Test(priority = 8)
//    public void submit_Order_Test() throws InterruptedException {
//        log.info("");
//        reportLog("****** Click submit button if there are no 'Warning!' Messages ******");
//        if(driver.findElements(By.xpath("//*[contains(text(),'Warning!')]")).size()>0
//                && !driver.findElement(By.id("rx_order_submit")).isEnabled()) {
//            reportLog("Contains 'Warning!' Notifications");
//        }else {
//            app.getOrderTabService().clickSubmitButton(new OrderTabData().OrderId(orderId));
//        }
//    }


}
