package com.outbound.config;



import com.homedepot.ta.aa.crypto.Crypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.outbound.client.utils.DataStoreUtil;



public class CloudConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CloudConfig.class);

    private static final String SFTP_INFO = "SFTP_INFO";
    private static final String COMSALES = "COMSALES";
    private static final String COMMON = "COMMON";
    private static final String EMAIL_SERVICE = "EMAIL_SERVICE";

    private DataStoreUtil dataStoreUtil = new DataStoreUtil();

    /**
     * Gets an instance of the SFIntegrationServiceConfig, fully built from the GCP DataStore.
     *
     * @return SFIntegrationServiceConfig
     */
    public SFIntegrationServiceConfig getSFIntegrationServiceConfig() {
        dataStoreUtil.connectToDatastore();
        LOG.info("Building Config for transfer service.");
        return new SFIntegrationServiceConfig.Builder()
                .withOutputBucket(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("OUTPUT_BUCKET_LOCATION"))
                .withOutputFolder(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("OUTPUT_STORAGE_FOLDER"))
                .withNumberRetries((int) (this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getLong("NUMBER_RETRIES")))
                .withEmailEndpoint(this.dataStoreUtil.getEntityFromKindAndKey(COMMON, EMAIL_SERVICE).getString("EMAIL_SERVICE_URL"))
                .withSftpOutputDirectory(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("SFTP_OUTPUT_LOCATION"))
                .withSftpInputDirectory(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("SFTP_INPUT_LOCATION"))
                .withSftpArchiveDirectory(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("SFTP_ARCHIVE_LOCATION"))
                .withEmailAddresses(this.dataStoreUtil.getEntityFromKindAndKey(COMMON, EMAIL_SERVICE).getString("TO_EMAIL_ADDRESSES"))
                .withSftpURI(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("URI_TARGET"))
                .withSftpPassword(Crypto.decrypt(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("SFTPPASSKEY")))
                .withSftpUsername(Crypto.decrypt(this.dataStoreUtil.getEntityFromKindAndKey(COMSALES, SFTP_INFO).getString("SFTPUSER")))
                .build();
    }

}
