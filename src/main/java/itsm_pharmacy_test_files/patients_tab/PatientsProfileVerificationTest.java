package itsm_pharmacy_test_files.patients_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.testng.annotations.Test;

import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;

public class PatientsProfileVerificationTest extends TestBase {

    private PatientsTabData p = new PatientsTabData();
    private SelectorService s = new SelectorService(ApplicationManager.driver);

    /*
    * Allergies, Diseases and Health Condition,
    * Patient Insurance widgets test cases are in EntryTabSingleRxWorkflow.java file.
    **/

    @Priority(1)
    @Test(priority = 1)
    public void go_To_Patients_Tab_And_Search_For_A_Patient_Test(){
        reportLog("***** Check availability of Patients tab image *****");
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToPatientsTab();
        app.getPatientsTabSearchService().checkPatientsTabImage();
    }

    @Priority(2)
    @Test(priority = 2)
    public void fill_Patient_Required_fields_Info_Test() throws InterruptedException, ParseException {
        log.info("");
        reportLog("***** Fill all the required fields and get Success message *****");
        selectPatientById();
        sleep(MILLISEC2000);
        app.getPatientsTabService().getPatientAge(p.patDob(s.randomNumb(1,12)+"/"
                +s.randomNumb(1,30)+"/"+"198"+s.randomNumb(1,9))); //Generating a random birthday
        app.getPatientsTabService().commonPatientInfo();
    }

    @Priority(3)
    @Test(priority = 3)
    public void patient_Eligibility_Check_Test() throws InterruptedException {
        log.info("");
        reportLog("***** Run 'Primary' Eligibility test *****");
        selectPatientById();
        sleep(MILLISEC2000);
        app.getSelectorService().scrollUpOrDown(70);
        app.getPatientsTabService().clickRunButton();
        app.getSelectorService().implicit_Wait(SECONDS20);
        app.getPatientsTabService().clickPrimaryButton();
        app.getPatientsTabService().getEligibilityStatus();
    }

//    @Priority(4)
//    @Test(priority = 4)
//    public void add_Patient_Comments_Test() throws ParseException {
//        log.info("");
//        reportLog("***** Add a comment to Patient comments widget and Assert it *****");
//        app.getPatientsTabService().clickAddNewBtn();
//        app.getPatientsTabService().EnterPatientComment(p.patComment("Automation Test"));
//        app.getPatientsTabService().clickSaveBtn();
//        app.getPatientsTabService().checkCommentDateAndUsername();
//    }

    @Priority(4)
    @Test(priority = 4)
    public void checkProductInterestWidgetTest() throws InterruptedException {
        log.info("");
        reportLog("***** Check availability of the contents of the product interest widget *****");
        selectPatientById();
        sleep(MILLISEC2000);
        app.getPatientsTabService().clickVerifyEnrollmentBtn();
        app.getPatientsTabService().productInterestVerifyEnrollmentWindow();
        app.getPatientsTabService().clickEnrollmentsHistoryBtn();
        app.getPatientsTabService().productInterestEnrollmentsHistoryWindow();
        app.getPatientsTabService().clickSubmitEnrollmentBtn();
        app.getPatientsTabService().productInterestSubmitEnrollmentWindow();
    }

    private void selectPatientById() {
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToPatientsTab();
        app.getPatientsTabSearchService().enterPatientId(p.patientId(AUTOMATION_TEST_PATIENT));
        app.getPatientsTabSearchService().clickSearchButton();
        app.getPatientsTabSearchService().clickSearchResult(p.patientId(AUTOMATION_TEST_PATIENT));
    }
}
