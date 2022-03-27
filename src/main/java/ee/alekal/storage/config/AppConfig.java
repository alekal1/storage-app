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

    @Bean
    public AppMapper personMapper() {
        return Mappers.getMapper(AppMapper.class);
    }

    @Bean
    public CommandLineRunner addAdminPerson(PersonRepository personRepository) {
        return args -> {
            var props = configureProps();
            var adminPerson = new Person();
            adminPerson.setUsername(props.getProperty("username"));
            adminPerson.setPassword(PasswordHelper.encodePassword(props.getProperty("password")));
            adminPerson.setProfileType(ADMIN.value);
            personRepository.saveAndFlush(adminPerson);
        };
    }

    public static Properties configureProps() {
        Properties props = new Properties();
        var inputStream = AppConfig.class.getClassLoader()
                .getResourceAsStream("admin.properties");
        try {
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
