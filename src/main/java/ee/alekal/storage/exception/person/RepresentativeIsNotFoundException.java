package ee.alekal.storage.exception.person;

import static ee.alekal.storage.utils.AppConstants.REPRESENTATIVE_EXCEPTION_MSG;

public class RepresentativeIsNotFoundException extends IllegalArgumentException {

    public RepresentativeIsNotFoundException() {
        super(REPRESENTATIVE_EXCEPTION_MSG);
    }

}
