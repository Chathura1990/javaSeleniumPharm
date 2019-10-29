package itsm_pharmacy_test_files.order_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.EntryTabData;
import itsm_pharmacy_base_files.app_manager.model_data.OrderTabData;
import org.testng.Assert;

import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;

public class OrderTabWorkflow {

    public static int OrderNumberById;

    private EntryTabData entryTabData = new EntryTabData();

    public void checkOrderNumberAndDate(ApplicationManager app) throws ParseException, InterruptedException {
        log.info("");
        reportLog("**** Get Order Number & Check Current Order date is Future date or not ****");
//        app.getSelectorService().closeNotification();
        OrderNumberById = app.getOrderTabService().getOrderId();
        Assert.assertTrue(app.getSelectorService().checkDateIsFutureDate(app.getOrderTabService().getOrderDate(), "Order"));
    }

    public void verifyQtyPrescribedAndDaySupply(ApplicationManager app){
        log.info("");
        reportLog("**** Verify if Qty_Prescribed and Day_Supply Data According to Rx Data or Not ****");
        app.getOrderTabService().checkQtyDispAndDaysSup(entryTabData.qtyPrescribed(QTY_PRESCRIBED).daySupply(DAYS_SUPPLY));
    }

    public void selectNDCFromWidget(ApplicationManager app) throws InterruptedException {
        log.info("");
        reportLog("**** Select a NDC number from [Select NDC] widget ****");
        app.getOrderTabService().selectNDC();
    }

    public void checkDURNotifications(ApplicationManager app){
        log.info("");
        reportLog("**** Check D.U.R drug interactions ****");
        app.getOrderTabService().checkDrugInteractionReviews();
    }

    public void clickSubmitButtonToContinue(ApplicationManager app) throws InterruptedException {
        log.info("");
        reportLog("**** Click Submit Button ****");
        sleep(LONG_WAIT*2);
        app.getSelectorService().scrollUpOrDown(450);
        app.getOrderTabService().clickSubmitButton(new OrderTabData().OrderId(OrderNumberById));
    }
}
