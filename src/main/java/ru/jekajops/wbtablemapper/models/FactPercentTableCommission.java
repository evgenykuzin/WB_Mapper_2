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
public class FactPercentTableCommission {
    @Column("Комиссия ВБ Факт")
    String fact;
    @Column("Комиссия ВБ %")
    String percent;
}
