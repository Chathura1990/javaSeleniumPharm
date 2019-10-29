package itsm_pharmacy_test_files.verification_tab;

import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.testng.annotations.Test;

import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;

public class VerificationTabValidationTest extends TestBase {

    @Priority(1)
    @Test(priority = 1)
    public void select_A_Single_Template_Test() throws InterruptedException {
        reportLog("***** Select Single Template from Queues Tab ******");
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToQueuesTab();
        app.getNavigationServiceQueuesTabs().goToVerify();
        sleep(LONG_WAIT);
        app.getNavigationServiceQueuesTabs().selectSingleTemplateOrderFromQueues();
    }

    @Priority(2)
    @Test(priority = 2)
    public void check_VerificationTab_Image_And_Select_Order_Test() {
        log.info("");
        reportLog("***** Check availability of VerificationTab Image and Select an Order from Dropdown Container ******");
        app.getVerificationTabService().checkVerificationTabImage();
        app.getVerificationTabService().selectOrderFromDropDownContainer();
    }

    @Priority(3)
    @Test(priority = 3)
    public void checkOrderDateAndSelectShippingMethod() throws ParseException {
        log.info("");
        reportLog("***** Check Order date and Click Shipping Method 'Home Delivery' **********");
        app.getVerificationTabService().checkOrderDateIsFutureDateOrNot();
        app.getVerificationTabService().selectShippingMethod(1);
        app.getSelectorService().assertSuccessMessage("Shipping method saved");
    }

    @Priority(4)
    @Test(priority = 4)
    public void selectAcceptButton(){
        log.info("");
        reportLog("***** Click all the available Accept buttons ********");
        app.getVerificationTabService().clickAcceptButton();
    }
}
