package itsm_pharmacy_test_files.management_tab;

import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.test_base.TestBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.MILLISEC1000;
import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;

public class ReturnsTabVerificationTest extends TestBase {

    @DataProvider
    public Object[] getPatientIdFromJsonFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/main/resources/testData.json"));
        JSONObject jsonObject = (JSONObject) obj;
        return new PatientsTabData[]{new PatientsTabData().patientId(
                Integer.parseInt(String.valueOf(jsonObject.get("PatientId"))))
                .rxId(Integer.parseInt(String.valueOf(jsonObject.get("RxId"))))};
    }

    @Priority(1)
    @Test(priority = 1, dataProvider = "getPatientIdFromJsonFile")
    public void checkIfRestockedAndDiscardedFieldsAreDisablingOrNot(PatientsTabData pat) throws InterruptedException {
        reportLog("**** Verify that 'Restocked', 'Patient' and 'Discarded' fields are disabled for 'Not returned' items ****");
        int itemNumber = 0; //Item panel number is 0 since Rx is Single
        int notReturned = 2; //Select option of the "Not returned" Action is 2
        app.getReturnsTabService().goToReturnsTab(app);
        sleep(MILLISEC1000);
        if(pat != null) {
            app.getReturnsTabService().searchForAPatient(pat);
            app.getReturnsTabService().selectAction(itemNumber, notReturned);
            sleep(MILLISEC1000);
            assertEquals(app.getReturnsTabService().checkAvailabilityOfRestockedAndDiscardedFields(), 2,
                    "Should be two disabled input fields with the attribute 'readonly'");
        }else{
            reportLog("There are no data inside the Json file to retrieve");
        }
    }

    @Priority(2)
    @Test(priority = 2)
    public void checkNotReturnedAndMoveToShippingActionCombination() {
        log.info("");
        reportLog("**** Verify that action combination: 'Not returned' + 'Move to Shipping' is not allowed to return items  ****");
        int firstItem = 0; //Item panel id number is 0
        int secondItem = 1; //Item panel id number is 1
        int moveToShipping = 0; //'select' option of the "Move To Shipping" Action is 0
        int notReturned = 2; //'select' option of the "Not returned" Action is 2
        app.getReturnsTabService().goToReturnsQueueUsingQueuesTab(app);
        app.getNavigationServiceQueuesTabs().selectReturnOrderFromQueues();
        app.getReturnsTabService().selectAction(firstItem,moveToShipping);
        app.getReturnsTabService().selectAction(secondItem,notReturned);
        app.getReturnsTabService().clickSaveButton();
        app.getSelectorService().assertSuccessMessage("Please, check your highlighted fields.");
    }

    @Priority(3)
    @Test(priority = 3, dataProvider = "getPatientIdFromJsonFile")
    public void changeActionToDeleteAndCheckItInTheReturnsQueues(PatientsTabData pat) throws InterruptedException {
        log.info("");
        reportLog("Verify that orders marked as 'Deleted' is displayed in Returns queue after clicking [Save] button on Returns page");
        int itemNumber = 0; //Item panel number is 0 since Rx is Single
        int delete = 1; //'select' option of the "Delete" Action is 1
        int other = 9; //option number for the reason "Other" is 9
        app.getReturnsTabService().goToReturnsTab(app);
        sleep(MILLISEC1000);
        app.getReturnsTabService().searchForAPatient(pat);
        app.getReturnsTabService().selectAction(itemNumber, delete);
        app.getReturnsTabService().selectReason(itemNumber,other);
        String beforeRestockedValue = app.getReturnsTabService().enterRestockedValue(itemNumber);
        app.getReturnsTabService().clickSaveButton();
        if(app.getReturnsTabService().getSuccessMessage().equals("Return data have been successfully saved.")){
            app.getReturnsTabService().goToReturnsQueueUsingQueuesTab(app);
            app.getReturnsTabService().chooseAPatientUsingPatId(pat);
            String afterRestockedValue = app.getReturnsTabService().assertDataInReturnedItem(itemNumber);
            reportLog("Restocked value before delete the Rx: "+beforeRestockedValue);
            reportLog("Restocked value after delete the Rx: "+afterRestockedValue);
            assertEquals(afterRestockedValue,beforeRestockedValue,"Before and after restocked values should be equal. Orders marked as 'Deleted'" +
                    " should be displayed in Returns queue after clicking [Save] button on Returns page");

        }
        app.getReturnsTabService().goToReturnsQueueUsingQueuesTab(app);
        app.getReturnsTabService().deleteReturnItem(pat);
    }

}
