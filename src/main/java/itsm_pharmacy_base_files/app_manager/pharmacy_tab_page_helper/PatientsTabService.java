package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class PatientsTabService extends SelectorService {

    private By PatientId = id("pat_prof_id");
    private By PatientName = id("patient_profile_widget_form_first");
    private By PatientMiddleName = id("patient_profile_widget_form_middle");
    private By PatientLastName = id("patient_profile_widget_form_last");
    private By PatientSSN = id("patient_profile_widget_form_ssn");
    private By DateOfBirth = id("patient_profile_widget_form_birthday");
    private By PatGender = id("patient_profile_widget_form_sex");
    private By PatPhone = id("patient_profile_widget_form_phone1");
    private By PatEmail = id("patient_profile_widget_form_email");
    private By PatientAddress1 = id("patient_profile_widget_form_address1");
    private By PatientAddress2 = id("patient_profile_widget_form_address2");
    private By PatientCity = id("patient_profile_widget_form_city");
    private By PatientState = id("patient_profile_widget_form_state");
    private By PatientZipCode = id("patient_profile_widget_form_zipcode");
    private By DateOfDeath = id("patient_profile_widget_form_dateDeath");
    //Add Physician
    private By PhysSearchBtn = id("patient_profile_widget_form_physicianLookup");
    private By PhysId = cssSelector("input[name='wdg_phy_id']");
    private By SearchResultBtn = xpath("//*[@id='_Modal_1']/div/div/div[2]/form/div[2]/button[1]");
    private By PhysResult = xpath("//table[@id='typing-phy-search-result-table']/tbody/tr");
    //Patient Allergies Widget
    private By NoKnownAllergies = id("is_patient_allergies_form_noKnownAllergies");
    private By AddAllergy = xpath("//*[@id='pat-allergy-body-container']/div/form/div[4]/div[1]/button");
    private By PatientAllergy1 = xpath("//*[@id='pat-allergy-body-container']/div/form/div[3]/div/div[1]/span/span[1]/span");
    private By PatientAllergy2 = xpath("//*[@id='pat-allergy-body-container']/div/form/div[3]/div[2]/div[1]/span/span[1]/span");
    private By AllergyInputField = xpath("//span/span/span[1]/input[@class='select2-search__field']");
    private By AllergySaveBtn = xpath("//*[@id='pat-allergy-body-container']/div/form/div[4]/div[2]/button");
    //Patient Diseases and Health Condition Widget
    private By AddDiseases = xpath("//*[@id='pat-diseases-container']/div/div[2]/span/span[1]/span/ul/li/input");
    private By SelectDisease = xpath("//ul[@class='select2-results__options']/li[1]/div");
    //Patient Insurance Widget
    private By AddNewBtn = xpath("//div[@id='pat-insurance-container']/div/table/tbody/tr/td/button[2]");
    private By InsuranceBin = xpath("//input[@id='patient_rx_insurance_form_type_bin']");
    private By SelectBinPCN = xpath("//*[contains(text(),'"+BIN_RESULT+"')]");
    private By InsuranceType = id("patient_rx_insurance_form_type_type_0");
    private By CardholderId = id("patient_rx_insurance_form_type_cardholderId");
    private By SaveInsuranceBtn = xpath("//button[contains(text(),'Save')][@id='save_rx_insurance_history']");
    private By ModalCloseBtn = xpath("//div[@id='_Modal_0']/div/div/div/button");
    private By SuccessMessage = xpath("//div[@class='ui-pnotify-text']");
    private By PatientProfileSaveBtn = id("patient_profile_widget_form_saveBtn");
    //Patient Current Medication Widget
    private By AddMedicationBtn = id("add-medication");
    private By SupplierField = id("select2-internalsite_widget_patient_medications_form_ndc_select-container");
    private By MedicationInputField = xpath("//span/span/span[1]/input[@class='select2-search__field'][@type='search']");
    private By SelectMedication = xpath("//*[@id='select2-internalsite_widget_patient_medications_form_ndc_select-results']/li[2]/div");
    private By TreatmentStartedDate = id("internalsite_widget_patient_medications_form_type_lengthOfTreatment");
    private By CommentWidget = id("internalsite_widget_patient_medications_form_type_comment");
    private By SubmitBtn = id("internalsite_widget_patient_medications_form_type_submit");
    private By ReturnBtn = id("return_button");
    //Eligibility widget
    private By RunButton = id("wdg_run_elg_now");
    private By PrimaryElig = xpath("//span[@id='run-elg']/ul/li[1]/a");
    private By EligibilityStatus = xpath("//div[@id='eligibility-content-container']/div/table/tbody/tr[1]/td[2]");
    private By EligibilityType = xpath("//*[@id='eligibility-content-container']/div/table/tbody/tr[1]/td[3]");
    private By DeductAmount = xpath("//*[@id='eligibility-content-container']/div/table/tbody/tr[1]/td[4]");
    //Patient Comments widget
    private By AddNewButton = xpath("//button[@name='wdg_btn_add_comment' and @class='btn btn-primary wdg_btn_add_comment']");
    private By CommentInputSection = id("patient_comment_widget_form_comments");
    private By SaveButton = id("patient_comment_widget_form_save");
    private By CommentResult = xpath("//*[@id='tabs-1']/tbody/tr[3]/td[1]");
    private By CommentDate = xpath("//*[@id='tabs-1']/tbody/tr[3]/td[2]");
    private By CommentUser = xpath("//*[@id='tabs-1']/tbody/tr[3]/td[3]");
    //Product Interest Widget
    private By VerifyEnrollmentBtn = xpath("//*[@id='enrollment-verification-enrollment-container']/div/button[1]");
    private By EnrollmentsHistoryBtn = xpath("//*[@id='enrollment-verification-enrollment-container']/div/button[2]");
    private By SubmitEnrollmentBtn = xpath("//*[@id='enrollment-verification-enrollment-container']/div/button[3]");
    //Verify Enrollment window
    private By SourceCampaign = xpath("//*[@id='wdg_enrollment']/div/div[1]/div[2]/select");
    private By EnrollmentCampaign = xpath("//*[@id='wdg_enrollment']/div/div[2]/div[2]/select");
    private By PatientInput = xpath("//*[@id='wdg_enrollment']/div/div[3]/div[2]/select");
    private By Location = xpath("//*[@id='wdg_enrollment']/div/div[5]/div[2]/select");
    private By CancelBtn = id("new_enrollment_close_btn");
    //Enrollments History window
    private By EntrollmentHist = xpath("//*[@id='_Modal_0']/div/div/div[1]/h4");
    private By CloseBtn = xpath("//*[@id='_Modal_0']/div/div/div[1]/button/span[1]");
    //Submit Enrollment
    private By location2 = xpath("//*[@id='_Modal_0']/div/div/div[2]/div/form/div[1]/div[2]/select");
    private By CancelBtn2 = id("submit_enrollment_close_btn");

    public PatientsTabService(WebDriver driver) {
        super(driver);
    }

    public int getPatientId(){
        int patId = Integer.valueOf(driver.findElement(PatientId).getText());
        reportLog("Patient id: ["+ patId +"]");
        return patId;
    }

    public int getPatientAge(PatientsTabData patientsTabData) throws ParseException {
        if(getText(DateOfBirth).isEmpty()){
            type(DateOfBirth,patientsTabData.getPatDob());
        }else
            checkDateIsFutureDate(driver.findElement(DateOfBirth).getText(), "Patient Birth");
            int patientAge = calculateAge(DateOfBirth);
            reportLog("Patient Age: ["+ patientAge +"]");
            return patientAge;
    }

    private void fillPatientInfoForm(PatientsTabData patientsTabData){
        type(PatientName,patientsTabData.getPatName());
        type(PatientMiddleName,patientsTabData.getPatMiddleName());
        type(PatientLastName,patientsTabData.getPatLastName());
        type(PatientSSN,String.valueOf(patientsTabData.getPatSocialSecurityNumber()));
        selectAnOptionFromDropdown(PatGender,patientsTabData.getSelectOption());
        type(PatPhone,String.valueOf(patientsTabData.getPatPhoneNumber()));
        type(PatEmail,patientsTabData.getPatEmail());
    }

    public void commonPatientInfo() throws InterruptedException {
        int PatientGenderOption = 2; //Patient's gender dropdown 2nd option is "Male".
        fillPatientInfoForm(new PatientsTabData()
                .patName(RandomStringUtils.randomAlphabetic(3,6))
                .patMiddleName(RandomStringUtils.randomAlphabetic(3,5))
                .patLastName(RandomStringUtils.randomAlphabetic(6))
                .patSocialSecurityNumber("1"+ RandomStringUtils.randomNumeric(8))
                .selectOption(PatientGenderOption)
                .patPhoneNumber("1"+RandomStringUtils.randomNumeric(9))
                .patEmail(RandomStringUtils.randomAlphabetic(7) + "@email.com"));
        changePatientAddress(new PatientsTabData()
                .addressLine1("8788 "+RandomStringUtils.randomAlphabetic(8))
                .city(RandomStringUtils.randomAlphabetic(5))
                .state(PATIENT_STATE)
                .zipCode(Integer.parseInt("1"+ RandomStringUtils.randomNumeric(8))));
        scrollUpOrDown(200);
        clickSaveChangesButton();
    }

    public void addPhysician(PatientsTabData p) {
        click(PhysSearchBtn,"[Physician Search] Button");
        type(PhysId, p.getPatPhysId());
        click(SearchResultBtn, "[Search] Button");
        click(PhysResult, "First Result");
    }

    public void changePatientAddress(PatientsTabData patientsTabData){
        type(PatientAddress1, patientsTabData.getAddressLine1());
        driver.findElement(PatientAddress2).clear();
        type(PatientCity, patientsTabData.getCity());
        Select state = new Select(driver.findElement(PatientState));
        state.selectByValue(patientsTabData.getState());
        type(PatientZipCode, String.valueOf(patientsTabData.getZipCode()));
        clearField(DateOfDeath);
    }

    public void addAllergies(PatientsTabData patientsTabData) throws InterruptedException {
        sleep(MILLISEC2000);
        if(driver.findElement(NoKnownAllergies).isSelected()) {
            click(NoKnownAllergies, "No Known Allergy Checkbox");
        }
        click(AddAllergy,"Add New Allergy Button");
        //Add first allergy
        driver.findElement(PatientAllergy1).click();
        sleep(MILLISEC1000);
        type(AllergyInputField, patientsTabData.getAllergy());
        sleep(MILLISEC2000);
        driver.findElement(AllergyInputField).sendKeys(Keys.RETURN);
        sleep(MILLISEC1000);
        click(AllergySaveBtn, "Save Button");
    }

    public void addDisease(PatientsTabData patientsTabData)throws InterruptedException {
        sleep(LONG_WAIT);
        type(AddDiseases, patientsTabData.getDisease());
        sleep(MILLISEC2000 *3);
        if(driver.findElements(SelectDisease).size()>0) {
            click(SelectDisease, "Disease");
        }else {
            reportLog("Unable to add recommended disease.");
        }
    }

    public void clickAddInsuranceBtn(){
        click(AddNewBtn,"[+Add New] button");
    }

    public void addInsurance(PatientsTabData patientsTabData)throws InterruptedException {
        type(InsuranceBin,String.valueOf(patientsTabData.getBinWithSixIntValues()));
        click(SelectBinPCN, "Bin & PCN");
        sleep(MILLISEC1000);
        click(InsuranceType, "Insurance Type: [Primary]");
        sleep(MILLISEC1000 *2);
        type(CardholderId,String.valueOf(patientsTabData.getCardholderId()));
        typeAndPressEnter(CardholderId,String.valueOf(patientsTabData.getCardholderId()));
        sleep(LONG_WAIT);
    }

    public void addCurrentMedication(PatientsTabData patientsTabData){
        click(AddMedicationBtn,"[Add Medication] button");
        click(SupplierField,"[Supplier] field");
        type(MedicationInputField, patientsTabData.getMedication());
        visibilityOfElementLocatedBylocator(SelectMedication, SECONDS20).click();
        driver.findElement(TreatmentStartedDate).click();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
        Date date = new Date();
        String date1= dateFormat.format(date);
        type(TreatmentStartedDate,String.valueOf(date1));
        driver.findElement(CommentWidget).click();
        click(SubmitBtn,"[Submit] button");
    }

    public void clickRunButton() {
        implicit_Wait(SECONDS20);
        click(RunButton, "[Run] button");
    }

    public void clickPrimaryButton() {
        implicit_Wait(SECONDS20);
        click(PrimaryElig, "[Primary] button");
    }

    public void getEligibilityStatus() {
        reportLog("Eligibility Status: "+ getText(EligibilityStatus));
        reportLog("Eligibility Type: "+ getText(EligibilityType).replaceAll("\\s", " "));
        reportLog("Eligibility Deduct Amount: "+ getText(DeductAmount));
    }

    public void clickSaveChangesButton(){
        click(PatientProfileSaveBtn, "[Save Changes] button");
        assertSuccessMessage("The patient has been saved!");
    }

    public void clickReturnToEntryTabButton(){
        click(ReturnBtn, "[Return] button");//Click green color Return button in order to go back to Entry tab
    }

    public void clickAddNewBtn() {
        click(AddNewButton,"[Add New] button");
    }


    public void EnterPatientComment(PatientsTabData p) {
        pageLoad_Timeout(PAGE_LOAD_TIMEOUT);
        if(isELementPresent(CommentInputSection)) {
            type(CommentInputSection, p.getComment());
        }
    }

    public void clickSaveBtn() {
        click(SaveButton,"[Save] button");
    }

    public void checkCommentDateAndUsername() throws ParseException {
        if(getText(CommentResult).equals("Automation Test") && getText(CommentUser).equals(VALID_USERNAME)){
            reportLog("Added '"+getText(CommentResult)+ "' comment by the User: '"+getText(CommentUser)+"'");
        }else
        {
            reportLog("Comment: "+getText(CommentResult) + ", User Id:" + getText(CommentUser));
        }
        checkDate(getText(CommentDate));
    }

    public void clickVerifyEnrollmentBtn() throws InterruptedException {
        scrollUpOrDown(400);
        click(VerifyEnrollmentBtn, "[Verify Enrollment] button");
        randomWait();
    }

    public void productInterestVerifyEnrollmentWindow(){
        reportLog("Source Campaign dropdown is Available: " + isELementPresent(SourceCampaign));
        reportLog("Enrollment Campaign dropdown is Available: " + isELementPresent(EnrollmentCampaign));
        reportLog("Patient dropdown is Available: " + isELementPresent(PatientInput));
        reportLog("Location dropdown is Available: " + isELementPresent(Location));
        visibilityOfElementLocatedBylocator(CancelBtn, SECONDS20).click();
    }

    public void clickEnrollmentsHistoryBtn(){
        randomWait();
        click(EnrollmentsHistoryBtn, "[Enrollments History] button");
        randomWait();
    }

    public void productInterestEnrollmentsHistoryWindow(){
        reportLog("Enrollment history Element is Available: " + isELementPresent(EntrollmentHist));
        visibilityOfElementLocatedBylocator(CloseBtn, SECONDS20).click();
    }

    public void clickSubmitEnrollmentBtn(){
        randomWait();
        click(SubmitEnrollmentBtn,"[Submit Enrollment] button");
        randomWait();
    }

    public void productInterestSubmitEnrollmentWindow(){
        randomWait();
        reportLog("Location dropdown is Available: " + isELementPresent(location2));
        visibilityOfElementLocatedBylocator(CancelBtn2, SECONDS20).click();
    }
}
