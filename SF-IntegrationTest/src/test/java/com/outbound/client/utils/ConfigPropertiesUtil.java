package com.outbound.client.utils;

import com.outbound.client.StorageClient;
import com.outbound.client.exception.SFIntegrationTestsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static com.outbound.client.constants.SFApiTestConstants.PROPERTIES_FILE;

public class ConfigPropertiesUtil {


    private Path propertyFile;
    private BufferedReader propertiesReader;
    Properties appProps;

    private static final Logger logger = LoggerFactory.getLogger(ConfigPropertiesUtil.class);


    public ConfigPropertiesUtil() throws SFIntegrationTestsException {

        propertyFile = Paths.get(PROPERTIES_FILE);
        try {
            propertiesReader = Files.newBufferedReader(propertyFile);
            appProps = new Properties();
            appProps.load(propertiesReader);
        } catch (IOException e) {
            logger.error("Failed to init properties file");
            throw new SFIntegrationTestsException("ailed to init proprties file", e.getCause());
        }

    }


    public String readProperty(String key) {

        return appProps.getProperty(key);
    }


}
