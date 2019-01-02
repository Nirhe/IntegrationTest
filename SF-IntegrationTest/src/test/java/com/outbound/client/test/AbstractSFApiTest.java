package com.outbound.client.test;

import com.outbound.client.constants.SFApiTestConstants;
import com.outbound.config.CloudConfig;
import com.outbound.config.SFIntegrationServiceConfig;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.ConfigurationUtils;
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;


import java.util.UUID;

import org.slf4j.Logger;


import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


public abstract class AbstractSFApiTest {

    private static final String ALWAYS_LOG_REQ_AND_RESP_PROPERTY = "alwaysLogApiRequestAndResponse";

    protected ImmutableConfiguration config;
    protected CloudConfig cloudConfig;
    protected SFIntegrationServiceConfig sfIntegrationServiceConfig;

    /**
     * Automate the startup and shutdown of the WireMock mock HTTP server before and after the execution of each test to
     * support stubbing HTTP response and verifying HTTP requests.
     * <p>
     * Configures WireMock to pick a random free HTTP(S) port, rather than the default of always listening on 8080, which
     * may be in use. The utilised port can subsequently be discovered using wireMockRule.port() and httpsPort().
     */
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

    AbstractSFApiTest() {
        this.config = loadApplicationConfiguration();
    }

    @Before
    public void setUp() throws Exception {
        initRestAssured();
        this.cloudConfig = new CloudConfig();

    }

    @After
    public void tearDown() {
        WireMock.reset();
    }

    /**
     * @return The Logger for this class.
     */
    protected abstract Logger getLogger();

    private ImmutableConfiguration loadApplicationConfiguration() {
        try {
            CombinedConfiguration config = new Configurations().combined("config.xml");
            return ConfigurationUtils.unmodifiableConfiguration(config);
        } catch (ConfigurationException e) {
            throw new RuntimeException("Error loading application configuration. Cause: " + e.toString(), e);
        }
    }

    private void initRestAssured() {
        RestAssured.basePath = SFApiTestConstants.RELEX_TRANSFER_SERVICE_PATH;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        boolean alwaysLogRequestAndResponse = this.config.getBoolean(ALWAYS_LOG_REQ_AND_RESP_PROPERTY, Boolean.TRUE);
        initRestAssuredResponseDefaults(alwaysLogRequestAndResponse);
    }

    private void initRestAssuredResponseDefaults(boolean alwaysLogRequestAndResponse) {
        if (alwaysLogRequestAndResponse) {
            // RestAssured doesn't support enabling logging using a ResponseSpecification in the following expected way -
            //   ResponseSpecification responseSpec = new ResponseSpecBuilder().build().log().all();
            //   RestAssured.responseSpecification = responseSpec;
            // So, instead, enable response logging by adding a filter to the default list of filters.
            RestAssured.filters(new ResponseLoggingFilter());
        }
    }




    /**
     * @return A valid, unique realm name.
     */
    static String generateUniqueRealmName() {
        return "realm-" + UUID.randomUUID();
    }


    static String generateRandomAlphabeticString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * @return A valid realm key.
     */
    static String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public ImmutableConfiguration getConfig() {
        return config;
    }

    public CloudConfig getCloudConfig() {
        return cloudConfig;
    }
}
