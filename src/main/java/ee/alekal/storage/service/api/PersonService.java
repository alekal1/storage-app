package ee.alekal.storage.service.api;

import ee.alekal.storage.model.dto.PersonDto;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    ResponseEntity<?> loginPerson(PersonDto person);
    ResponseEntity<?> registerPerson(PersonDto person);
}
