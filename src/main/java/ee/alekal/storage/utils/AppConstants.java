package ee.alekal.storage.utils;

import ee.alekal.storage.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

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
    public static final String ITEM_IS_NOT_EMPTY_MSG
            = "Item is not empty! You can remove only empty items";
    public static final String SUB_ITEM_IS_BIGGER_THAN_PARENT_MSG
            = "Sub item's size is bigger that it's parent size";
    public static final String ITEM_SIZE_IS_ZERO_MSG
            = "Item's size cannot be zero!";
    public static final String ITEM_NOT_EXIST
            = "Item not exists!";


    // Response
    public static ResponseEntity<ErrorResponse> errorResponse(String message) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .uuid(UUID.randomUUID().toString())
                        .message(message)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
