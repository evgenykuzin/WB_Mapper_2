package ru.jekajops.wbtablemapper.models;

import io.github.millij.poi.ss.model.annotations.SheetColumn;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Adv")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Adv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ru.jekajops.wbtablemapper.util.annotations.Column("Артикул ВБ")
    @SheetColumn("")
    private String vbArticle2;

    @ru.jekajops.wbtablemapper.util.annotations.Column("предмет")
    @SheetColumn("")
    private String subject;

    @ru.jekajops.wbtablemapper.util.annotations.Column("РК")
    @SheetColumn("")
    private String advertisingCampaign;

    @ru.jekajops.wbtablemapper.util.annotations.Column("расходы на РК")
    @SheetColumn("")
    private double advertisingExpenses;

    @ru.jekajops.wbtablemapper.util.annotations.Column("CTR")
    @SheetColumn("")
    private Double clickThroughRate;

    @ru.jekajops.wbtablemapper.util.annotations.Column("Количество_просмотров")
    @SheetColumn("")
    private Integer viewsCount;

    @ru.jekajops.wbtablemapper.util.annotations.Column("ДРР_от_заказов")
    @SheetColumn("")
    private Double orderConversionRate;

    @ru.jekajops.wbtablemapper.util.annotations.Column("CPM")
    @SheetColumn("")
    private Double costPerMille;

    @ru.jekajops.wbtablemapper.util.annotations.Column("CPM_2")
    @SheetColumn("")
    private Double costPerMille2;

    @ru.jekajops.wbtablemapper.util.annotations.Column("Предметное_фото")
    @SheetColumn("")
    private Boolean productPhoto;

    @ru.jekajops.wbtablemapper.util.annotations.Column("Модельная_съемка")
    @SheetColumn("")
    private Boolean modelPhoto;
}
