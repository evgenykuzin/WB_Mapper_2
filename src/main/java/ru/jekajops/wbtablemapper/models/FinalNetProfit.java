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
public class FinalNetProfit {
    @Column("Чистая прибыль итого ЧП с 1 ед")
    String cpFromOne;
    @Column("Чистая прибыль итого Сумма итого")
    String finalSum;
    @Column("Чистая прибыль итого МАРЖА %")
    String margin;
}
