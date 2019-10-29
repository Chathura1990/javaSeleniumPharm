package itsm_pharmacy_test_files.pos_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.testng.annotations.Test;

import java.io.File;
import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.testng.Assert.*;

public class PosTabValidationTest extends TestBase {

    private PatientsTabData p = new PatientsTabData();
    private SelectorService s = new SelectorService(ApplicationManager.driver);
//    @DataProvider
//    public Iterator<Object[]> validDataFromCsv() throws IOException {
//        List<Object[]> list = new ArrayList<>();
//        File path = new File(PATHTOCSVFILE);
//        BufferedReader reader = new BufferedReader(new FileReader(new File(String.valueOf(path))));
//        String line = reader.readLine();
//        String[] split = line.split(",");
//        list.add(new Object[] {new PatientsTabData().patientId(Integer.parseInt(split[0])).patName(split[1]).patLastName(split[2])});
//        return list.iterator();
//    }
    @Priority(1)
    @Test(priority = 1)
    public void testErrorNotifications() throws InterruptedException {
        reportLog("****** Check 'Check parameters to submit' Error notification *****");
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToPosTab();
        app.getPosTabService().clickSubmitBtn();
        app.getSelectorService().assertSuccessMessage("Check parameters to submit. Doc Type, File or Patient");
    }

    @Priority(2)
    @Test(priority = 2)
    public void testErrorNotifications2(){
        log.info("");
        reportLog("****** Check 'search criteria' Error notification *****");
        app.getNavigationServicePharmMain().goToPharmacyMainPage();
        app.getNavigationServicePharmMain().goToPosTab();
        app.getPosTabService().clickPatientSearchBtn();
        app.getSelectorService().assertSuccessMessage("You should provide search criteria.");
    }

    @Priority(3)
    @Test(priority = 3)
    public void attachPdf() throws InterruptedException {
        log.info("");
        reportLog("****** Upload pdf to 'Document Upload' widget *****");
        File image = new File(PATHTOPDFDOC);
        app.getSelectorService().implicit_Wait(SECONDS20);
        app.getPosTabService().attachPdfToPatProfile(p.photo(image));
        sleep(LONG_WAIT);
        assertTrue(app.getPosTabService().checkAvailabilityOfDoc());//Check whether the required document is loaded or not
    }

    @Priority(4)
    @Test(priority = 4)
    public void createPatient() throws InterruptedException, ParseException {
        log.info("");
        reportLog("****** Search Patient and Add Insurance *****");
        app.getPosTabService().clickAddNewPatientBtn();
        sleep(MILLISEC2000);
        app.getPatientsTabService().getPatientAge(p.patDob(s.randomNumb(1,12)+"/"
                +s.randomNumb(1,30)+"/"+"198"+s.randomNumb(1,9))); //Generating a random birthday
        app.getPatientsTabService().addPhysician(p.patPhysId(PHYS_ID)); //Adding Physician
        sleep(MILLISEC2000);
        app.getPatientsTabService().commonPatientInfo(); //filling all the required patient info fields
//        app.getSelectorService().closeNotification();
        assertTrue(app.getPosTabService().checkAvailabilityOfPat());//check whether the new patient has been created or not
    }
//
//    @Priority(5)
//    @Test(priority = 5)
//    public void addValidInsurance() throws InterruptedException {
//        log.info("");
//        reportLog("****** Add Insurance to newly created Patient ******");
//        app.getSelectorService().implicit_Wait(MILLISEC2000);
//        app.getPosTabService().clickAddInsuranceBtn();
//        app.getPatientsTabService().addInsurance(p.binWithSixIntValues(BIN_NUMBER).cardholderId(CARDHOLDER_ID));
//    }

    @Priority(5)
    @Test(priority = 5)
    public void clickSubmitBtn() throws InterruptedException {
        log.info("");
        reportLog("****** Click submit button and get success message");
        sleep(LONG_WAIT);
        app.getPosTabService().clickSubmitBtn();
        app.getPosTabService().deleteSubmittedRx();
    }



}
