package com.deesite.global;

import java.io.File;

/**
 * Global Variable Class
 *
 * @author Deepak Arora
 */
public class Global_VARS {
    // target app defaults or // browser defaults
    public static final String BROWSER = "chrome";
    public static final String PLATFORM = "MAC";
    public static final String ENVIRONMENT = "local";
    public static String DEF_BROWSER = null;
    public static String DEF_PLATFORM = null;
    public static String DEF_ENVIRONMENT = null;
    public static String PROPS_PATH = null;

    public static final String TARGET_URL = "http://www.practiceselenium.com/";

    // suite folder defaults
    public static String SUITE_NAME = null;

    // driver class defaults
    public static String propFile = "/Users/deepakarora/git/SeleniumFrameworkDesign/configs/selenium.properties";
    public static final String SE_PROPS = new File(propFile).getAbsolutePath();

    // test output path defaults
    public static final String TEST_OUTPUT_PATH = "test-output/";
    public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "Logs/";
    public static final String REPORT_PATH = TEST_OUTPUT_PATH + "Reports/";
    public static final String BITMAP_PATH = TEST_OUTPUT_PATH + "Bitmaps/";
    public static final String REPORT_CONFIG_FILE = "/Users/deepakarora/git/SeleniumFrameworkDesign/configs/extent-config.xml";

    // timeout defaults
    public static final int TIMEOUT_MINUTE = 60;
    public static final int TIMEOUT_SECOND = 1;
    public static final int TIMEOUT_ZERO = 0;
    public static final int TIMEOUT_ELEMENT = 10;


}