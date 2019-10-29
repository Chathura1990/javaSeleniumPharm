package itsm_pharmacy_test_files.entry_tab;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.EntryTabData;
import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;

import java.text.ParseException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static itsm_pharmacy_test_files.pharmacy_complete_workflow.PharmCompleteWorkflow.*;
import static java.lang.Thread.sleep;

public class EntryTabSingleRxWorkflow{

    private EntryTabData entryTabData = new EntryTabData();
    private PatientsTabData patientsTabData = new PatientsTabData();

    public void select_valid_Rx(ApplicationManager app) throws InterruptedException {
        reportLog("**** Select Single Rx from Queues -> Pending Rxes/No Allergy Info ****");
        app.getEntryTabService().selectValidRxFromQueues(app);
        sleep(LONG_WAIT);
        RxId = app.getEntryTabService().getRxId();
        app.getSelectorService().minimizeHistoryWidget();
    }

    public void click_Go_To_Next_Stage_Button(ApplicationManager app) {
        log.info("");
        reportLog("*** Click Go to Next Stage Button in order to continue the process ****");
        app.getEntryTabService().clickGoToNextStageButton();
    }

    public void change_Bundle_To_Single(ApplicationManager app) {
        log.info("");
        reportLog("**** Change Bundle to Single if Rx type is Bundle *****");
        app.getEntryTabService().changeBundleToSingle();
    }

    public void change_Patient_Info(ApplicationManager app) throws InterruptedException, ParseException {
        log.info("");
        reportLog("******* Prepare Patient Information in order to test D.U.R **********");
        app.getEntryTabService().goToPatientProfile();
        PatientId = app.getPatientsTabService().getPatientId();
        PatientAge = app.getPatientsTabService().getPatientAge(patientsTabData.patDob("01/04/1989"));
        sleep(MILLISEC1000);
        app.getPatientsTabService().changePatientAddress(patientsTabData
                .addressLine1(PATIENT_ADDRESS_LINE1)
                .city(PATIENT_CITY)
                .state(PATIENT_STATE)
                .zipCode(PATIENT_ZIP_CODE));
        app.getPatientsTabService().addAllergies(patientsTabData.allergy(DOXEPIN_ALLERGY));
        app.getSelectorService().getSuccessMessageCommon();
        app.getPatientsTabService().addDisease(patientsTabData.disease(GLAUCOMA));
        sleep(MILLISEC2000);
        app.getSelectorService().scrollUpOrDown(50);
        app.getPatientsTabService().clickAddInsuranceBtn();
        app.getPatientsTabService().addInsurance(patientsTabData.binWithSixIntValues(BIN_NUMBER)
                .cardholderId(CARDHOLDER_ID));
        app.getPatientsTabService().getSuccessMessageCommon();
//        app.getPatientsTabService().addCurrentMedication(patientsTabData.medication(PACERONE100));
        app.getSelectorService().scrollUpOrDown(400);
        sleep(LONG_WAIT);
        app.getPatientsTabService().clickSaveChangesButton();
        app.getPatientsTabService().clickReturnToEntryTabButton();
        }

    public void choose_Medication_from_dropdown(ApplicationManager app) {
        log.info("");
        reportLog("**** Choose Medication from Medication/DTS dropdown ********");
        app.getEntryTabService().chooseInsulinRadioButton(true);
        app.getEntryTabService().chooseICD10RadioButton(true);
        app.getEntryTabService().chooseMedicationFromDropdrown(entryTabData.medicationDts(DOXEPIN));
    }

    public void enter_QtyPrescribed_DaySupply_DateWritten(ApplicationManager app) {
        log.info("");
        reportLog("**** Enter Qty Prescribed, Day supply ********");
        app.getEntryTabService().fillEntryTabData(entryTabData.qtyPrescribed(QTY_PRESCRIBED).daySupply(DAYS_SUPPLY));
        app.getEntryTabService().EnterDateWritten();
    }

    public void click_Submit_Button_In_RxTab(ApplicationManager app) throws InterruptedException {
        log.info("");
        reportLog("******* Click Submit Button to Continue And Get Success Message **********");
        sleep(LONG_WAIT*2);//This will hold the test until some notifications are closed.
        app.getEntryTabService().clickSubmitButton();
        app.getSelectorService().assertSuccessMessage("Prescription typed successfully");
    }
}
