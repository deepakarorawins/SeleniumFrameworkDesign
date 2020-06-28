package com.deesite.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * File I/O Static Utility Class
 *
 * @author Deepak Arora
 *
 */
public class File_IO {
    /**
     * loadProps- method to load a Properties file *
     * @param file - The file to load
     * @return Properties - The properties to retrieve * @throws Exception
     */
    public static Properties loadProps(String file) throws Exception {
        Properties props = new Properties(); props.load(new FileInputStream(file));
        return props;
    }

}
