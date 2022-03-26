package ee.alekal.storage.exception;

import static ee.alekal.storage.utils.AppConstants.USER_IS_ALREADY_REGISTERED_MSG;

public class UserAlreadyRegisteredException extends IllegalArgumentException {

    public UserAlreadyRegisteredException() {
        super(USER_IS_ALREADY_REGISTERED_MSG);
    }
}
