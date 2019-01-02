package com.outbound.client;

import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.outbound.client.exception.SFIntegrationTestsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class BigQueryClient {

    private BigQuery bigQuery;

    private static BigQueryClient instance;

    private static final Logger logger = LoggerFactory.getLogger(BigQueryClient.class);

    public static synchronized BigQueryClient getInstance() {
        if (instance == null) {
            instance = new BigQueryClient();
        }
        return instance;
    }


    private BigQueryClient() {
        this.bigQuery = BigQueryOptions.getDefaultInstance().getService();
    }

    /**
     *
     * @param query
     * @return
     */
    public TableResult getQueryResults(String query , String locationCode) throws SFIntegrationTestsException {

        String selectSql = String.format(query,locationCode);
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(selectSql).build();
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        TableResult result = null;

        if (queryJob != null) {

            try {
                result = queryJob.getQueryResults();
            } catch (InterruptedException e) {
                logger.error("File to get query results");
                throw new SFIntegrationTestsException("File to get query results",e.getCause());
            }

        }
        return result;
    }
}
//        // Wait for the query to complete.
//        queryJob = queryJob.waitFor();

//        for (FieldValueList row : bigQuery.query(queryConfig).iterateAll()) {
//            for (FieldValue val : row) {
//                System.out.printf("%s,", val.toString());
//            }
//            System.out.printf("\n");
//        }

