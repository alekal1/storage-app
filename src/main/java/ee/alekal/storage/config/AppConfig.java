package ee.alekal.storage.config;

import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.mapper.AppMapper;
import ee.alekal.storage.model.jpa.Person;
import ee.alekal.storage.utils.PasswordHelper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.Properties;

import static ee.alekal.storage.model.ProfileType.ADMIN;

@Configuration
@EnableSwagger2
public class AppConfig {

    private static final String ADMIN_PROP_FILE = "admin.properties";
    private static final String ADMIN_USERNAME_PROP = "username";
    private static final String ADMIN_PASSWORD_PROP = "password";

    @Bean
    public AppMapper personMapper() {
        return Mappers.getMapper(AppMapper.class);
    }

    @Bean
    public CommandLineRunner addAdminPerson(PersonRepository personRepository) {
        return args -> {
            var props = configureProps();
            var adminPerson = new Person();
            adminPerson.setUsername(props.getProperty(ADMIN_USERNAME_PROP));
            adminPerson.setPassword(PasswordHelper.encodePassword(props.getProperty(ADMIN_PASSWORD_PROP)));
            adminPerson.setProfileType(ADMIN.value);
            personRepository.saveAndFlush(adminPerson);
        };
    }

    public static Properties configureProps() {
        Properties props = new Properties();
        var inputStream = AppConfig.class.getClassLoader()
                .getResourceAsStream(ADMIN_PROP_FILE);
        try {
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
