package itsm_pharmacy_base_files.app_manager.model_data;

public class PharmacyFakeData {

    private int patients;
    private int numberOfRxes;

    public PharmacyFakeData patients(int patients){
        this.patients = patients;
        return this;
    }

    public PharmacyFakeData numberOfRxes(int numberOfRxes){
        this.numberOfRxes = numberOfRxes;
        return this;
    }

    public int getNumberOfRxes(){
        return numberOfRxes;
    }

    public int getPatients(){
        return patients;
    }
}
