package itsm_pharmacy_test_files.shipping_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.ShippingTabData;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.LONG_WAIT;
import static itsm_pharmacy_test_files.pharmacy_complete_workflow.PharmCompleteWorkflow.*;
import static java.lang.Thread.sleep;

public class ShippingTabWorkflow {

    private ShippingTabData shippingTabData = new ShippingTabData();

    public void enter_RxId_And_click_Save_Weight_Btn(ApplicationManager app) throws InterruptedException {
        log.info("");
        sleep(LONG_WAIT);
        app.getNavigationServicePharmMain().goToShippingTab();
        app.getShippingTabService().enterRxId(shippingTabData.rxid(RxId));
        app.getShippingTabService().clickSubmitBtn();
        app.getShippingTabService().clickSaveWeightBtn();
        app.getShippingTabService().getSuccessMessage();
    }

    public void check_OrderHist_And_Click_Save_OrderBtn(ApplicationManager app) throws InterruptedException {
        log.info("");
        app.getShippingTabService().clickOrderHistoryBtn();
        reportLog("Check whether Order Status is 'Approved' and Action is 'Approve' or not");
        app.getShippingTabService().getStatusOfTheOrder();
        sleep(LONG_WAIT);
        app.getShippingTabService().clickSaveOrderBtn();
    }

    public void select_Shipping_Method_And_Click_Finalize_Btn(ApplicationManager app) throws InterruptedException {
        log.info("");
        int providerUSPS = 6;
        app.getShippingTabService().selectShippingMethod(providerUSPS);
        app.getShippingTabService().clickSaveShippingMethodBtn();
        sleep(LONG_WAIT*2);
        app.getShippingTabService().clickFinalizedBtn();
        app.getShippingTabService().getSuccessMessage();
        sleep(LONG_WAIT);
    }
}
