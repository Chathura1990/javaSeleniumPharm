package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.PatientsTabData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.framework.mainClass.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;
import static org.testng.Assert.*;

public class PosTabService extends SelectorService {

    public PosTabService(WebDriver driver) {
        super(driver);
    }

    //Document Upload Widget
    private By SubmitBtn = cssSelector("button[class='btn btn-success submit-pos']");
    private By SelectPDF = id("document_upload_form_chose");
    private By imageTag = id("doc_img");
    //New Patient Search Widget
    private By NewPatientBtn = cssSelector("button[name='wdg_new_patient']");
    private By PatIdField = name("wdg_pat_id");
    private By FirstName = name("wdg_pat_first");
    private By LastName = name("wdg_pat_last");
    private By SearchButton = cssSelector("button[name='wdg_search']");
    private By PatResult = xpath("//*[@id='point-of-sale-pat-search-result-container']/table/tbody[2]/tr[1]/td[2]");
    //Patient Info Widget
    private By PatientId = id("point-of-sale-pat_prof_id");
    //Patient Insurance Widget
    private By AddNewButton = xpath("//div[@id='point-of-sale-pat-insurance-container']/div[1]/table/tbody/tr[1]/td/button[2]");
    //Patient Allergies widget
    private By NoKnownAllergies = id("is_patient_allergies_form_noKnownAllergies");
    //Rx
    private By DeleteRx = id("single_rx_type_remove");
    private By OkBtn = xpath("//*[@id='_Modal_0']/div/div/div[3]/button[1]");

    public void attachPdfToPatProfile(PatientsTabData patient) {
        attach(SelectPDF, patient.getPhoto());
        reportLog("Attached photo using "+SelectPDF+" element");
    }

    public boolean checkAvailabilityOfDoc(){
        String src = visibilityOfElementLocatedBylocator(imageTag, SECONDS20).getAttribute("src");
        return src.startsWith("data:image/png;");
    }

    public boolean checkAvailabilityOfPat(){
        return !getText(PatientId).isEmpty();
    }

    public void clickAddNewPatientBtn(){
        click(NewPatientBtn,"[New Patient] button");
    }

    public void fillPatInfo(PatientsTabData patInfo) throws InterruptedException {
        type(PatIdField, String.valueOf(patInfo.getPatientId()));
        type(FirstName, patInfo.getPatName());
        type(LastName, patInfo.getPatLastName());
        click(SearchButton, "{Search} button");
        sleep(LONG_WAIT);
    }

    public void selectPatientFromSearchResults(){
        click(PatResult, getText(PatResult));
    }

    public void clickAddInsuranceBtn() throws InterruptedException {
        scrollUpOrDown(900);
        click(AddNewButton,"[+Add New] button");
    }

    public void clickNoAllergy() throws InterruptedException {
        sleep(MILLISEC2000);
        if(!driver.findElement(NoKnownAllergies).isSelected()) {
            click(NoKnownAllergies, "No Known Allergy Checkbox");
        }
    }

    public void clickSubmitBtn()throws InterruptedException {
        scrollUpOrDown(100);
        click(SubmitBtn, "[Submit] button");
        sleep(LONG_WAIT);
        if(driver.getCurrentUrl().endsWith("/app.php/pharmacy/main#typing")){
            reportLog("Rx was submitted properly.");
            getSuccessMessageCommon();
            assertEquals(driver.getCurrentUrl(), Parameters.instance().getUrl()+"/app.php/pharmacy/main#typing");
        }else if(driver.getCurrentUrl().endsWith("/app.php/pharmacy/main#point-of-sale")){
            reportLog("Something went wrong.");
            reportLog("Current URL is: "+driver.getCurrentUrl());
        }
    }

    public void deleteSubmittedRx() {
//        invisibilityOfElementLocatedByLocator(SubmitBtn, SECONDS20);
        click(DeleteRx, "[Delete Rx!] button");
        visibilityOfElementLocatedBylocator(OkBtn, SECONDS20).click();
    }

    public void clickPatientSearchBtn() {
        click(SearchButton,"[Search] button");
    }

//    //Every day, this method removes the first line from the CSV file after the test is completed.
//    public void deleteLineFromCsv() throws IOException {
//        List<PatientsTabData> list = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(new FileReader(new File(PATHTOCSVFILE)));
//        String line = reader.readLine();
//        while(line != null) {
//            String[] split = line.split(",");
//            list.add(new PatientsTabData().patientId(Integer.parseInt(split[0])).patName(split[1]).patLastName(split[2]));
//            line = reader.readLine();
//        }
//        reportLog(list.get(0).getPatientId()+", "
//                +list.get(0).getPatName()+ ", "
//                +list.get(0).getPatLastName()+" has been removed from the CSV file.");
//        list.remove(0);
//        CSVWriter writer = new CSVWriter(new FileWriter(new File(PATHTOCSVFILE)),
//                ',',CSVWriter.NO_QUOTE_CHARACTER);
//        List<String[]> newList = list.stream().map(item -> new String[]
//                {String.valueOf(item.getPatientId()), item.getPatName(), item.getPatLastName()})
//                .collect(Collectors.toList());
//        writer.writeAll(newList);
//        writer.close();
//    }

}
