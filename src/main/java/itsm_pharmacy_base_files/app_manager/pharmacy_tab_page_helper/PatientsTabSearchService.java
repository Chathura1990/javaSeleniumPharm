package itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.*;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.*;

public class PatientsTabSearchService extends SelectorService {

    private By PatientsTabImage = xpath("//*[@src='/bundles/internalsitepharmacy/images/patients.png']");
    private By PatientSearchIdField = cssSelector("input[name='wdg_pat_id']");
    private By SearchButton = cssSelector("button[name='wdg_search'");

    public PatientsTabSearchService(WebDriver driver){
        super(driver);
    }

    public void checkPatientsTabImage(){
        checkImageIsAvailableOrNot(PatientsTabImage, "'images/patients.png'", "Patients Tab");
    }

    public void enterPatientId(PatientsTabData patientsTabData){
        type(PatientSearchIdField,String.valueOf(patientsTabData.getPatientId()));
    }

    public void clickSearchResult(PatientsTabData patientsTabData){
        click(xpath("//td[contains(., '"+patientsTabData.getPatientId()+"')]"),"[patient id] from the Search Result widget");
    }

    public void clickSearchButton(){
        click(SearchButton,"[Search] button");
    }
}
