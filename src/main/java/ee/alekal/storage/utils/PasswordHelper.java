package ee.alekal.storage.utils;

import java.util.Base64;

public class PasswordHelper {

    public static String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decodePassword(String password) {
        return new String(Base64.getDecoder().decode(password.getBytes()));
    }

    public static boolean encodedEqualsDecoded(String password) {
        var encoded = encodePassword(password);
        var decoded = decodePassword(encoded);
        return password.equals(decoded);
    }
}
