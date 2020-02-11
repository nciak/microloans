package pl.twino.microloan;

import java.time.LocalTime;

public class Constants {

    public static final String LOAN_APPLICATION_ENTITY_TABLE_NAME = "loanapplication";
    public static final String LOAN_APPLICATION_SEQUENCE_NAME = "loanapplication_seq";

    public static final String LOAN_GRANTED_ENTITY_TABLE_NAME = "loangranted";
    public static final String LOAN_GRANTED_SEQUENCE_NAME = "loangranted_seq";

    public static final String CUSTOMER_ENTITY_TABLE_NAME = "customer";
    public static final String CUSTOMER_SEQUENCE_NAME = "customer_seq";


    public static final LocalTime RISK_HOURS_START = LocalTime.parse( "00:00:00" );
    public static final LocalTime RISK_HOURS_STOP = LocalTime.parse( "06:00:00" );

    public static final double MAX_LOAN_AMOUNT = 5000;
    public static final int MAX_NUMBER_OF_ATTEMPTS = 3;
    public static final long LOAN_DELAY_DAYS = 14;
}
