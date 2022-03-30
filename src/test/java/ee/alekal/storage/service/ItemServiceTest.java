package ee.alekal.storage.service;

import ee.alekal.storage.dao.ItemRepository;
import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.model.jpa.Item;
import ee.alekal.storage.service.api.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ItemServiceTest.Config.class)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void verifySearchItemReturnsCorrectly() {
        var itemPath = itemService.searchItem("", "last");
        assertEquals(List.of("ParentBox/SubItem-1/LastItem-1/"), itemPath.getBody());
    }

    @ComponentScan(basePackages = {
            "ee.alekal.storage.config",
            "ee.alekal.storage.service"
    })
    static class Config {

        @Bean
        public ItemRepository itemRepository() {
            var mock = Mockito.mock(ItemRepository.class);
            var lastItem = createItems();
            when(mock.getAllBySearchQueryAndUsername(anyString(), eq("last")))
                    .thenReturn(Collections.singletonList(lastItem));
            return mock;
        }

        @Bean
        public PersonRepository personRepository() {
            return Mockito.mock(PersonRepository.class);
        }

        private Item createItems() {
            var item = new Item();
            item.setId(1L);
            item.setName("ParentBox");

            var subItem = new Item();
            subItem.setId(2L);
            subItem.setName("SubItem-1");
            subItem.setParentItem(item);

            var lastItem = new Item();
            lastItem.setId(3L);
            lastItem.setName("LastItem-1");
            lastItem.setParentItem(subItem);
            return lastItem;
        }
    }
}
