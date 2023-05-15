package ru.jekajops.wbtablemapper.services;

import org.springframework.stereotype.Service;
import ru.jekajops.wbtablemapper.models.Setting;
import ru.jekajops.wbtablemapper.repository.SettingsRepository;
import ru.jekajops.wbtablemapper.services.util.SettingsReader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SettingsService {
    private final SettingsReader settingsReader;
    private final SettingsRepository settingsRepository;

    public SettingsService(SettingsReader settingsReader, SettingsRepository settingsRepository) {
        this.settingsReader = settingsReader;
        this.settingsRepository = settingsRepository;
        settingsRepository.init();
    }

    public Map<String, String> findSettingsMap(Setting.Group group) {
        return settingsRepository.findByGroup(group)
                .stream()
                .collect(Collectors.toMap(Setting::getName, Setting::getValue));
    }

    public void updateSettings(Map<String, String> updateMap, Setting.Group group) {
        Map<String, Setting> currentMap = settingsRepository.findByGroup(group)
                .stream()
                .collect(Collectors.toMap(Setting::getName, s -> s));
        List<Setting> updated = updateMap.entrySet().stream().map(e -> {
            Setting setting = currentMap.get(e.getKey());
            setting.setValue(e.getValue());
            return setting;
        }).collect(Collectors.toList());
        settingsRepository.saveAllAndFlush(updated);
    }
}
