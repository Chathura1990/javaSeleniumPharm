package itsm_pharmacy_test_files.operations_warehouse;

import itsm_pharmacy_base_files.app_manager.ApplicationManager;
import itsm_pharmacy_base_files.app_manager.model_data.WarehouseData;

import java.io.IOException;

import static itsm_pharmacy_base_files.app_manager.ApplicationManager.log;
import static itsm_pharmacy_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_pharmacy_test_files.pharmacy_complete_workflow.PharmCompleteWorkflow.*;

public class FedexAndInventoryTrackingNew {

    private WarehouseData whd = new WarehouseData();

    /**
     * Tests will be run in the PharmCompleteWorkflow.java test class
     * @path pharmacy_complete_workflow\PharmCompleteWorkflow.java
     */
    public void checkAvailabilityOfShipment(ApplicationManager app) throws IOException {
        log.info("");
        reportLog("**** Check the status of the shipment ****");
        app.getNavigationServiceInternalsite().goToFedexInventoryTracking();
        app.getFedexInventoryService().clickFindPackageBtn();
        app.getFedexInventoryService().chooseMethodToFindPackage(whd.setIndex(0));//select dropdown option #0 is Patient's id
        app.getFedexInventoryService().EnterPackageDetails(whd.setPatientId(PatientId));
        app.getFedexInventoryService().clickFindBtn();
        TrackingNumb = app.getFedexInventoryService().checkTrackingNumber();//This method will check the fedex tracking number -> status
    }
}
