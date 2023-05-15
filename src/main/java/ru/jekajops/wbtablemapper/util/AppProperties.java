package ru.jekajops.wbtablemapper.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String wbFilePath = "tmp/wb.xlsx";
    private String remoteTableUrlGS = "https://docs.google.com/spreadsheets/d/1A75aZat38ikmucx8FXqOWGaOsWrqUUqQLYI82P9EZOA/edit#gid=0";
}
