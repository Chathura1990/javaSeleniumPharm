package itsm_pharmacy_test_files.filling_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.FillingTabData;
import org.openqa.selenium.By;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_test_files.pharmacy_complete_workflow.PharmCompleteWorkflow.*;
import static org.openqa.selenium.By.xpath;

public class FillingTabWorkflow {

    private By SuccessMessage = xpath("//*[contains(text(),'Verified successfully.')]");

    private FillingTabData fillingTabData = new FillingTabData();

    public void enterRxIdAndClickSearchBtn(ApplicationManager app) {
        log.info("");
        app.getNavigationServicePharmMain().goToFillingTab();
        app.getFillingTabService().checkFillingTabImage();
        app.getFillingTabService().clickSearchButton();
        app.getFillingTabService().enterRxId(fillingTabData.rxid(RxId));
        app.getFillingTabService().clickSearchButton();
    }

    public void printLeaflet(ApplicationManager app) throws InterruptedException {
        log.info("");
        app.getSelectorService().invisibilityOfElementLocatedByLocator(SuccessMessage,10);
//        app.getFillingTabService().clickPdfCheckbox();
//        app.getFillingTabService().clickPrintLeafletButton();
//        app.getSelectorService().assertSuccessMessage("Leaflets has been printed");
        String originalPage = app.getFillingTabService().clickPdfCheckbox();
        app.getFillingTabService().clickDownloadBtn(originalPage);
    }


}
