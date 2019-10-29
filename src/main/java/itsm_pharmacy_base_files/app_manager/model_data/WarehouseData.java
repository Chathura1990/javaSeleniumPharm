package itsm_pharmacy_base_files.app_manager.model_data;

public class WarehouseData {

    private int index;//select option index
    private int patientId;

    public int getIndex() {
        return index;
    }

    public int getPatientId(){
        return patientId;
    }

    public WarehouseData setIndex(int index) {
        this.index = index;
        return this;
    }

    public WarehouseData setPatientId(int patientId) {
        this.patientId = patientId;
        return this;
    }
}
