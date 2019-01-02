package com.outbound.client.test;

import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.outbound.client.BigQueryClient;
import com.outbound.client.StorageClient;
import com.outbound.client.constants.SFApiTestConstants;
import com.outbound.client.constants.SFIntegrationTestsQueriesEnum;
import com.outbound.client.exception.SFIntegrationTestsException;
import com.outbound.client.utils.ConfigPropertiesUtil;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ImportFilesToBQTest extends AbstractSFApiTest {

    private static final Logger logger = LoggerFactory.getLogger(ImportFilesToBQTest.class);
    protected StorageClient storageClient;
    protected File file;

    @Override
    protected Logger getLogger() {
        return logger;
    }


    public ImportFilesToBQTest() {
        super();
    }

    @Override
    @Before
    public void setUp() throws Exception {

        super.setUp();
        storageClient = new StorageClient(getCloudConfig().getSFIntegrationServiceConfig());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ConfigPropertiesUtil configPropertiesUtil = new ConfigPropertiesUtil();
        String fileName = configPropertiesUtil.readProperty("fileName");
        file = new File(classLoader.getResource(fileName).getFile());

        //Put file in GCS

        storageClient.putFileOnGCS(file);
        HashMap<String,String> postParams = new HashMap<>();
        postParams.put("bucketName","dataflowdirectory");
        postParams.put("fileName",configPropertiesUtil.readProperty("filePrefixTest")+"/"
                +configPropertiesUtil.readProperty("fileName"));
        postParams.put("testFlag","true");

        //Call Relex Transfer Service to move file from GCS bucket to SFTP
        RestAssured.given().contentType("application/json").body(postParams).
                post(RestAssured.basePath + SFApiTestConstants.TRANSFER_FILES_USING_POST).then().statusCode(202);

    }


    @Test
    public void importOutboundFilestoBQ() throws IOException, SFIntegrationTestsException {

        //TODO create a file with current date (save the current date) and save in GCS
        logger.info("Going to call Relex Transfer files  to Retrieve files from Relex SFTP");
        RestAssured.when().get(RestAssured.basePath +
        SFApiTestConstants.RETRIEVE_FILES_USING_GET)
                .then().statusCode(202);

        logger.info("Verify in Storage that the file was transfer from SFTP to Google Cloud Storage");

        assertTrue(storageClient.verifyFileInGCS(file.getName()));


        //Check in Big query if the records are there
        logger.info("Verify in Big Query that data is persist");
        TableResult queryResults = BigQueryClient.getInstance().getQueryResults
                (SFIntegrationTestsQueriesEnum.SELECT_FROM_SFRELEX_DAILY_BY_LOCATION_CODE.getSql(),"");


        boolean foundRecord = false;
        for (FieldValueList row : queryResults.iterateAll()) {
            Object date = row.get("DATE").getValue();
            //   BigDecimal locationCode = row.get(2).getNumericValue();
            //2019-03-11
            if(date.toString().equals("2019-03-11")){
                foundRecord = true;
            }
        }
        assertTrue(foundRecord);

    }


//    @Test
//    public void generalTest() throws  SFIntegrationTestsException {
//
//        FileUtil fileUtil = new FileUtil();
//        fileUtil.decompressFile(this.file);
//    }


    @After
    public void tearDown(){


    }




}



