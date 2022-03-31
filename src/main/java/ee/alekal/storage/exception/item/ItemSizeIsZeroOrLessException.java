package ee.alekal.storage.exception.item;

import static ee.alekal.storage.utils.AppConstants.ITEM_SIZE_IS_ZERO_OR_LESS_MSG;

public class ItemSizeIsZeroOrLessException extends IllegalArgumentException {
    public ItemSizeIsZeroOrLessException() {
        super(ITEM_SIZE_IS_ZERO_OR_LESS_MSG);
    }
}
