package ee.alekal.storage.controller;

import ee.alekal.storage.model.dto.PersonDto;
import ee.alekal.storage.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ee.alekal.storage.utils.AppConstants.PERSON_API_PATH;

@RestController
@RequestMapping(path = PERSON_API_PATH)
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<?> loginPerson(@RequestBody PersonDto person) {
        return personService.loginPerson(person);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerPerson(@RequestBody PersonDto person) {
        return personService.registerPerson(person);
    }

}
