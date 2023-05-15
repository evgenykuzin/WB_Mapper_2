package ru.jekajops.wbtablemapper.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jekajops.wbtablemapper.models.Setting;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSettingsFormData {
    List<Setting> settings;
}
