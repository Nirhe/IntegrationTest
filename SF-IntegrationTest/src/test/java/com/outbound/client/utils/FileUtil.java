package com.outbound.client.utils;

import com.outbound.client.exception.SFIntegrationTestsException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.outbound.client.constants.SFApiTestConstants.RESOURCE_TEST_PATH;

public class FileUtil {


    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public ZipFile decompressFile(File file) throws  SFIntegrationTestsException {

        ZipFile zipFile= null;

        try {
            zipFile = new ZipFile(file);
            zipFile.extractAll(RESOURCE_TEST_PATH);

        } catch (ZipException e) {
            logger.error("Failed to unzip file");
            throw new SFIntegrationTestsException("Failed to unzip file", e.getCause());
        }

        return zipFile;

    }

}
