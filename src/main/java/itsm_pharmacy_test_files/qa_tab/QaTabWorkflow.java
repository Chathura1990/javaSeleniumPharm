package itsm_pharmacy_test_files.qa_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.QaTabData;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.MILLISEC2000;
import static itsm_pharmacy_test_files.pharmacy_complete_workflow.PharmCompleteWorkflow.*;
import static java.lang.Thread.sleep;

public class QaTabWorkflow {

    private QaTabData qaTabData = new QaTabData();

    public void enter_RxId_And_Press_Enter(ApplicationManager app) throws InterruptedException {
        log.info("");
        app.getNavigationServicePharmMain().goToQATab();
        sleep(MILLISEC2000);
        app.getQaTabService().enterRxIdAndPressEnter(qaTabData.rxid(RxId));
    }

    public void approve_The_Order(ApplicationManager app) throws InterruptedException {
        log.info("");
        app.getQaTabService().clickOrderApproveBtn();
        app.getQaTabService().enterCommentForDur(qaTabData.durComment("Automation test"));
        app.getQaTabService().clickDurApproveBtn();
    }


}
