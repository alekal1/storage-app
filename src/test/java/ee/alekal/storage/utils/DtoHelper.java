package ee.alekal.storage.utils;

import ee.alekal.storage.model.ProfileType;
import ee.alekal.storage.model.dto.PersonDto;

import static ee.alekal.storage.utils.TestConstants.INVALID_PASSWORD;
import static ee.alekal.storage.utils.TestConstants.INVALID_REPRESENTATIVE_NAME;
import static ee.alekal.storage.utils.TestConstants.INVALID_USERNAME;
import static ee.alekal.storage.utils.TestConstants.VALID_PASSWORD;
import static ee.alekal.storage.utils.TestConstants.VALID_REPRESENTATIVE_NAME;
import static ee.alekal.storage.utils.TestConstants.VALID_USERNAME;

public class DtoHelper {

    public static PersonDto createValidBusinessDto() {
        return PersonDto.builder()
                .username(VALID_USERNAME)
                .password(VALID_PASSWORD)
                .profileType(ProfileType.BUSINESS)
                .representativeUsername(VALID_REPRESENTATIVE_NAME)
                .build();
    }

    public static PersonDto createValidPrivateDto() {
        return PersonDto.builder()
                .username(VALID_USERNAME)
                .password(VALID_PASSWORD)
                .profileType(ProfileType.PRIVATE)
                .representativeUsername(VALID_REPRESENTATIVE_NAME)
                .build();
    }

    public static PersonDto createValidPrivateDto(String name) {
        return PersonDto.builder()
                .username(name)
                .password(VALID_PASSWORD)
                .profileType(ProfileType.PRIVATE)
                .representativeUsername(VALID_REPRESENTATIVE_NAME)
                .build();
    }

    public static PersonDto createInvalidBusinessDto() {
        return PersonDto.builder()
                .username(INVALID_USERNAME)
                .password(INVALID_PASSWORD)
                .profileType(ProfileType.BUSINESS)
                .representativeUsername(INVALID_REPRESENTATIVE_NAME)
                .build();
    }
}
