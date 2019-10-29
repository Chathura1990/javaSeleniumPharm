package itsm_pharmacy_test_files.general_tests;

import itsm_pharmacy_base_files.app_manager.model_data.PharmacyFakeData;
import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.testng.annotations.Test;

public class GenerateFakeDataForPharmacy extends TestBase {

    private PharmacyFakeData fakeData = new PharmacyFakeData();

//    @Priority(1)
//    @Test(priority = 1)
//    public void generate_Fake_Patients_For_Pharmacy_Test(){
//        app.getGenerateFakeDataService().goToGenerateFakePatientsTab();
//        app.getGenerateFakeDataService().createFakePatient(fakeData.patients(1));
//    }

    @Priority(2)
    @Test(priority = 2)
    public void generate_Fake_Rxes_For_Pharmacy_Test() throws InterruptedException {
//        log.info("");
        app.getGenerateFakeDataService().goToGenerateFakeRxesTab();
        app.getGenerateFakeDataService().createFakeRxes(fakeData.numberOfRxes(2));
    }

//    @Priority(3)
//    @Test(priority = 3, invocationCount = 10)
//    public void deleteFakeRxesFromTheQueues() throws InterruptedException {
//        app.getEntryTabService().selectValidRxFromQueues(app);
//        app.getEntryTabService().changeBundleToSingle();
//        app.getPosTabService().deleteSubmittedRx();
//    }
}
