package ru.jekajops.wbtablemapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import ru.jekajops.wbtablemapper.repository.*;
import ru.jekajops.wbtablemapper.services.googlesheets.GoogleSheetsNuSeller;
import ru.jekajops.wbtablemapper.services.googlesheets.GoogleSheetsNuSellerMock;
import ru.jekajops.wbtablemapper.services.googlesheets.exception.InitiationException;
import ru.jekajops.wbtablemapper.util.AppProperties;

public class WBTableMapperConfig {

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public GoogleSheetsNuSeller googleSheetsNuSeller(AppProperties appProperties) throws InitiationException {
        try {
            return GoogleSheetsNuSeller.builder()
                    .appProperties(appProperties)
                    .build();
        } catch (Throwable t) {
            return GoogleSheetsNuSellerMock.builder()
                    .appProperties(appProperties)
                    .build();
        }
    }

    @Bean
    public NuSellerRepository nuSellerRepository() {
        return new NuSellerRepositoryMock();
    }

    @Bean
    public WBRepository wbRepository() {
        return new WBREpositoryMock();
    }

    @Bean
    public SettingsRepository settingsRepository() {
        return new SettingsRepositoryMock();
    }
}
