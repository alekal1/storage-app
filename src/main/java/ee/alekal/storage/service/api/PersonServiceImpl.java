package ee.alekal.storage.service.api;

import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.exception.RepresentativeIsNotFoundException;
import ee.alekal.storage.exception.UserAlreadyRegisteredException;
import ee.alekal.storage.exception.UserIsNotRegisteredException;
import ee.alekal.storage.mapper.AppMapper;
import ee.alekal.storage.model.Action;
import ee.alekal.storage.model.dto.PersonDto;
import ee.alekal.storage.service.validation.PersonValidator;
import ee.alekal.storage.utils.PasswordHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static ee.alekal.storage.utils.AppConstants.errorResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonValidator personValidator;
    private final PersonRepository personRepository;
    private final AppMapper appMapper;

    @Override
    public ResponseEntity<?> loginPerson(PersonDto personDto) {
        try {
            log.info("loginPerson, validating person.");

            personValidator.checkPersonsAction(personDto, Action.LOGIN);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserIsNotRegisteredException e) {
            return errorResponse(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> registerPerson(PersonDto personDto) {
        try {
            log.info("registerPerson, validating person.");

            personValidator.checkPersonsAction(personDto, Action.REGISTER);

            var entity = appMapper.personDtoToEntity(personDto);
            entity.setPassword(PasswordHelper.encodePassword(personDto.getPassword()));
            personRepository.saveAndFlush(entity);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RepresentativeIsNotFoundException | UserAlreadyRegisteredException e) {
            return errorResponse(e.getMessage());
        }
    }

}
