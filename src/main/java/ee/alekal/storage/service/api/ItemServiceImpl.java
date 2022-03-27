package ee.alekal.storage.service.api;

import ee.alekal.storage.dao.ItemRepository;
import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.exception.ItemSizeIsBiggerThanItsParentException;
import ee.alekal.storage.mapper.AppMapper;
import ee.alekal.storage.model.dto.ItemDto;
import ee.alekal.storage.model.jpa.Item;
import ee.alekal.storage.service.validation.ItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ee.alekal.storage.utils.AppConstants.ITEM_IS_NOT_EMPTY_MSG;
import static ee.alekal.storage.utils.AppConstants.USER_IS_NOT_REGISTERED_MSG;
import static ee.alekal.storage.utils.AppConstants.errorResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final PersonRepository personRepository;
    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;
    private final AppMapper appMapper;

    @Override
    public ResponseEntity<?> addItem(String personUsername, ItemDto itemDto) {
        var item = appMapper.itemDtoTiEntity(itemDto);
        var person = personRepository.getByUsername(personUsername);

        if (person.isEmpty()) {
            return errorResponse(USER_IS_NOT_REGISTERED_MSG);
        }

        item.setPerson(person.get());
        item.setLastAccessedOn(LocalDate.now());
        itemRepository.saveAndFlush(item);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> addSubItem(String personUsername, Long parentItemId, ItemDto itemDto) {
        var parentItem = itemRepository.getById(parentItemId);
        var item = appMapper.itemDtoTiEntity(itemDto);
        var person = personRepository.getByUsername(personUsername);
        if (person.isEmpty()) {
            return errorResponse(USER_IS_NOT_REGISTERED_MSG);
        }
        item.setPerson(person.get());
        item.setParentItem(parentItem);
        item.setLastAccessedOn(LocalDate.now());

        log.info("Current parent item size={}", item.getSize());

        try {
            itemValidator.checkItemSize(parentItem, itemDto);
            parentItem.setSize(parentItem.getSize().subtract(itemDto.getSize()));
            log.info("Decreasing parent item size={}", item.getSize());

            itemRepository.saveAndFlush(item);
            return ResponseEntity.ok().build();
        } catch (ItemSizeIsBiggerThanItsParentException e) {
            return errorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> removeItem(String personUsername, Long itemId) {
        var person = personRepository.getByUsername(personUsername);
        if (person.isEmpty()) {
            return errorResponse(USER_IS_NOT_REGISTERED_MSG);
        }

        var subItems = itemRepository.findAllByParentItemId(itemId);
        if (!subItems.isEmpty()) {
            return errorResponse(ITEM_IS_NOT_EMPTY_MSG);
        }
        log.info("Got subItems for id={}, {}", itemId, subItems);

        itemRepository.deleteById(itemId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getTopLevelItems(String userName) {
        var items = itemRepository.findAll();
        Predicate<Item> nullParent = item -> item.getParentItem() == null;
        Predicate<Item> sameUserName = item -> userName.equals(item.getPerson().getUsername());
        if (items.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        var results = items.stream()
                .filter(nullParent.and(sameUserName))
                .collect(Collectors.toList());
        results.forEach(item -> item.setLastAccessedOn(LocalDate.now()));

        return ResponseEntity.ok(
               results
        );
    }

    @Override
    public ResponseEntity<?> getSubItems(Long itemId) {
        var items = itemRepository.findAllByParentItemId(itemId);
        items.forEach(item -> item.setLastAccessedOn(LocalDate.now()));
        log.info("Got sub items {}, for id={}", items, itemId);
        return ResponseEntity.ok(items);
    }


}
