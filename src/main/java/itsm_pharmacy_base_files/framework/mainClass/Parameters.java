package itsm_pharmacy_base_files.framework.mainClass;

import com.beust.jcommander.Parameter;
import itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters;

public class Parameters {

    private static Parameters instance;

    @Parameter(names = {"--chrome", "-c"}, description = "Path to Google Chrome Driver")
    private String chromeDriver = "./src/main/resources/driver/chromedriver";

    @Parameter(names = "--help", help = true, description = "How to use")
    private boolean help;

    @Parameter(names = {"--url", "-u"}, description = "URL")
    private String url = GlobalParameters.websiteURL;

    @Parameter(names = "--headless", description = "If flag set to 'true' Browser will be started in headless mode (required for running on server)")
    private String headless = "false";

    public static synchronized Parameters instance() {
        if (instance == null) {
            instance = new Parameters();
        }
        return instance;
    }

    public String getChromeDriver() {
        return chromeDriver;
    }

    public boolean isHelp() {
        return help;
    }

    public String getHeadless() {
        return headless;
    }

    public String getUrl() {
        return url;
    }
}
