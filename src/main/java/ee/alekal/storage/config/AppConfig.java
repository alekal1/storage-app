package ee.alekal.storage.config;

import ee.alekal.storage.dao.ItemRepository;
import ee.alekal.storage.dao.PersonRepository;
import ee.alekal.storage.mapper.AppMapper;
import ee.alekal.storage.model.jpa.Item;
import ee.alekal.storage.model.jpa.Person;
import ee.alekal.storage.utils.PasswordHelper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Properties;

import static ee.alekal.storage.model.ProfileType.ADMIN;
import static ee.alekal.storage.utils.AppConstants.ADMIN_PASSWORD_PROP;
import static ee.alekal.storage.utils.AppConstants.ADMIN_PROP_FILE;
import static ee.alekal.storage.utils.AppConstants.ADMIN_USERNAME_PROP;

@Configuration
@EnableSwagger2
public class AppConfig extends WebMvcAutoConfiguration {

    @Bean
    public AppMapper personMapper() {
        return Mappers.getMapper(AppMapper.class);
    }

    @Bean
    public CommandLineRunner addAdminPerson(PersonRepository personRepository,
                                            ItemRepository itemRepository) {
        return args -> {
            var props = configureProps();
            var adminPerson = new Person();
            adminPerson.setUsername(props.getProperty(ADMIN_USERNAME_PROP));
            adminPerson.setPassword(PasswordHelper.encodePassword(
                    props.getProperty(ADMIN_PASSWORD_PROP)));
            adminPerson.setProfileType(ADMIN.value);
            personRepository.saveAndFlush(adminPerson);

            for (int i = 0; i < 2; i++) {
                var item = new Item();
                item.setPerson(adminPerson);
                item.setColor("RED");
                item.setLastAccessedOn(LocalDate.now());
                item.setSize(BigInteger.TEN);
                item.setName("Box-" + i);
                item.setSerialNumber("abc-def-12" + i);
                item.setPicturePath("" +
                        "https://i.pinimg.com/originals/59/54/b4/5954b408c66525ad932faa693a647e3f.jpg");
                itemRepository.saveAndFlush(item);
            }

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
