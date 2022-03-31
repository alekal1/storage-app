package ee.alekal.storage.exception.person;

import static ee.alekal.storage.utils.AppConstants.USER_IS_NOT_REGISTERED_MSG;

public class UserIsNotRegisteredException extends IllegalArgumentException {

    public UserIsNotRegisteredException() {
        super(USER_IS_NOT_REGISTERED_MSG);
    }

}
