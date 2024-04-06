package com.pavlob;

public class Constant {

    public static final String BEARER_PREFIX = "Bearer ";

    public enum RandomlyCargoOperations {
        CREATE_NEW_REPORT,
        UPDATE_REPORT
    }

    public class Kafka {
        public static final String CARGO_REPORT_TOPIC = "cargoReportsTopic";
    }
}
