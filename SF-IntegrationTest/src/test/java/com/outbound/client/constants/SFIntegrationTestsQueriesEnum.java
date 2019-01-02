package com.outbound.client.constants;

public enum SFIntegrationTestsQueriesEnum {

    SELECT_FROM_SFRELEX_DAILY("SELECT * FROM `np-sales-forecasting.relex_fcst_output.SF_RLX_DLY` where LOCATION_CODE =‘0527’"),

    SELECT_FROM_SFRELEX_DAILY_BY_LOCATION_CODE(
            "SELECT * FROM `np-sales-forecasting.relex_fcst_output.SF_RLX_DLY` where LOCATION_CODE = `%1$s`");


    private String sql;

    SFIntegrationTestsQueriesEnum(String sql){
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
