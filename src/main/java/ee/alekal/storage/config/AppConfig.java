package ee.alekal.storage.config;

import ee.alekal.storage.mapper.AppMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AppMapper personMapper() {
        return Mappers.getMapper(AppMapper.class);
    }
}
