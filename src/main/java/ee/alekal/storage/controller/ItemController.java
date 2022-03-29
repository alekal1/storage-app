package ee.alekal.storage.controller;

import ee.alekal.storage.model.dto.ItemDto;
import ee.alekal.storage.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ee.alekal.storage.utils.AppConstants.ITEM_API_PATH;

@RestController
@RequestMapping(path = ITEM_API_PATH)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}/subItems")
    public ResponseEntity<?> getSubItems(@PathVariable Long itemId) {
        return itemService.getSubItems(itemId);
    }

    @GetMapping("/{userName}/items")
    public ResponseEntity<?> getTopLevelItems(@PathVariable String userName) {
        return itemService.getTopLevelItems(userName);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> addItem(@PathVariable String userName,
                                     @RequestBody ItemDto itemDto) {
        return itemService.addItem(userName, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemSize(@PathVariable Long itemId) {
        return itemService.getItemSize(itemId);
    }

    @PostMapping("/{parentId}/{userName}")
    public ResponseEntity<?> addSubItem(@PathVariable Long parentId,
                                        @PathVariable String userName,
                                        @RequestBody ItemDto itemDto) {
        return itemService.addSubItem(userName, parentId, itemDto);
    }

    @DeleteMapping("/{itemId}/{userName}")
    public ResponseEntity<?> removeItem(@PathVariable Long itemId,
                                        @PathVariable String userName) {
        return itemService.removeItem(userName, itemId);
    }
}
