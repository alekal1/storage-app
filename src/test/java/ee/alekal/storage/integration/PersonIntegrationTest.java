package ee.alekal.storage.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.alekal.storage.model.ProfileType;
import ee.alekal.storage.model.dto.PersonDto;
import org.junit.jupiter.api.MethodOrderer;
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

import static ee.alekal.storage.utils.AppConstants.PERSON_API_PATH;
import static ee.alekal.storage.utils.DtoHelper.createInvalidBusinessDto;
import static ee.alekal.storage.utils.DtoHelper.createValidPrivateDto;
import static ee.alekal.storage.utils.TestConstants.VALID_USERNAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final PersonDto validDto = createValidPrivateDto();

    @Test
    @Order(1)
    public void userCanRegisterSuccessfully() throws Exception {
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/register"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validDto)))
            .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void secondUserCanRegisterSuccessfully() throws Exception {
        final var validSecondDto = PersonDto.builder()
                .username("SECOND")
                .password("SECOND")
                .profileType(ProfileType.PRIVATE)
                .build();
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSecondDto)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void userCanLoginSuccessfully() throws Exception {
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/login"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validDto)))
            .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void userCannotRegisterSecondTime() throws Exception {
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void nonRegisteredUserCannotLogin() throws Exception {
        final var invalidDto = createInvalidBusinessDto();
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/login"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void businessUserCannotRegisterIfRepresentativeNotExists() throws Exception {
        final var invalidDto = createInvalidBusinessDto();
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void validBusinessClientCanRegister() throws Exception {
        final var validBusinessDto = PersonDto.builder()
                .username("BUSINESS")
                .password("PASS")
                .profileType(ProfileType.BUSINESS)
                .representativeUsername(VALID_USERNAME)
                .build();
        this.mockMvc.perform(post(PERSON_API_PATH.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBusinessDto)))
                .andExpect(status().isOk());
    }
}
