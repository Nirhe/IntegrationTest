package com.outbound.client.constants;

public final class SFApiTestConstants {


    private static final String RETRIEVE_FILES = "retrieveFiles";
    private static final String TRANSFER_SERVICE = "/transferService";
    private static final String TRANSFER_FILE = "transferFile";

    public static final String RETRIEVE_FILES_USING_GET = TRANSFER_SERVICE + "/" + RETRIEVE_FILES;
    public static final String RELEX_TRANSFER_SERVICE_PATH = "https://sf-relextransferservice-dot-np-sales-forecasting.appspot.com";
    public static final String PROPERTIES_FILE = "src/test/resources/application.properties";
    public static final String RESOURCE_TEST_PATH = "src/test/resources/";
    public static final String TRANSFER_FILES_USING_POST = TRANSFER_SERVICE + "/" + TRANSFER_FILE;


}
