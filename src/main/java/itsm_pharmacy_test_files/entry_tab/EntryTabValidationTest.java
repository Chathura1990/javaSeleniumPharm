package itsm_pharmacy_test_files.entry_tab;

import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static org.openqa.selenium.By.xpath;

public class EntryTabValidationTest extends TestBase {

    private By PatientName = xpath("//*[@id='single_rx_type_patName']/p/a/span");
    private By BriefPatInfo = xpath("//*[contains(text(), 'Brief Patient Info')]");
    private By PrescriberName = xpath("//*[@id='singleTypingForm']/form/div[1]/div[2]/div[1]/p");
    private By BriefPrescInfo = xpath("//*[contains(text(), 'Brief Prescriber Info')]");
    private By EntryTabImage = xpath("//*[@src='/bundles/internalsitepharmacy/images/entry.png']");

    @Priority(1)
    @Test(priority = 1)
    public void select_valid_Rx_Test() throws InterruptedException {
        reportLog("**** Check Availability of Entry Tab Image and Change Bundle to Single ****");
        app.getEntryTabService().selectValidRxFromQueues(app);
        Assert.assertTrue(app.getSelectorService().checkImageIsAvailableOrNot(EntryTabImage, "'images/entry.png'", "Entry tab"));
    }

    @Priority(2)
    @Test(priority = 2)
    public void check_Availability_Of_EntryTab_Image_Test() throws InterruptedException {
        log.info("");
        reportLog("**** Open Brief info section of Patient and Physician by Hovering cursor *****");
        app.getEntryTabService().selectValidRxFromQueues(app);
        app.getEntryTabService().changeBundleToSingle();
        app.getEntryTabService().hoverToCheckBriefInfo(PatientName, BriefPatInfo);
        app.getEntryTabService().hoverToCheckBriefInfo(PrescriberName, BriefPrescInfo);
    }

    @Priority(3)
    @Test(priority = 3)
    public void brief_Patient_Physician_Info_Hover_Test() throws InterruptedException {
        log.info("");
        reportLog("**** Check dispense as written and available rx origins *****");
        app.getEntryTabService().selectValidRxFromQueues(app);
        app.getEntryTabService().changeBundleToSingle();
        app.getEntryTabService().checkDispenseAsWritten();
        reportLog("");
        app.getEntryTabService().checkAvailableRxOrigins();
    }

    @Priority(4)
//    @Test(priority = 4)
    public void check_DAW_And_RxOrigins_Test() throws InterruptedException {
        log.info("");
        reportLog("**** Assert Error Message -> 'Please, check your input in highlighted fields' *****");
        app.getEntryTabService().selectValidRxFromQueues(app);
        app.getEntryTabService().changeBundleToSingle();
        app.getEntryTabService().clearRequiredFields();
        app.getEntryTabService().clickSubmitButton();
        app.getEntryTabService().assertRequiredFieldsMessage();
    }
}
