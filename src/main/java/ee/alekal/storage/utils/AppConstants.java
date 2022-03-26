package ee.alekal.storage.utils;

public class AppConstants {
    // General
    private static final String API_BASE_PATH = "/api";
    public static final String PERSON_API_PATH = API_BASE_PATH + "/person";
    public static final String ITEM_API_PATH = API_BASE_PATH + "/item";


    // DB
    public static final String PERSON_SEQUENCE = "person_sequence";
    public static final String ITEM_SEQUENCE = "item_sequence";
    public static final int DEFAULT_ALLOCATION_SIZE = 1;

    // Exceptions
    public static final String REPRESENTATIVE_EXCEPTION_MSG
            = "Representative with given username was not found!";
    public static final String USER_IS_NOT_REGISTERED_MSG
            = "User is not registered!";
    public static final String USER_IS_ALREADY_REGISTERED_MSG
            = "User is already registered!";
}
