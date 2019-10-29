package itsm_pharmacy_base_files.app_manager.model_data;

import java.io.File;
import java.util.Objects;

public class PatientsTabData {
    //Patient Info widget
    private int patientId;
    private int rxId;
    private String patName;
    private String patMiddleName;
    private String patLastName;
    private String patSocialSecurityNumber;
    private String patPhoneNumber;
    private String patDob;
    private String patEmail;
    private String addressLine1;
    private String city;
    private String state;
    private int zipCode;
    private File photo;
    //Physician Details
    private String PhysId;
    //Allergy widget
    private String allergy;
    //Diseases and Health Condition widget
    private String disease;
    //Patient Insurance widget
    private int binWithSixIntValues;
    private int cardholderId;
    //Current Medication widget
    private String medication;
    private int selectOption;
    private String comment;
    private String trackingNum;

    public PatientsTabData patientId(int patientId){
        this.patientId = patientId;
        return this;
    }

    public PatientsTabData rxId(int rxId) {
        this.rxId = rxId;
        return this;
    }

    public PatientsTabData patPhysId(String physId) {
        PhysId = physId;
        return this;
    }

    public PatientsTabData patName(String patName){
        this.patName = patName;
        return this;
    }

    public PatientsTabData patMiddleName(String patMiddleName){
        this.patMiddleName = patMiddleName;
        return this;
    }

    public PatientsTabData patLastName(String patLastName){
        this.patLastName = patLastName;
        return this;
    }

    public PatientsTabData patSocialSecurityNumber(String patSocialSecurityNumber){
        this.patSocialSecurityNumber = patSocialSecurityNumber;
        return this;
    }

    public PatientsTabData patPhoneNumber(String patPhoneNumber){
        this.patPhoneNumber = patPhoneNumber;
        return this;
    }

    public PatientsTabData patDob(String patDob){
        this.patDob = patDob;
        return this;
    }

    public PatientsTabData patEmail(String patEmail){
        this.patEmail = patEmail;
        return this;
    }

    public PatientsTabData addressLine1(String addressLine1){
        this.addressLine1 = addressLine1;
        return this;
    }

    public PatientsTabData city(String city){
        this.city = city;
        return this;
    }

    public PatientsTabData state(String state){
        this.state = state;
        return this;
    }

    public PatientsTabData zipCode(int zipCode){
        this.zipCode = zipCode;
        return this;
    }

    public PatientsTabData photo(File photo) {
        this.photo = photo;
        return this;
    }

    public PatientsTabData allergy(String allergy) {
        this.allergy = allergy;
        return this;
    }

    public PatientsTabData disease(String disease){
        this.disease = disease;
        return this;
    }

    public PatientsTabData binWithSixIntValues(int binWithSixIntValues){
        this.binWithSixIntValues = binWithSixIntValues;
        return this;
    }

    public PatientsTabData cardholderId(int cardholderId){
        this.cardholderId = cardholderId;
        return this;
    }

    public PatientsTabData medication(String medication){
        this.medication = medication;
        return this;
    }

    public PatientsTabData selectOption(int selectOption){
        this.selectOption = selectOption;
        return this;
    }

    public PatientsTabData patComment(String comment) {
        this.comment = comment;
        return this;
    }

    public PatientsTabData trackingNum(String trackingNum){
        this.trackingNum = trackingNum;
        return this;
    }

    public int getPatientId(){ return patientId; }

    public int getRxId() { return rxId; }

    public String getPatPhysId() { return PhysId; }

    public String getPatName(){ return patName; }

    public String getPatMiddleName(){ return patMiddleName; }

    public String getPatLastName(){ return patLastName; }

    public String getPatSocialSecurityNumber(){ return patSocialSecurityNumber; }

    public String getPatPhoneNumber(){ return patPhoneNumber; }

    public String getPatDob(){ return patDob; }

    public String getPatEmail(){ return patEmail; }

    public String getAddressLine1(){ return addressLine1; }

    public String getCity(){ return city; }

    public String getState(){ return state; }

    public int getZipCode(){ return zipCode; }

    public File getPhoto() { return photo; }

    public String getAllergy(){ return allergy; }

    public String getDisease(){ return disease; }

    public int getBinWithSixIntValues(){ return binWithSixIntValues; }

    public int getCardholderId(){ return cardholderId; }

    public String getMedication(){ return medication; }

    public int getSelectOption() { return selectOption; }

    public String getComment() { return comment; }

    public String getTrackingNum() { return trackingNum; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientsTabData that = (PatientsTabData) o;
        return Objects.equals(trackingNum, that.trackingNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNum);
    }

    @Override
    public String toString() {
        return "PatientsTabData{" +
                "trackingNum='" + trackingNum + '\'' +
                '}';
    }
}
