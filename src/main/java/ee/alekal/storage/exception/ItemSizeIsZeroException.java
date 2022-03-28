package ee.alekal.storage.exception;

import static ee.alekal.storage.utils.AppConstants.ITEM_SIZE_IS_ZERO_MSG;

public class ItemSizeIsZeroException extends IllegalArgumentException {
    public ItemSizeIsZeroException() {
        super(ITEM_SIZE_IS_ZERO_MSG);
    }
}
