package ee.alekal.storage.config;

import ee.alekal.storage.mapper.AppMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig {

    @Bean
    public AppMapper personMapper() {
        return Mappers.getMapper(AppMapper.class);
    }
}
