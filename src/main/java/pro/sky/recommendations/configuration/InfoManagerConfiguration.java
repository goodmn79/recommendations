package pro.sky.recommendations.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pro.sky.recommendations.dto.InfoManager;

@Configuration
@PropertySource("classpath:build-info.properties")
public class InfoManagerConfiguration {

    @Bean
    public InfoManager infoManager() {
        return new InfoManager();
    }
}
