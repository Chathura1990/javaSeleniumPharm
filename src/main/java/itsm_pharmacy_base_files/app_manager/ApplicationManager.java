package itsm_pharmacy_base_files.app_manager;

import itsm_pharmacy_base_files.app_manager.internalsite_page_helper.GenerateFakeDataService;
import itsm_pharmacy_base_files.app_manager.navigate_to_tabs.NavigationServiceInternalsite;
import itsm_pharmacy_base_files.app_manager.navigate_to_tabs.NavigationServicePharmMain;
import itsm_pharmacy_base_files.app_manager.navigate_to_tabs.NavigationServiceQueuesTabs;
import itsm_pharmacy_base_files.app_manager.pharmacy_reports_helper.FedexShippedReport;
import itsm_pharmacy_base_files.app_manager.pharmacy_tab_page_helper.*;
import itsm_pharmacy_base_files.app_manager.selector_helper.SelectorService;
import itsm_pharmacy_base_files.app_manager.test_base.SessionHelper;
import itsm_pharmacy_base_files.framework.mainClass.Parameters;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static itsm_pharmacy_base_files.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;

public class ApplicationManager {

    public static ChromeDriver driver;
    private final static boolean DEBUG = true;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String OS = System.getProperty("os.name").toLowerCase();
    public static Logger log = Logger.getLogger(ApplicationManager.class.getName());

    private FedexShippedReport fedexShippedReport;
    private FedexInventoryService fedexInventoryService;
    private SelectorService selectorService;
    private GenerateFakeDataService generateFakeDataService;
    private EntryTabService entryTabService;
    private OrderTabService orderTabService;
    private VerificationTabService verificationTabService;
    private FillingTabService fillingTabService;
    private QaTabService qaTabService;
    private ShippingTabService shippingTabService;
    private PatientsTabSearchService patientsTabSearchService;
    private PatientsTabService patientsTabService;
    private NavigationServiceInternalsite navigationServiceInternalsite;
    private NavigationServicePharmMain navigationServicePharmMain;
    private NavigationServiceQueuesTabs navigationServiceQueuesTabs;
    private PosTabService posTabService;
    private ReturnsTabService returnsTabService;

    public void init() {

        Path currentRelativePath = Paths.get("");//getting current path
        String path = currentRelativePath.toAbsolutePath().toString();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        /*
         * open browser (GoogleChrome) and enter user credentials
         */
        ChromeOptions chromeOptions = new ChromeOptions();

        // Prevent infobars from appearing.
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--test-type");
        // Disable extensions.
        chromeOptions.addArguments("--disable-extensions");
        // Disables GPU hardware acceleration. If software renderer is not in place, then the GPU process won't launch.
        chromeOptions.addArguments("--disable-gpu");
        // Disables the sandbox for all process types that are normally sandboxed (bypass OS security model_data) - this is
        // necessary within the Docker environment otherwise you will get "NoSuchSession" exception
        chromeOptions.addArguments("--no-sandbox");
        // Disables the use of a zygote process for forking child processes. Instead, child processes will be forked and
        // exec'd directly. Note that --no-sandbox should also be used together with this flag because the sandbox needs the
        // zygote to work.
        chromeOptions.addArguments("--no-zygote");
        // Overcome limited resource problems
        chromeOptions.addArguments("--disable-dev-shm-usage");

        if (OS.startsWith("windows")) {
            chromePrefs.put("download.default_directory", path+WIN_RESOURCES_PATH);
            System.setProperty("webdriver.chrome.driver", ".\\src\\main\\resources\\driver\\chromedriver.exe");
            if (Parameters.instance().getHeadless().toLowerCase().equals("true")) {
                chromeOptions.addArguments("--headless");
            }
        } else if (OS.startsWith("linux")) {
            chromePrefs.put("download.default_directory", path+LIN_RESOURCES_PATH);
            System.setProperty("webdriver.chrome.driver", Parameters.instance().getChromeDriver());
            if (Parameters.instance().getHeadless().toLowerCase().equals("true")) {
                chromeOptions.addArguments("--headless");
            }
        }
        // Set max. dimensions of the browser window
        chromeOptions.addArguments("window-size=1920,1080");
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

//        DesiredCapabilities cap = DesiredCapabilities.chrome();
//        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(SECONDS20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);

        long start = System.currentTimeMillis();
        driver.get(Parameters.instance().getUrl() + "/app.php/pharmacy/main"); //Opening the Staging site https://internalsiterx.staging.itsupportme.com/app.php/security/login
        long finish = System.currentTimeMillis();
        long totalTimeInMillis = finish - start;
        double seconds = (totalTimeInMillis / 1000.0) % 60;
        double minutes = (double) ((totalTimeInMillis / (1000 * 60)) % 60);
        reportLog("Total time to load the page -> " + "milliseconds: " + totalTimeInMillis + " minutes:" + minutes + " seconds:" + seconds);
        fedexShippedReport = new FedexShippedReport(driver);
        fedexInventoryService = new FedexInventoryService(driver);
        selectorService = new SelectorService(driver);
        generateFakeDataService = new GenerateFakeDataService(driver);
        entryTabService = new EntryTabService(driver);
        orderTabService = new OrderTabService(driver);
        verificationTabService = new VerificationTabService(driver);
        fillingTabService = new FillingTabService(driver);
        qaTabService= new QaTabService(driver);
        shippingTabService = new ShippingTabService(driver);
        patientsTabSearchService = new PatientsTabSearchService(driver);
        patientsTabService = new PatientsTabService(driver);
        navigationServiceInternalsite = new NavigationServiceInternalsite(driver);
        navigationServicePharmMain = new NavigationServicePharmMain(driver);
        navigationServiceQueuesTabs = new NavigationServiceQueuesTabs(driver);
        posTabService = new PosTabService(driver);
        returnsTabService = new ReturnsTabService(driver);

        new SessionHelper(driver).login_To_Website();
    }

    public void stop() throws InterruptedException {
        sleep(LONG_WAIT);
        driver.quit();
    }

    //Method for adding logs passed from test cases
    public static String reportLog(String message) {
        if (DEBUG) {
            Reporter.setEscapeHtml(false);
            Date date = new Date();
            log.info("-- " + message);
            Reporter.log(dateFormat.format(date) + " /" + " " + message);
        }
        return message;
    }

    public FedexShippedReport getFedexShippedReport() {return fedexShippedReport; }

    public FedexInventoryService getFedexInventoryService(){ return fedexInventoryService; }

    public PosTabService getPosTabService() { return posTabService; }

    public ReturnsTabService getReturnsTabService() { return  returnsTabService; }

    public SelectorService getSelectorService(){ return selectorService; }

    public GenerateFakeDataService getGenerateFakeDataService() { return generateFakeDataService; }

    public EntryTabService getEntryTabService() { return entryTabService; }

    public OrderTabService getOrderTabService() { return orderTabService; }

    public VerificationTabService getVerificationTabService(){ return verificationTabService; }

    public FillingTabService getFillingTabService(){ return fillingTabService; }

    public QaTabService getQaTabService(){ return qaTabService; }

    public ShippingTabService getShippingTabService(){ return shippingTabService; }

    public PatientsTabSearchService getPatientsTabSearchService() {
        return patientsTabSearchService;
    }

    public PatientsTabService getPatientsTabService() {
        return patientsTabService;
    }

    public NavigationServiceInternalsite getNavigationServiceInternalsite() {
        return navigationServiceInternalsite;
    }

    public NavigationServicePharmMain getNavigationServicePharmMain() {
        return navigationServicePharmMain;
    }

    public NavigationServiceQueuesTabs getNavigationServiceQueuesTabs() {
        return navigationServiceQueuesTabs;
    }
}
