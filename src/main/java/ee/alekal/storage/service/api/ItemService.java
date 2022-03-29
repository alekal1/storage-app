package ee.alekal.storage.service.api;

import ee.alekal.storage.model.dto.ItemDto;
import org.springframework.http.ResponseEntity;

public interface ItemService {

    ResponseEntity<?> addItem(String personUsername, ItemDto itemDto);
    ResponseEntity<?> addSubItem(String personUsername, Long parentItemId, ItemDto itemDto);
    ResponseEntity<?> removeItem(String personUsername, Long itemId);
    ResponseEntity<?> getTopLevelItems(String userName);
    ResponseEntity<?> getSubItems(Long itemId);
    ResponseEntity<?> getItemSize(Long itemId);
}
