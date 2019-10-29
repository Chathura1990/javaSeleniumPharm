package itsm_pharmacy_base_files.framework.custom_emailable_report;

import org.testng.annotations.DataProvider;

public class Data
{

    @DataProvider( name = "dp" )
    public static Object[][] createData1() {
        return new Object[][] {
                { "Iteration1", new Integer(36) },
                { "Iteration2", new Integer(37)},
        };
    }

}
