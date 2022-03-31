package ee.alekal.storage.service.validation;

import ee.alekal.storage.exception.item.ItemSizeIsBiggerThanItsParentException;
import ee.alekal.storage.exception.item.ItemSizeIsZeroOrLessException;
import ee.alekal.storage.model.dto.ItemDto;
import ee.alekal.storage.model.jpa.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Service
public class ItemValidator {

    public void checkItemSize(Item parentItem, ItemDto itemDto) {
        if (itemDto.getSize().longValue() > parentItem.getSize().longValue()) {
            log.info("Sub item's size is bigger than parent item's size");
            throw new ItemSizeIsBiggerThanItsParentException();
        }
        checkIfSizeNotZeroOrLess(itemDto);
    }

    public void checkIfSizeNotZeroOrLess(ItemDto itemDto) {
        if (itemDto.getSize().equals(BigInteger.ZERO)
                || itemDto.getSize().signum() != 1) {
            log.info("Sub item's size cannot be zero or zero than less");
            throw new ItemSizeIsZeroOrLessException();
        }
    }
}
