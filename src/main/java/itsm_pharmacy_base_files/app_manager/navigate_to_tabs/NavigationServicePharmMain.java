package itsm_pharmacy_base_files.app_manager.navigate_to_tabs;

import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.framework.mainClass.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.xpath;

public class NavigationServicePharmMain extends SelectorService {

    private By PosPage = xpath("//*[@id='pharmacy_tabs']/li[1]/a");
    private By EntryTabPage = xpath("//*[@id='pharmacy_tabs']/li[2]/a");
    private By OrderTabPage = xpath("//*[@id='pharmacy_tabs']/li[3]/a");
    private By VerificationTabPage = xpath("//*[@id='pharmacy_tabs']/li[4]/a");
    private By FillingTabPage = xpath("//*[@id='pharmacy_tabs']/li[5]/a");
    private By QaTabPage = xpath("//*[@id='pharmacy_tabs']/li[6]/a");
    private By ShippingTabPage = xpath("//*[@id='pharmacy_tabs']/li[7]/a");
    private By PatientsTabPage = xpath("//*[@id='pharmacy_tabs']/li[8]/a");
    private By QueuesTabPage = xpath("//*[@id='pharmacy_tabs']/li[9]/a");
    private By ManagementTabPage = xpath("//*[@id='pharmacy_tabs']/li[10]/a");
    private By ReturnsTabPage = xpath("//ul[@id='tabs']/li[4]/a");

    public NavigationServicePharmMain(WebDriver driver) {
        super(driver);
    }

    public void goToPharmacyMainPage() {
        driver.get(Parameters.instance().getUrl() + "/app.php/pharmacy/main");
    }

    public void goToPosTab() {
        click(PosPage, "POS Tab");
    }

    public void goToEntryTab() {
        click(EntryTabPage, "Entry Tab");
    }

    public void goToOrderTab() {
        click(OrderTabPage, "Order Tab");
    }

    public void goToVerificationTab() {
        click(VerificationTabPage, "Verification Tab");
    }

    public void goToFillingTab() {
        click(FillingTabPage, "Filling Tab");
    }

    public void goToQATab() {
        click(QaTabPage, "QA Tab");
    }

    public void goToShippingTab() {
        click(ShippingTabPage, "Shipping Tab");
    }

    public void goToPatientsTab() {
        click(PatientsTabPage, "Patients Tab");
    }

    public void goToQueuesTab() {
        click(QueuesTabPage, "Queues Tab");
    }

    public void goToManagementTab() {
        click(ManagementTabPage, "Management Tab");
    }

    public void goToReturnsTab() { click(ReturnsTabPage, "Returns Tab");
    }
}
