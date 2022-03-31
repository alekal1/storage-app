package ee.alekal.storage;

import ee.alekal.storage.utils.PasswordHelper;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static ee.alekal.storage.utils.TestConstants.VALID_ENCODED_PASS;
import static ee.alekal.storage.utils.TestConstants.VALID_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordHelperTest {

    @Test
    public void verifyEncodedAndDecodedPasswordsAreTheSame() {
        var encodedPass = PasswordHelper.encodePassword(VALID_PASSWORD);
        assertNotEquals(encodedPass, VALID_PASSWORD);
    }
}
