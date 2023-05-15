package ru.jekajops.wbtablemapper.models;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.jekajops.wbtablemapper.util.Table;
import ru.jekajops.wbtablemapper.util.annotations.Column;
import ru.jekajops.wbtablemapper.util.annotations.SubTable;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Sheet
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NuSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column("№")
    @SheetColumn("№")
    Integer index;
    @Column("Товар")
    @SheetColumn("Товар")
    String product;
    @Column("Артикул поставщика")
    @SheetColumn("Артикул поставщика")
    String producerArticle;
    @Column("Дата выплаты")
    @SheetColumn("Дата выплаты")
    String payoutDate;
    @Column("Себестоимость")
    @SheetColumn("Себестоимость")
    String costPrice;
    @Column("Себестоимость суммарная")
    @SheetColumn("Себестоимость суммарная")
    String sumCostPrice;
    @Column("Средняя цена продажи")
    @SheetColumn("Средняя цена продажи")
    String avgSellingPrice;
    @Column("Количество продаж")
    @SheetColumn("Количество продаж")
    String sellsCount;
    @Column("Общая выручка")
    @SheetColumn("Общая выручка")
    String totalRevenues;
    @Column("Перечислено от ВБ")
    @SheetColumn("Перечислено от ВБ")
    String paidFromWB;
    @Column("Комиссия ВБ")
    @SheetColumn("Комиссия ВБ")
    @SubTable
    @Transient
    FactPercentTableCommission commissionWB;
    @Column("Доля продаж от перечисления")
    @SheetColumn("Доля продаж от перечисления")
    String shareOfSellsFromTransfer;
    @Column("Логистика")
    @SheetColumn("Логистика")
    String logistics;
    @Column("Хранение")
    @SheetColumn("Хранение")
    String storage;
    @Column("Хранение + Логистика")
    @SheetColumn("Хранение + Логистика")
    @SubTable
    @Transient
    FactPercentTableStorageAnalitics storageAndLogistics;
    @Column("Возвраты")
    @SheetColumn("Возвраты")
    String returns;
    @Column("Реклама")
    @SheetColumn("Реклама")
    String advertisement;
    @Column("Фонд налогов")
    @SheetColumn("Фонд налогов")
    String taxFund;
    @Column("Фонд Опер расходов")
    @SheetColumn("Фонд Опер расходов")
    String operExpensesFund;
    @Column("Фонд Рекламы")
    @SheetColumn("Фонд Рекламы")
    String advertisementFund;
    @Column("Чистая прибыль итого")
    @SheetColumn("Чистая прибыль итого")
    @SubTable
    @Transient
    FinalNetProfit finalNetProfit;
    @Column("Возврат инвестиций (ROI)")
    @SheetColumn("Возврат инвестиций (ROI)")
    String returnsOnInvestment;
    @Column("weekUUID")
    UUID weekUUID;

    public static <M extends Table.Row> NuSeller fromMap(M map) {
        NuSeller nuSeller = new NuSeller();
        for (Field f : NuSeller.class.getDeclaredFields()) {
            Column column = f.getAnnotation(Column.class);
            if (column != null) {
                String key = column.value();
                Object value =  map.get(key);
                if (value == null) continue;
                if (f.getType().equals(String.class)) {
                    setValue(nuSeller, f, value.toString());
                } else if (f.getType().equals(int.class) || f.getType().equals(Integer.class)) {
                    setValue(nuSeller, f, Integer.parseInt(value.toString()));
                }
            }
        }
        return nuSeller;
    }

    private static <T> void setValue(NuSeller nuSeller, Field f, T value) {
        try {
            setterMethod(f, nuSeller.getClass(), value.getClass())
                    .invoke(nuSeller, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static Method method(String prefix, Field field, Class<?> cls, Class<?>... argTypes) throws NoSuchMethodException {
        String fieldName = field.getName();
        String methodName = prefix + fieldName.substring(0,1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
        return cls.getDeclaredMethod(methodName, argTypes);
    }

    private static Method getterMethod(Field field, Class<?> cls) throws NoSuchMethodException {
        return method("get", field, cls);
    }

    private static Method setterMethod(Field field, Class<?> cls, Class<?>... argTypes) throws NoSuchMethodException {
        return method("set", field, cls, argTypes);
    }

    private static Object getFromGetterMethod(Field field, Object o, Object... args) {
        try {
            return getterMethod(field, o.getClass()).invoke(o, args);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Table.Row toRow() {
        Table.Row row = new Table.Row();
        for (Field field : NuSeller.class.getDeclaredFields()) {
            if (field.getAnnotation(SubTable.class) != null) {
                Field[] innerFields = field.getType().getDeclaredFields();
                Object inst = getFromGetterMethod(field, this);
                for (Field innerField : innerFields) {
                    putFromColumnValue(innerField, row, inst);
                }
            } else if (field.getAnnotation(Column.class) != null) {
                putFromColumnValue(field, row, this);
            }
        }
        System.out.println(row);
        return row;
    }

    private void putFromColumnValue(Field field, Table.Row row, Object o) {
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            String key = column.value();
            Object value = getFromGetterMethod(field, o);
            row.put(key, String.valueOf(value));
        }
    }

//    public static class Nastya {
//        public static void main(String[] args) {
//            doWork(
//                    "@ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"id\")\n" +
//                    "    int rowIndex;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Товар\")\n" +
//                    "    @SheetColumn(\"Товар\")\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Mapping(\"Предмет\")\n" +
//                    "    String product;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Артикул поставщика\")\n" +
//                    "    @SheetColumn(\"Артикул поставщика\")\n" +
//                    "    String producerArticle;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Дата выплаты\")\n" +
//                    "    @SheetColumn(\"Дата выплаты\")\n" +
//                    "    String PayoutDate;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Себестоимость\")\n" +
//                    "    @SheetColumn(\"Себестоимость\")\n" +
//                    "    String costPrice;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Себестоимость суммарная\")\n" +
//                    "    @SheetColumn(4)\n" +
//                    "    String sumCostPrice;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Средняя цена продажи\")\n" +
//                    "    @SheetColumn(5)\n" +
//                    "    String avgSellingPrice;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Количество продаж\")\n" +
//                    "    @SheetColumn(6)\n" +
//                    "    String sellsCount;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Общая выручка\")\n" +
//                    "    @SheetColumn(7)\n" +
//                    "    String totalRevenues;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Перечислено от ВБ\")\n" +
//                    "    @SheetColumn(8)\n" +
//                    "    String paidFromWB;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Комиссия ВБ\")\n" +
//                    "    @SheetColumn(9)\n" +
//                    "    FactPercentTable commissionWB;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Доля продаж от перечисления\")\n" +
//                    "    @SheetColumn(10)\n" +
//                    "    String shareOfSellsFromTransfer;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Логистика\")\n" +
//                    "    @SheetColumn(11)\n" +
//                    "    String logistics;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Хранение\")\n" +
//                    "    @SheetColumn(12)\n" +
//                    "    String storage;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Хранение + Логистика\")\n" +
//                    "    @SheetColumn(13)\n" +
//                    "    FactPercentTable storageAndLogistics;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Возвраты\")\n" +
//                    "    @SheetColumn(14)\n" +
//                    "    String returns;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Реклама\")\n" +
//                    "    @SheetColumn(15)\n" +
//                    "    String advertisement;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Фонд налогов\")\n" +
//                    "    @SheetColumn(16)\n" +
//                    "    String taxFund;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Фонд Опер расходов\")\n" +
//                    "    @SheetColumn(17)\n" +
//                    "    String operExpensesFund;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Фонд Рекламы\")\n" +
//                    "    @SheetColumn(18)\n" +
//                    "    String advertisementFund;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Чистая прибыль итого\")\n" +
//                    "    @SheetColumn(19)\n" +
//                    "    ru.jekajops.ru.jekajops.wbtablemapper.models.FinalNetProfit finalNetProfit;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Возврат инвестиций (ROI)\")\n" +
//                    "    @SheetColumn(20)\n" +
//                    "    String returnsOnInvestment;");
//        }
//
//        static void doWork(String clsStr) {
//            StringBuilder sb = new StringBuilder();
//            String[] split = clsStr.split("@ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column");
//            for (String s : split) {
//                System.out.println(s);
//                System.out.println(Arrays.toString(s.split("\\(\"")));
//                String[] keySplit = s.split("\\(\"");
//                if (keySplit.length < 2) continue;
//                String key = keySplit[1].split("\"\\)")[0];
//                String sheetColumn = "@SheetColumn(\"" + key + "\")";
//                String edited = s.replaceAll("@SheetColumn\\(.*?\\)", sheetColumn);
//                sb.append("@ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column").append(edited);
//            }
//            System.out.println("\n\n\n\n");
//            System.out.println(sb);
//        }
//    }

}
