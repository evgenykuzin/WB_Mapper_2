package ru.jekajops.wbtablemapper.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.jekajops.wbtablemapper.util.annotations.Column;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FactPercentTableStorageAnalitics {
    @Column("Хранение + Логистика Факт")
    String fact;
    @Column("Хранение + Логистика %")
    String percent;
}
