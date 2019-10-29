package itsm_pharmacy_base_files.app_manager.model_data;

public class EntryTabData {

    private String medicationDts;
    private int qtyPrescribed;
    private int daySupply;
    private String dateWritten;

    public EntryTabData medicationDts(String medicationDts){
        this.medicationDts = medicationDts;
        return this;
    }

    public EntryTabData qtyPrescribed(int qtyPrescribed){
        this.qtyPrescribed = qtyPrescribed;
        return this;
    }

    public EntryTabData daySupply(int daySupply){
        this.daySupply = daySupply;
        return this;
    }

    public EntryTabData dateWritten(String dateWritten){
        this.dateWritten = dateWritten;
        return this;
    }

    public String getMedicationDts(){
        return medicationDts;
    }

    public int getQtyPrescribed(){
        return qtyPrescribed;
    }

    public int getDaySupply(){
        return daySupply;
    }

    public String getDateWritten(){
        return dateWritten;
    }
}
