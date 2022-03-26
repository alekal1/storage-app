package ee.alekal.storage.utils;

public class TestConstants {

    public static final String VALID_REPRESENTATIVE_NAME = "Valid";
    public static final String INVALID_REPRESENTATIVE_NAME = "Wrong";

    public static final String PERSON_USER_NAME = "Alex";
    public static final String PERSON_PASSWORD = "alekal";

    public static final String VALID_ENCODED_PASS = PasswordHelper.encodePassword(PERSON_PASSWORD);
    public static final String INVALID_ENCODED_PASS = PasswordHelper.encodePassword(PERSON_PASSWORD.concat("i"));

}
