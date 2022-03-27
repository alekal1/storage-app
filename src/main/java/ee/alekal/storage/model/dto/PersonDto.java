package ee.alekal.storage.model.dto;

import ee.alekal.storage.model.ProfileType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDto {

    private String username;
    private String password;
    private String representativeUsername;
    private String profileType;

    public static PersonDtoBuilder builder() {
        return new PersonDtoBuilder();
    }

    public static class PersonDtoBuilder {
        private String username;
        private String password;
        private String representativeUsername;
        private String profileType;

        public PersonDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public PersonDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public PersonDtoBuilder representativeUsername(String username) {
            this.representativeUsername = username;
            return this;
        }

        public PersonDtoBuilder profileType(ProfileType profileType) {
            this.profileType = profileType.value;
            return this;
        }

        public PersonDto build() {
            var personDto = new PersonDto();
            personDto.username = username;
            personDto.password = password;
            personDto.representativeUsername = representativeUsername;
            personDto.profileType = profileType;
            return personDto;
        }
    }
}
