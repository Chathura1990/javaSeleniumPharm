package itsm_pharmacy_test_files.verification_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;

public class VerificationTabWorkflow {

    public void getProfitAndPtRespFromOrder(ApplicationManager app) throws InterruptedException {
        log.info("");
        app.getVerificationTabService().getOrderProfitAndPtResp();
    }

    public void clickAcceptButtonTest(ApplicationManager app){
        log.info("");
        app.getVerificationTabService().clickAcceptButton();
        app.getSelectorService().assertSuccessMessage("Verified successfully.");
    }
}
