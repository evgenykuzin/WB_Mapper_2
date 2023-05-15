package ru.jekajops.wbtablemapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jekajops.wbtablemapper.models.Setting;

import java.util.List;
import java.util.Set;

@Repository
public interface SettingsRepository extends JpaRepository<Setting, Long> {
    default void init() {
        initDefault(Setting.Group.COST_PRICE, "0");
        initDefault(Setting.Group.ADVERTISEMENT, "0");
    }

    private void initDefault(Setting.Group group, String dfltValue) {
        boolean settingsNotExists = findByGroup(group).isEmpty();
        if (settingsNotExists) {
            saveAllAndFlush(Set.of(
                    setting("Пакеты для вакууматора", group, dfltValue),
                    setting("Дарсонваль", group, dfltValue),
                    setting("Шампуни", group, dfltValue),
                    setting("Умные светильники", group, dfltValue),
                    setting("Упаковщики вакуумные", group, dfltValue),
                    setting("Внешние аккумуляторы", group, dfltValue)
            ));
        }
    }

    private Setting setting(String stngName, Setting.Group group, String dfltValue) {
        return Setting.builder()
                .name(stngName)
                .value(dfltValue)
                .group(group)
                .build();
    }

    List<Setting> findByGroup(Setting.Group group);

}
