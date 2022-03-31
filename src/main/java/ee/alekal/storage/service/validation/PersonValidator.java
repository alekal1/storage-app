package ee.alekal.storage.service.validation;

import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.exception.person.RepresentativeIsNotFoundException;
import ee.alekal.storage.exception.person.UserAlreadyRegisteredException;
import ee.alekal.storage.exception.person.UserIsNotRegisteredException;
import ee.alekal.storage.model.Action;
import ee.alekal.storage.model.dto.PersonDto;
import ee.alekal.storage.utils.PasswordHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ee.alekal.storage.model.Action.LOGIN;
import static ee.alekal.storage.model.ProfileType.BUSINESS;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonValidator {

    private final PersonRepository personRepository;

    public void checkPersonsAction(PersonDto personDto, Action action) throws UserIsNotRegisteredException {
        log.info("Checking action {}", action.name());
        var encodedPassword = PasswordHelper.encodePassword(personDto.getPassword());
        var person = personRepository.findByUsernameAndPassword(
                personDto.getUsername(), encodedPassword
        );
        if (LOGIN.equals(action)) {
            if (person.isEmpty()) {
                throw new UserIsNotRegisteredException();
            } else {
                personDto.setProfileType(person.get().getProfileType());
            }
        } else {
            if (person.isPresent()) {
                throw new UserAlreadyRegisteredException();
            } else {
                checkIfRepresentativeAssigned(personDto);
            }
        }
    }

    private void checkIfRepresentativeAssigned(PersonDto personDto) throws RepresentativeIsNotFoundException {
        if (BUSINESS.value.equalsIgnoreCase(personDto.getProfileType())) {
            var repr = personRepository.findByUsername(
                    personDto.getRepresentativeUsername());

            if (repr.isEmpty()) {
                throw new RepresentativeIsNotFoundException();
            }
        }
    }
}
