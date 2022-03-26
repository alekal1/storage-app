package ee.alekal.storage.service;

import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.model.ProfileType;
import ee.alekal.storage.model.dto.ErrorResponse;
import ee.alekal.storage.model.dto.PersonDto;
import ee.alekal.storage.model.jpa.Person;
import ee.alekal.storage.service.api.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static ee.alekal.storage.utils.TestConstants.INVALID_ENCODED_PASS;
import static ee.alekal.storage.utils.TestConstants.PERSON_PASSWORD;
import static ee.alekal.storage.utils.TestConstants.PERSON_USER_NAME;
import static ee.alekal.storage.utils.TestConstants.VALID_ENCODED_PASS;
import static ee.alekal.storage.utils.TestConstants.VALID_REPRESENTATIVE_NAME;
import static ee.alekal.storage.utils.TestConstants.INVALID_REPRESENTATIVE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersonServiceTest.Config.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void verifyReturnErrorResponseOnWrongRepresentativeName() {
        final var personDto = createInvalidDto();
        var response = personService.registerPerson(personDto);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        var errorResponse = (ErrorResponse) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getCode());
    }

    @Test
    public void verifyNoErrorsOnValidRepresentative() {
        final var personDto = createValidDto();
        var response = personService.registerPerson(personDto);
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void verifyUserCanLogin() {
        final var personDto = createValidDto();
        var response = personService.loginPerson(personDto);
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    public void verifyUserCannotLogin() {
        final var personDto = createInvalidDto();
        var response = personService.registerPerson(personDto);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        var errorResponse = (ErrorResponse) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getCode());
    }

    private PersonDto createValidDto() {
        return PersonDto.builder()
                .username(PERSON_USER_NAME)
                .password(PERSON_PASSWORD)
                .profileType(ProfileType.BUSINESS)
                .representativeUsername(VALID_REPRESENTATIVE_NAME)
                .build();
    }

    private PersonDto createInvalidDto() {
        return PersonDto.builder()
                .username(PERSON_USER_NAME)
                .password(PERSON_PASSWORD)
                .profileType(ProfileType.BUSINESS)
                .representativeUsername(INVALID_REPRESENTATIVE_NAME)
                .build();
    }


    @ComponentScan(basePackages = {
            "ee.alekal.storage.config",
            "ee.alekal.storage.service"
    })
    static class Config {
        @Bean
        public PersonRepository personRepository() {
            var mock = Mockito.mock(PersonRepository.class);
            when(mock.getByUsername(eq(INVALID_REPRESENTATIVE_NAME))).thenReturn(
                    Optional.empty()
            );
            when(mock.getByUsername(eq(VALID_REPRESENTATIVE_NAME))).thenReturn(
                    Optional.of(new Person())
            );

            when(mock.getByUsernameAndPassword(eq(PERSON_USER_NAME), eq(VALID_ENCODED_PASS)))
                    .thenReturn(Optional.of(new Person()));
            when(mock.getByUsernameAndPassword(eq(PERSON_USER_NAME), eq(INVALID_ENCODED_PASS)))
                    .thenReturn(
                            Optional.empty()
                    );
            return mock;
        }
    }
}
