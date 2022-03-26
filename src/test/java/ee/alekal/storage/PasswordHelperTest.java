package ee.alekal.storage;

import ee.alekal.storage.utils.PasswordHelper;
import org.junit.jupiter.api.Test;

import static ee.alekal.storage.utils.TestConstants.VALID_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordHelperTest {

    @Test
    public void verifyEncodedAndDecodedPasswordsAreTheSame() {
        assertTrue(PasswordHelper.encodedEqualsDecoded(VALID_PASSWORD));
    }
}
