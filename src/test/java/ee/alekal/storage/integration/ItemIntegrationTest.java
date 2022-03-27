package ee.alekal.storage.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.alekal.storage.dao.ItemRepository;
import ee.alekal.storage.model.dto.ItemDto;
import ee.alekal.storage.model.dto.PersonDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;

import static ee.alekal.storage.utils.AppConstants.ITEM_API_PATH;
import static ee.alekal.storage.utils.AppConstants.PERSON_API_PATH;
import static ee.alekal.storage.utils.DtoHelper.createValidPrivateDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final PersonDto validDto = createValidPrivateDto("NEW");

    @Test
    @Order(1)
    public void userCanAddItemSuccessfully() throws Exception {
        registerUser();
        final var itemDto = ItemDto.builder()
                .color("RED")
                .name("Box-1")
                .picturePath("picture")
                .serialNumber("123-1255-gasd")
                .size(BigInteger.TEN)
                .build();

        this.mockMvc.perform(post(ITEM_API_PATH.concat("/").concat(validDto.getUsername()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(itemDto)))
            .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void nonRegisteredUserCannotAddItem() throws Exception {
        final var itemDto = ItemDto.builder()
                .color("RED")
                .name("Box-1")
                .picturePath("picture")
                .serialNumber("123-1255-gasd")
                .size(BigInteger.TEN)
                .build();
        this.mockMvc.perform(post(ITEM_API_PATH.concat("/").concat("nonRegistered"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isBadRequest());
    }

    @RepeatedTest(3)
    @Order(3)
    public void userCanAddSubItems() throws Exception {
        var parentItem = itemRepository.getById(1L);
        final var subItem = ItemDto.builder()
                .color("BLUE")
                .name("Sub-Box-1")
                .picturePath("picture")
                .serialNumber("aaa-aaaa-aaa")
                .size(BigInteger.ONE)
                .build();
        final var path = ITEM_API_PATH.concat("/")
                .concat(String.valueOf(parentItem.getId()))
                .concat("/")
                .concat(validDto.getUsername());
        this.mockMvc.perform(post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subItem)))
            .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void itemSizeDecreasedAfterAddingSubItems() {
        var item = itemRepository.getById(1L);
        assertEquals(new BigInteger(String.valueOf(7L)), item.getSize());
    }

    @Test
    @Order(5)
    public void cannotAddSubItemWithBiggerSize() throws Exception {
        var parentItem = itemRepository.getById(1L);
        System.out.println(parentItem.getSize());
        final var subItem = ItemDto.builder()
                .color("BLUE")
                .name("Sub-Box-1")
                .picturePath("picture")
                .serialNumber("aaa-aaaa-aaa")
                .size(BigInteger.TEN)
                .build();
        final var path = ITEM_API_PATH.concat("/")
                .concat(String.valueOf(parentItem.getId()))
                .concat("/")
                .concat(validDto.getUsername());
        this.mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void cannotDeleteNonEmptyItem() throws Exception {
        var parentItem = itemRepository.getById(1L);
        final var path = ITEM_API_PATH.concat("/")
                .concat(String.valueOf(parentItem.getId()))
                .concat("/")
                .concat(validDto.getUsername());
        this.mockMvc.perform(delete(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void canGetTopLevelItem() throws Exception {
        final var path = ITEM_API_PATH.concat("/")
                .concat(validDto.getUsername())
                .concat("/")
                .concat("items");
        var results = this.mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(results.getResponse());
    }

    @Test
    @Order(8)
    public void canGetSubItems() throws Exception {
        var item = itemRepository.getById(1L);
        final var path = ITEM_API_PATH.concat("/")
                .concat(String.valueOf(item.getId()))
                .concat("/subItems");
        var results = this.mockMvc.perform(get(path)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        assertNotNull(results.getResponse());
    }

    private void registerUser() throws Exception {
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isOk());
    }
}
