package ee.alekal.storage.mapper;

import ee.alekal.storage.model.dto.PersonDto;
import ee.alekal.storage.model.jpa.Person;
import org.mapstruct.Mapper;

@Mapper
public interface AppMapper {

    Person personDtoToEntity(PersonDto personDto);
}
