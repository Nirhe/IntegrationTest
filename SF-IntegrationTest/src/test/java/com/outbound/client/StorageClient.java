package com.outbound.client;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.outbound.client.exception.SFIntegrationTestsException;
import com.outbound.client.test.ImportFilesToBQTest;
import com.outbound.client.utils.ConfigPropertiesUtil;
import com.outbound.config.SFIntegrationServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StorageClient {

    private Storage storage;
    private Bucket outputBucket;
    private String outputFolder;
    private ConfigPropertiesUtil configPropertiesUtil;

    private static final Logger logger = LoggerFactory.getLogger(StorageClient.class);


    public StorageClient(SFIntegrationServiceConfig sfIntegrationServiceConfig) throws SFIntegrationTestsException {

        this.storage = StorageOptions.getDefaultInstance().getService();
        this.outputBucket = getSingleBucket(sfIntegrationServiceConfig.getOutputBucket());
        this.outputFolder = sfIntegrationServiceConfig.getOutputFolder();
        configPropertiesUtil = new ConfigPropertiesUtil();

    }


    public void putFileOnGCS(File file) {

        try {
            this.placeFlieInBucket(file);
        } catch (SFIntegrationTestsException e) {
            e.printStackTrace();
        }
    }


    public String getFileContentFromGCS(String fileName) {

        Page<Blob> blobs = this.getOutputBucket().list();
        for (Blob blob : blobs.getValues()) {
            if (blob.getName().contains(fileName)) {
                return new String(blob.getContent());
            }
        }
        return null;
    }

    /**
     * @param fileName
     * @return
     */
    public Boolean verifyFileInGCS(String fileName) {

        Page<Blob> blobs = this.getOutputBucket().list();
        for (Blob blob : blobs.getValues()) {
            if (blob.getName().contains(fileName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param file
     * @throws SFIntegrationTestsException
     */
    public void placeFlieInBucket(File file) throws SFIntegrationTestsException {

        try {
            String fileFullPath = configPropertiesUtil.readProperty("filePrefixTest") + "/" + file.getName();
            byte[] fileContent = Files.readAllBytes(file.toPath());

            BlobInfo blobInfo = BlobInfo.newBuilder(outputBucket.getName(), fileFullPath)
                    .build();
            storage.create(blobInfo, fileContent);
        } catch (IOException e) {
            logger.error("Failed to upload file " + file.getName());
            throw new SFIntegrationTestsException("Failed to upload file ", e.getCause());
        }

    }


    private Bucket getSingleBucket(String bucketName) {
        return storage.get(bucketName);
    }


    public Storage getStorage() {
        return storage;
    }

    public Bucket getOutputBucket() {
        return outputBucket;
    }

}
