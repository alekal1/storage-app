package ee.alekal.storage.service.api;

import ee.alekal.storage.dao.ItemRepository;
import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.exception.item.ItemSizeIsBiggerThanItsParentException;
import ee.alekal.storage.exception.item.ItemSizeIsZeroOrLessException;
import ee.alekal.storage.mapper.AppMapper;
import ee.alekal.storage.model.dto.ItemDto;
import ee.alekal.storage.model.jpa.Item;
import ee.alekal.storage.service.validation.ItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ee.alekal.storage.utils.AppConstants.ITEM_IS_NOT_EMPTY_MSG;
import static ee.alekal.storage.utils.AppConstants.ITEM_NOT_EXIST;
import static ee.alekal.storage.utils.AppConstants.NO_ITEM_FOUND_WITH_GIVEN_SEARCH_QUERY;
import static ee.alekal.storage.utils.AppConstants.SEPARATOR;
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
        log.info("addItem, user={}, itemDto={}",
                personUsername, itemDto);
        try {
            itemValidator.checkIfSizeNotZeroOrLess(itemDto);
        } catch (ItemSizeIsZeroOrLessException e) {
            return errorResponse(e.getMessage());
        }

        var item = appMapper.itemDtoTiEntity(itemDto);
        var person = personRepository.findByUsername(personUsername);

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
        log.info("addSubItem, user={}, parentItemId={}, itemDto={}",
                personUsername, parentItemId, itemDto);
        var parentItem = itemRepository.getById(parentItemId);
        var item = appMapper.itemDtoTiEntity(itemDto);
        var person = personRepository.findByUsername(personUsername);
        if (person.isEmpty()) {
            return errorResponse(USER_IS_NOT_REGISTERED_MSG);
        }
        item.setPerson(person.get());
        item.setParentItem(parentItem);
        item.setLastAccessedOn(LocalDate.now());

        try {
            itemValidator.checkItemSize(parentItem, itemDto);
            parentItem.setSize(parentItem.getSize().subtract(itemDto.getSize()));
            itemRepository.saveAndFlush(item);

            return ResponseEntity.ok().build();
        } catch (ItemSizeIsBiggerThanItsParentException | ItemSizeIsZeroOrLessException e) {
            return errorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> removeItem(String personUsername, Long itemId) {
        log.info("removeItem, user={}, itemId={}",
                personUsername, itemId);
        var person = personRepository.findByUsername(personUsername);
        if (person.isEmpty()) {
            return errorResponse(USER_IS_NOT_REGISTERED_MSG);
        }

        var subItems = itemRepository.findAllByParentItemId(itemId);
        if (!subItems.isEmpty()) {
            return errorResponse(ITEM_IS_NOT_EMPTY_MSG);
        }

        var item = itemRepository.getById(itemId);

        if (item.getParentItem() != null) {
            handleItemSize(item);
        }

        itemRepository.deleteById(itemId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getTopLevelItems(String userName) {
        log.info("getTopLevelItems, user={}",
                userName);
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
        log.info("getSubItems, itemId={}", itemId);
        var items = itemRepository.findAllByParentItemId(itemId);
        items.forEach(item -> item.setLastAccessedOn(LocalDate.now()));
        return ResponseEntity.ok(items);
    }

    @Override
    public ResponseEntity<?> getItemSize(Long itemId) {
        log.debug("getItemSize, itemId={}", itemId);
        var item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            return errorResponse(ITEM_NOT_EXIST);
        }
        return ResponseEntity.ok(item.get().getSize());
    }

    @Override
    public ResponseEntity<?> searchItem(String personUsername, String searchQuery) {
        log.info("searchItem, user={}, query={}", personUsername, searchQuery);
        var allPersonsItems =
                itemRepository.getAllBySearchQueryAndUsername(personUsername, searchQuery.toLowerCase());
        if (allPersonsItems.isEmpty()) {
            return errorResponse(NO_ITEM_FOUND_WITH_GIVEN_SEARCH_QUERY);
        }

        log.info("Got {} for given search query={}",
                allPersonsItems.size(), searchQuery);
        var paths = new ArrayList<String>();
        allPersonsItems.forEach(item -> paths.add(createItemPath(item, item.getName())));
        return ResponseEntity.ok(paths);
    }

    private String createItemPath(Item item, String path) {
        if (item.getParentItem() == null) {
            return reversePath(path);
        }
        var newPath = path.concat(SEPARATOR).concat(item.getParentItem().getName());
        return createItemPath(item.getParentItem(), newPath);
    }

    private String reversePath(String path) {
        log.info("Reversing {}", path);
        var pathSplit = path.split(SEPARATOR);
        var pathBuilder = new StringBuilder();
        for (int i = pathSplit.length - 1; i >= 0; i--) {
            pathBuilder.append(pathSplit[i].concat(SEPARATOR));
        }
        log.info("After reverse {}", pathBuilder.toString());
        return pathBuilder.toString();
    }

    private void handleItemSize(Item item) {
        var currSize = item.getSize();
        var parentItem = item.getParentItem();
        parentItem.setSize(parentItem.getSize().add(currSize));
    }

}
