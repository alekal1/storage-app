package ee.alekal.storage.utils;

import java.util.Base64;

public class PasswordHelper {

    public static String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
