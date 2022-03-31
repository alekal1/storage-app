package ee.alekal.storage.exception.item;

import static ee.alekal.storage.utils.AppConstants.SUB_ITEM_IS_BIGGER_THAN_PARENT_MSG;

public class ItemSizeIsBiggerThanItsParentException extends IllegalArgumentException {
    public ItemSizeIsBiggerThanItsParentException() {
        super(SUB_ITEM_IS_BIGGER_THAN_PARENT_MSG);
    }
}
