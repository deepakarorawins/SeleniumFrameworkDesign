package com.deesite.managers;

import com.deesite.global.Global_VARS;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.FileInputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* Selenium Singleton Class
*
* @author Deepak Arora
*/
public class CreateDriver {
    // local variables
    private static CreateDriver instance = null;
    private String browserHandle = null;
    private static final int IMPLICIT_TIMEOUT = 0;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();

    private ThreadLocal<String> sessionId = new ThreadLocal<String>();
    private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
    private ThreadLocal<String> sessionPlatform = new ThreadLocal<String>();
    private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();

    private String getEnv = null;
    private Properties props = new Properties();

    // no args constructor
    private CreateDriver() {

    }

    /**
     * getInstance method to retrieve active driver instance.
     *
     * @return the com.deesite.managers.CreateDriver
     */
    public static CreateDriver getInstance() {
        if (instance == null) {
            instance = new CreateDriver();
        }
        return instance;
    }

    /**
     * setDriver method to create driver instance
     *
     * @param browser
     * @param environment
     * @param platform
     * @param optPreferences
     * @throws Exception
     */
    @SafeVarargs
    public final void setDriver(String browser,
                                String platform,
                                String environment,
                                Map<String, Object>... optPreferences) throws Exception {
        DesiredCapabilities caps = null;
        String localHub = "http://127.0.0.1:4723/wd/hub";
        String getPlatform = null;
        props.load(new FileInputStream(Global_VARS.SE_PROPS));
        switch (browser) {
            case "firefox":
                caps = new DesiredCapabilities();
                FirefoxOptions ffOpts = new FirefoxOptions();
                FirefoxProfile ffProfile = new FirefoxProfile();
                ffProfile.setPreference("browser.autofocus", true);
                ffProfile.setPreference("browser.tabs.remote. autostart.2", false);
                caps.setCapability(FirefoxDriver.Capability.PROFILE, ffProfile);
                caps.setCapability("marionette", true);
                // then pass them to the local WebDriver
                if (environment.equalsIgnoreCase("local")) {
                    if (platform.equalsIgnoreCase("mac")) {
                        System.setProperty("webdriver.gecko.driver", props.getProperty("gecko.driver.mac.path"));
                    } else if (getPlatform.equalsIgnoreCase("windows")) {
                        System.setProperty("webdriver.gecko.driver", props.getProperty("gecko.driver.windows.path"));
                    }
                    //System.setProperty("webdriver.gecko.driver", "/Users/deepakarora/git/SeleniumFrameworkDesign/drivers/geckodriver");
                    if (optPreferences.length > 0) {
                        processFFProfile(ffProfile, optPreferences);
                    }
                    webDriver.set(new FirefoxDriver(ffOpts.merge(caps)));
                }
                // for each browser instance
                if (environment.equalsIgnoreCase("remote")) {
                    // set up the Selenium Grid capabilities...
                    //String remoteHubURL = "http://mygrid- hub.companyname.com:4444/wd/hub";
                    String remoteHubURL = "http://192.168.1.104:4444/";

                    caps.setCapability("browserName", browser);
                    caps.setCapability("version", caps.getVersion());
                    caps.setCapability("platform", platform);
                    // unique user-specified name
                    caps.setCapability("applicationName", platform + "-" + browser);

                    webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), caps));
                    ((RemoteWebDriver) webDriver.get()).setFileDetector(new LocalFileDetector());
                }
                break;
            case "chrome":
                caps = new DesiredCapabilities();
                ChromeOptions chOptions = new ChromeOptions();
                Map<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("credentials_enable_service", false);
                chOptions.setExperimentalOption("prefs", chromePrefs);
                chOptions.addArguments("--disable-plugins",
                        "--disable-extensions",
                        "--disable-popup-blocking");
                caps.setCapability(ChromeOptions.CAPABILITY, chOptions);
                caps.setCapability("applicationCacheEnabled", false);

                if (environment.equalsIgnoreCase("local")) {
                    if (platform.equalsIgnoreCase("mac")) {
                        System.setProperty("webdriver.chrome.driver", props.getProperty("chrome.driver.mac.path"));
                    } else if (getPlatform.equalsIgnoreCase("windows")) {
                        System.setProperty("webdriver.chrome.driver", props.getProperty("chrome.driver.windows.path"));
                    }
                    //System.setProperty("webdriver.chrome.driver", "/Users/deepakarora/git/SeleniumFrameworkDesign/drivers/chromedriver");
                    if (optPreferences.length > 0) {
                        processCHOptions(chOptions, optPreferences);
                    }
                    webDriver.set(new ChromeDriver(chOptions.merge(caps)));
                }
                if (environment.equalsIgnoreCase("remote")) {
                    // set up the Selenium Grid capabilities...
                    //String remoteHubURL = "http://mygrid- hub.companyname.com:4444/wd/hub";
                    String remoteHubURL = "http://192.168.1.104:4444/";

                    caps.setCapability("browserName", browser);
                    caps.setCapability("version", caps.getVersion());
                    caps.setCapability("platform", platform);
                    // unique user-specified name
                    caps.setCapability("applicationName", platform + "-" + browser);

                    webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), caps));
                    ((RemoteWebDriver) webDriver.get()).setFileDetector(new LocalFileDetector());
                }
                break;
            case "internet explorer":
                //caps = DesiredCapabilities.internetExplorer();
                InternetExplorerOptions ieOpts = new InternetExplorerOptions();
                ieOpts.requireWindowFocus();
                ieOpts.merge(caps);
                caps.setCapability("requireWindowFocus", true);
                if (environment.equalsIgnoreCase("local")) {
                    System.setProperty("webdriver.ie.driver", props.getProperty("ie.driver.windows.path"));
                    webDriver.set(new InternetExplorerDriver(ieOpts.merge(caps)));
                } if (environment.equalsIgnoreCase("remote")) {
                // set up the Selenium Grid capabilities...
                //String remoteHubURL = "http://mygrid- hub.companyname.com:4444/wd/hub";
                String remoteHubURL = "http://192.168.1.104:4444/";

                caps.setCapability("browserName", browser);
                caps.setCapability("version", caps.getVersion());
                caps.setCapability("platform", platform);
                // unique user-specified name
                caps.setCapability("applicationName", platform + "-" + browser);

                webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), caps));
                ((RemoteWebDriver) webDriver.get()).setFileDetector(new LocalFileDetector());
            }
                break;
            case "safari":
                caps = new DesiredCapabilities();
                SafariOptions safariOpts = new SafariOptions();
                ///safariOpts.setUseCleanSession(true);
                ///caps.setCapability(SafariOptions.CAPABILITY, safariOpts);
                caps.setCapability("autoAcceptAlerts", true);
                webDriver.set(new SafariDriver(safariOpts.merge(caps)));
                break;
            case "microsoftedge":
                //caps = DesiredCapabilities.edge();
                caps = new DesiredCapabilities();
                EdgeOptions edgeOpts = new EdgeOptions();
                ///edgeOpts.setPageLoadStrategy("normal");
                caps.setCapability(EdgeOptions.CAPABILITY, edgeOpts);
                caps.setCapability("requireWindowFocus", true);

                if (environment.equalsIgnoreCase("local")) {
                    if (platform.equalsIgnoreCase("mac")) {
                        System.setProperty("webdriver.edge.driver", props.getProperty("edge.driver.mac.path"));
                    } else if (getPlatform.equalsIgnoreCase("windows")) {
                        System.setProperty("webdriver.edge.driver", props.getProperty("edge.driver.windows.path"));
                    }
                    webDriver.set(new EdgeDriver(edgeOpts.merge(caps)));
                }
                if (environment.equalsIgnoreCase("remote")) {
                    // set up the Selenium Grid capabilities...
                    //String remoteHubURL = "http://mygrid- hub.companyname.com:4444/wd/hub";
                    String remoteHubURL = "http://192.168.1.104:4444/";

                    caps.setCapability("browserName", browser);
                    caps.setCapability("version", caps.getVersion());
                    caps.setCapability("platform", platform);
                    // unique user-specified name
                    caps.setCapability("applicationName", platform + "-" + browser);

                    webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), caps));
                    ((RemoteWebDriver) webDriver.get()).setFileDetector(new LocalFileDetector());
                }
                break;
            case "iphone":
            case "ipad":
                if (browser.equalsIgnoreCase("ipad")) {
                    //caps = DesiredCapabilities.ipad();
                } else {
                    caps = new DesiredCapabilities();
                }
                //caps.setCapability("appName", "https://myapp.com/myApp.zip");
                caps.setCapability("deviceName", "iPhoneX"); // physical device
                caps.setCapability("platformVersion", "13.5"); // physical device
                //caps.setCapability("bundleId", "com.google.chrome.ios"); // physical device
                //caps.setCapability("appName", "/Users/deepakarora/git/SeleniumFrameworkDesign/apps/OpenLink.ipa");
                caps.setCapability("udid", "2e5a94780943a089d14ce4f47f056e8c505ca4c4"); // physical device
                caps.setCapability("device", "iPhone"); // or iPad
                caps.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari"); // or iPad
                mobileDriver.set(new IOSDriver<MobileElement>(new URL(localHub), caps));
                //mobileDriver.set(new AppiumDriver<MobileElement>(new URL(localHub), caps));
                break;
            case "android":
                //caps = DesiredCapabilities.android();
                caps.setCapability("appName", "https://myapp.com/myApp.apk");
                caps.setCapability("udid", "12345678"); // physical device
                caps.setCapability("device", "Android");
                mobileDriver.set(new AndroidDriver<MobileElement>(new URL(localHub), caps));
                break;
        }
        getEnv = environment;
        getPlatform = platform;
        if (browser.equalsIgnoreCase("iphone") ||
                browser.equalsIgnoreCase("android")) {
            sessionId.set(((IOSDriver<MobileElement>) mobileDriver.get()).getSessionId().toString());
            ///sessionId.set(((AndroidDriver<MobileElement>) mobileDriver.get()).getSessionId().toString());
            sessionBrowser.set(browser);
            sessionVersion.set(caps.getCapability("deviceName").toString());
            sessionPlatform.set(getPlatform);
        } else {
            sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
            sessionBrowser.set(caps.getBrowserName());
            sessionVersion.set(caps.getVersion());
            sessionPlatform.set(getPlatform);
        }
        System.out.println("\n*** TEST ENVIRONMENT = "
                + getSessionBrowser().toUpperCase()
                + "/" + getSessionPlatform().toUpperCase() + "/" + getEnv.toUpperCase()
                + "/Selenium Version="
                + props.getProperty("selenium.revision")
                + "/Session ID="
                + getSessionId()
                + "\n");
        getDriver().manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
    }


    /**
     * overloaded setDriver method to switch driver to specific WebDriver * if running concurrent drivers
     *
     * @param driver WebDriver instance to switch to
     */
    public void setDriver(WebDriver driver) {
        webDriver.set(driver);
        sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
        sessionBrowser.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getBrowserName());
        sessionPlatform.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getPlatform().toString());
        ///setBrowserHandle(getDriver().getWindowHandle());
    }

    /**
     * overloaded setDriver method to switch driver to specific AppiumDriver * if running concurrent drivers
     *
     * @param driver AppiumDriver instance to switch to
     */
    public void setDriver(AppiumDriver<MobileElement> driver) {
        mobileDriver.set(driver);
        sessionId.set(mobileDriver.get().getSessionId().toString());
        sessionBrowser.set(mobileDriver.get().getCapabilities().getBrowserName());
        sessionPlatform.set(mobileDriver.get().getCapabilities().getPlatform().toString());
    }

    /**
     * getDriver method to retrieve active driver
     *
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return webDriver.get();
    }

    /**
     * getDriver method will retrieve the active AppiumDriver *
     *
     * @param mobile boolean parameter
     * @return AppiumDriver
     */
    public AppiumDriver<MobileElement> getDriver(boolean mobile) {
        return mobileDriver.get();
    }

    /**
     * getCurrentDriver method will retrieve the active WebDriver * or AppiumDriver
     *
     * @return WebDriver
     */
    public WebDriver getCurrentDriver() throws Exception {
        if (getInstance().getSessionBrowser().contains("iphone") ||
                getInstance().getSessionBrowser().contains("ipad") ||
                getInstance().getSessionBrowser().contains("android")) {
            return getInstance().getDriver(true);
        } else {
            return getInstance().getDriver();
        }
    }

    /**
     * driverWait method pauses the driver in seconds *
     *
     * @param seconds to pause
     */
    public void driverWait(long seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            // do something
        }
    }

    /**
     * driverRefresh method reloads the current browser page
     */
    public void driverRefresh() throws Exception {
        getCurrentDriver().navigate().refresh();
    }

    /**
     * closeDriver method to close active driver
     */
    public void closeDriver() {
        try {
            ///getDriver().quit();
            getCurrentDriver().quit();
        } catch (Exception e) {
            // do something
        }
    }

    /**
     * getSessionId method gets the browser or mobile id of the active session
     *
     * @return String
     * @throws Exception
     */
    public String getSessionId() throws Exception {
        return sessionId.get();
    }

    /**
     * getSessionBrowser method gets the browser or mobile type of the active session
     *
     * @return String
     * @throws Exception
     */
    public String getSessionBrowser() throws Exception {
        return sessionBrowser.get();
    }

    /**
     * getSessionVersion method gets the browser or mobile version of the active session
     *
     * @return String
     * @throws Exception
     */
    public String getSessionVersion() throws Exception {
        return sessionVersion.get();
    }

    /**
     * getSessionPlatform method gets the browser or mobile platform of the active session
     *
     * @return String
     * @throws Exception
     */
    public String getSessionPlatform() throws Exception {
        return sessionPlatform.get();
    }


    // for convenience, create a setPreferences method // to build the map to pass into the driver
    public Map<String, Object> setPreferences() {
        Map<String, Object> prefsMap = new HashMap<>();
        List<String> allPrefs = Arrays.asList(
                System.getProperty("browserPrefs").split(",", -1));
        // extract the key/value pairs and pass to map...
        for (String getPref : allPrefs) {
            prefsMap.put(getPref.split(":")[0], getPref.split(":")[1]);
        }
        return prefsMap;
    }

    /**
     * Process Desired Capabilities method to override default browser or mobile driver behavior
     *
     * @param caps    - the DesiredCapabilities object
     * @param options - the key: value pair map
     * @throws Exception
     */
    private void processDesiredCaps(DesiredCapabilities caps, Map<String, Object>[] options) throws Exception {
        for (int i = 0; i < options.length; i++) {
            Object[] keys = options[i].keySet().toArray();
            Object[] values = options[i].values().toArray();
            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    caps.setCapability(keys[j].toString(), (int) values[j]);
                } else if (values[j] instanceof Boolean) {
                    caps.setCapability(keys[j].toString(), (boolean) values[j]);
                } else if (isStringInt(values[j].toString())) {
                    caps.setCapability(keys[j].toString(),
                            Integer.valueOf(values[j].toString()));
                } else if (Boolean.parseBoolean(values[j].toString())) {
                    caps.setCapability(keys[j].toString(),
                            Boolean.valueOf(values[j].toString()));
                } else {
                    caps.setCapability(keys[j].toString(),
                            values[j].toString());
                }
            }
        }
    }

    /**
     * Process Firefox Profile Preferences method to override default * browser driver behavior
     *
     * @param profile - the FirefoxProfile object
     * @param options - the key: value pair map * @throws Exception
     */
    private void processFFProfile(FirefoxProfile profile, Map<String, Object>[] options) throws Exception {
        for (int i = 0; i < options.length; i++) {
            Object[] keys = options[i].keySet().toArray();
            Object[] values = options[i].values().toArray();
            // same as Desired Caps except the following difference
            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    profile.setPreference(keys[j].toString(), (int) values[j]);
                }
                // etc...
            }
        }
    }

    /**
     * Process Chrome Options method to override default browser driver behavior
     *
     * @param chOptions - the ChromeOptions object
     * @param options   - the key: value pair map
     * @throws Exception
     */
    private void processCHOptions(ChromeOptions chOptions, Map<String, Object>[] options) throws Exception {
        for (int i = 0; i < options.length; i++) {
            Object[] keys = options[i].keySet().toArray();
            Object[] values = options[i].values().toArray();

            // same as Desired Caps except the following difference
            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    values[j] = (int) values[j];
                    chOptions.setExperimentalOption("prefs", options[i]);
                }
                // etc...
            }
        }
    }

    /**
     * Method to check if a string is can be parseable by int
     *
     * @param s
     * @return
     */
    public static boolean isStringInt(String s) {
        boolean bool = false;
        try {
            Integer.parseInt(s);
            bool = true;
        } catch (NumberFormatException nfe) {
            bool = false;
        }
        return bool;
    }

}
