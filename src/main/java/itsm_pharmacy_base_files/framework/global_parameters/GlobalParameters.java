package itsm_pharmacy_base_files.framework.global_parameters;

import java.io.File;

public class GlobalParameters {

    public static final String websiteURL = "https://internalsiterx.staging.itsupportme.com";
    public static final String VALID_USERNAME = "CRR";
    public static final String VALID_PASSWORD = "123456c";
    public static final File WIN_RESOURCES_PATH = new File("\\Report\\");
    public static final File LIN_RESOURCES_PATH = new File("/Report/");

    //Page load waits
    public static int MILLISEC1000 = 1000;
    public static int MILLISEC2000 = 2000;
    public static int SECONDS20 = 20;
    public static int IMPLICIT_WAIT = 10;
    public static int PAGE_LOAD_TIMEOUT = 20;
    public static int LONG_WAIT = 4000;

    //Patient details
    public static int AUTOMATION_TEST_PATIENT = 27746;
    public static String PATIENT_ADDRESS_LINE1 = "8788 CHEVY CHASE ST";
    public static String PATIENT_CITY = "JAMAICA";
    public static String PATIENT_STATE = "NY";
    public static int PATIENT_ZIP_CODE = 114322441;
    //Patient Insurance details
    public static int BIN_NUMBER = Integer.parseInt("123456");
    public static String BIN_RESULT = "Bin: 123456, Name: Automation insurance, Pcn: 123";
    public static int CARDHOLDER_ID = 1111;
    //Patient physician details
    public static String PHYS_ID = "FIXMENOW";

    //Patient Medications, Allergies and Diseases
    public static int QTY_PRESCRIBED = 40;
    public static int DAYS_SUPPLY = 40;
    public static String DOXEPIN = "Doxepin HCl";//Medication
    public static String DOXEPIN_ALLERGY = "Doxepin";//Allergy
    public static String PACERONE100 = "Pacerone 100 mg tablet";//Current Medication
    public static String GLAUCOMA = "glaucoma";//Disease

    //Paths of the Patient Data Csv and rx pdf
    public static String PATHTOPDFDOC = "src/main/resources/testdoc.pdf";
}
