package ee.alekal.storage.utils;

public class TestConstants {

    public static final String VALID_REPRESENTATIVE_NAME = "Valid";
    public static final String INVALID_REPRESENTATIVE_NAME = "Wrong";

    public static final String VALID_USERNAME = "Alex";
    public static final String INVALID_USERNAME = "Mock";

    public static final String VALID_PASSWORD = "alekal";
    public static final String INVALID_PASSWORD = "Mo";
    public static final String VALID_ENCODED_PASS = PasswordHelper.encodePassword(VALID_PASSWORD);
    public static final String INVALID_ENCODED_PASS = PasswordHelper.encodePassword(INVALID_PASSWORD);

}
