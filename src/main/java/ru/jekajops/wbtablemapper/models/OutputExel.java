package ru.jekajops.wbtablemapper.models;


import io.github.millij.poi.ss.model.annotations.SheetColumn;
import lombok.*;
import org.hibernate.Hibernate;
import ru.jekajops.wbtablemapper.util.annotations.Column;
import ru.jekajops.wbtablemapper.util.annotations.Mapping;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Output_exel")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class OutputExel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column("Товарная группа")
    @Mapping(from = WB.class, value = "")
    @SheetColumn("")
    private String productGroup;

    @Column("Производитель")
    @SheetColumn("")
    private String manufacturer;

    @Column("Артикул продавца")
    @SheetColumn("")
    private String sellerCode;

    @Column("ЦЗ")
    @SheetColumn("")
    private String cz;

    @Column("Неделя года")
    @Mapping(from = WB.class, value = "")
    @SheetColumn("")
    private int weekOfYear;

    @Column("Сумма по полю Поступления, шт.")
    @SheetColumn("")
    @Mapping(from = WB.class, value = "")
    private int quantityReceived;

    @Column("Сумма по полю Заказано, шт.")
    @SheetColumn("")
    private int quantityOrdered;

    @Column("Сумма по полю Выкупили, шт.")
    @SheetColumn("")
    private int quantityPurchased;

    @Column("Сумма по полю Сумма заказов минус комиссия WB, руб.")
    @SheetColumn("")
    private double ordersMinusWBCommission;

    @Column("Сумма по полю сумма заказов в ЦЗ")
    @SheetColumn("")
    private double czOrdersSum;

    @Column("Сумма по полю сумма выкупов в ЦЗ")
    @SheetColumn("")
    private double czPurchasesSum;

    @Column("Сумма по полю К перечислению за товар, руб.")
    @SheetColumn("")
    private double productPaymentSum;

    @Column("Сумма по полю маржа по выкупам после вычетов МП")
    @SheetColumn("")
    private double marginAfterMPDeductions;

    @Column("поставка ВЛ 23 на ФФ")
    @SheetColumn("")
    private int deliveryVL23ToFF;

    @Column("поставка ВЛ 23 на ФФ в ЦЗ")
    @SheetColumn("")
    private int deliveryVL23ToCZ;

    @Column("Артикул ВБ")
    @SheetColumn("")
    private String vbArticle;

    @Column("текущий остаток")
    @SheetColumn("")
    private int currentBalance;

    @Column("остаток в ЦЗ")
    @SheetColumn("")
    private int czBalance;

    @Column("по заказам")
    @SheetColumn("")
    private int byOrders;

    @Column("по выкупам")
    @SheetColumn("")
    private int byPurchases;

    @Column("маржа до вычета расходов МП")
    @SheetColumn("")
    private double marginBeforeMPDeductions;

    @Column("сумма в РЦ")
    @SheetColumn("")
    private double retailPriceSum;

    @Column("РЦ")
    @SheetColumn("")
    private double retailPrice;

    @Column("РЦ с СПП 25%")
    @SheetColumn("")
    private double retailPriceWith25PercentsMarkup;

    @Column("наценка по текущей РЦ")
    @SheetColumn("")
    private double markupByCurrentRetailPrice;

    @Column("наценка ФАКТ после вычета комисии")
    @SheetColumn("")
    private double markupFactAfterCommissionDeduction;

    @Column("решение по цене")
    @SheetColumn("")
    private String priceDecision;

    @Column("Артикул ВБ")
    @SheetColumn("")
    private String vbArticle2;

    @Column("предмет")
    @SheetColumn("")
    private String subject;

    @Column("РК")
    @SheetColumn("")
    private String advertisingCampaign;

    @Column("расходы на РК")
    @SheetColumn("")
    private double advertisingExpenses;

    @Column("CTR")
    @SheetColumn("")
    private Double clickThroughRate;

    @Column("Количество_просмотров")
    @SheetColumn("")
    private Integer viewsCount;

    @Column("ДРР_от_заказов")
    @SheetColumn("")
    private Double orderConversionRate;

    @Column("Маркетинг")
    @SheetColumn("")
    private String marketingChannel;

    @Column("CPM")
    @SheetColumn("")
    private Double costPerMille;

    @Column("CPM_2")
    @SheetColumn("")
    private Double costPerMille2;

    @Column("Предметное_фото")
    @SheetColumn("")
    private Boolean productPhoto;

    @Column("Модельная_съемка")
    @SheetColumn("")
    private Boolean modelPhoto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OutputExel that = (OutputExel) o;
        return id != null
                && Objects.equals(id, that.id)
                && sellerCode != null
                && Objects.equals(sellerCode, that.sellerCode);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
