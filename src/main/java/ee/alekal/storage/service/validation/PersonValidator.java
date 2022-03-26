package ee.alekal.storage.service.validation;

import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.exception.RepresentativeIsNotFoundException;
import ee.alekal.storage.exception.UserIsNotRegisteredException;
import ee.alekal.storage.model.dto.PersonDto;
import ee.alekal.storage.utils.PasswordHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.alekal.storage.model.ProfileType.BUSINESS;

@Service
@RequiredArgsConstructor
public class PersonValidator {

    private final PersonRepository personRepository;

    public void checkIfRepresentativeAssigned(PersonDto personDto) throws RepresentativeIsNotFoundException {
        if (BUSINESS.value.equalsIgnoreCase(personDto.getProfileType())) {
            var repr = personRepository.getByUsername(
                    personDto.getRepresentativeUsername());

            if (repr.isEmpty()) {
                throw new RepresentativeIsNotFoundException();
            }
        }
    }

    public void checkIfPersonRegistered(PersonDto personDto) throws UserIsNotRegisteredException {
        var encodedPassword = PasswordHelper.encodePassword(personDto.getPassword());
        var person = personRepository.getByUsernameAndPassword(
                personDto.getUsername(), encodedPassword
        );
        if (person.isEmpty()) {
            throw new UserIsNotRegisteredException();
        }
    }
}
