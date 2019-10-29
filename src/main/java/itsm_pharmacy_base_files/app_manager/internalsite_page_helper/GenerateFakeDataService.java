package itsm_pharmacy_base_files.app_manager.internalsite_page_helper;

import itsm_pharmacy_base_files.app_manager.model_data.PharmacyFakeData;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.framework.mainClass.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.*;
import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

public class GenerateFakeDataService extends SelectorService {

    private By fakePatient = id("patients_generating_form_count");
    private By fakeRx = id("rxes_generating_form_count");
    private By successMessage = xpath("//div[@class='ui-pnotify-text']");

    public GenerateFakeDataService(WebDriver driver){
        super(driver);
    }

    public void goToGenerateFakePatientsTab(){
        driver.get(Parameters.instance().getUrl()+"/app.php/pharmacy/fake_data_generating/main#patients");
        reportLog("Opened Fake patient data generating page");
    }

    public void goToGenerateFakeRxesTab(){
        driver.get(Parameters.instance().getUrl()+"/app.php/pharmacy/fake_data_generating/main#rxes");
        reportLog("Opened Fake Rx generating page");
    }

    public void createFakePatient(PharmacyFakeData fakeData){
        implicit_Wait(SECONDS20);
        click(fakePatient, "[count] field");
        typeAndPressEnter(fakePatient, String.valueOf(fakeData.getPatients()));
        reportLog("Created new patient");

        String SuccessMessage = visibilityOfElementLocatedBylocator(successMessage, SECONDS20).getText();
        reportLog(":"+SuccessMessage+":");
    }

    public void createFakeRxes(PharmacyFakeData fakeData) throws InterruptedException {
        driver.navigate().refresh();
        sleep(LONG_WAIT);
        click(fakeRx, "[count] field");
        typeAndPressEnter(fakeRx,String.valueOf(fakeData.getNumberOfRxes()));
        implicit_Wait(SECONDS20);
        visibilityOfElementLocatedBylocator(fakeRx, SECONDS20);
        WebElement message = visibilityOfElementLocatedBylocator(successMessage, SECONDS20);
        getSuccessMessageCommon();
    }
}
