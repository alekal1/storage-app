package ee.alekal.storage.service.validation;

import ee.alekal.storage.exception.ItemSizeIsBiggerThanItsParentException;
import ee.alekal.storage.model.dto.ItemDto;
import ee.alekal.storage.model.jpa.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ItemValidator {

    public void checkItemSize(Item parentItem, ItemDto itemDto) {
        if (itemDto.getSize().longValue() > parentItem.getSize().longValue()) {
            log.info("Sub item's size is bigger than parent item's size");
            throw new ItemSizeIsBiggerThanItsParentException();
        }
    }
}
