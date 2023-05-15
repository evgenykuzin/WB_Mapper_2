package ru.jekajops.wbtablemapper.models;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Sheet
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class WB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //@Column(name = "n")
    @SheetColumn(value = "№", nullable = false)
    Long index;
    //@Column(name = "Номер_поставки")
    @SheetColumn("Номер поставки")
    String c2;
    //@Column(name = "Предмет")
    @SheetColumn("Предмет")
    String c3;
    //@Column(name = "Код_номенклатуры")
    @SheetColumn("Код номенклатуры")
    String c4;
    //@Column(name = "Бренд")
    @SheetColumn("Бренд")
    String c5;
    //@Column(name = "Артикул_поставщика")
    @SheetColumn("Артикул поставщика")
    String c6;
    //@Column(name = "Название")
    @SheetColumn("Название")
    String c7;
    //@Column(name = "Размер")
    @SheetColumn("Размер")
    String c8;
    //@Column(name = "Баркод")
    @SheetColumn("Баркод")
    String c9;
    //@Column(name = "Тип_документа")
    @SheetColumn("Тип документа")
    String c10;
    //@Column(name = "Обоснование_для_оплаты")
    @SheetColumn("Обоснование для оплаты")
    String c11;
    //@Column(name = "Дата_заказа_покупателем")
    @SheetColumn("Дата заказа покупателем")
    String c12;
    //@Column(name = "Дата_продажи")
    @SheetColumn("Дата продажи")
    String c13;
    //@Column(name = "Кол_во")
    @SheetColumn("Кол-во")
    String c14;
    //@Column(name = "Цена_розничная")
    @SheetColumn("Цена розничная")
    String c15;
    //@Column(name = "Вайлдберриз_реализовал_Товар_Пр")
    @SheetColumn("Вайлдберриз реализовал Товар (Пр)")
    String c16;
    //@Column(name = "Согласованный_продуктовый_дисконт")
    @SheetColumn("Согласованный продуктовый дисконт, %")
    String c18;
    //@Column(name = "Промокод %")
    @SheetColumn("Промокод %")
    String c19;
    //@Column(name = "Итоговая_согласованная_скидка")
    @SheetColumn("Итоговая согласованная скидка")
    String c20;
    //@Column(name = "ЦР_СУСС")
    @SheetColumn("Цена розничная с учетом согласованной скидки")
    String c21;
    //@Column(name = "СПП")
    @SheetColumn("Скидка постоянного Покупателя (СПП)")
    String c22;
    //@Column(name = "Размер_кВВ")
    @SheetColumn("Размер кВВ, %")
    String c23;
    //@Column(name = "РкВВбНДС_Б")
    @SheetColumn("Размер кВВ без НДС, % Базовый")
    String c24;
    //@Column(name = "РкВВбНДС")
    @SheetColumn("Итоговый кВВ без НДС, %")
    String c25;
    //@Column(name = "ВСПДВУПбНДС")
    @SheetColumn("Вознаграждение с продаж до вычета услуг поверенного, без НДС")
    String c26;
    //@Column(name = "ВРУП")
    @SheetColumn("Возмещение Расходов услуг поверенного")
    String c27;
    //@Column(name = "ВВбНДС")
    @SheetColumn("Вознаграждение Вайлдберриз (ВВ), без НДС")
    String c28;
    //@Column(name = "НДСсВВ")
    @SheetColumn("НДС с Вознаграждения Вайлдберриз")
    String c29;
    //@Column(name = "ППзРТ")
    @SheetColumn("К перечислению Продавцу за реализованный Товар")
    String c30;
    //@Column(name = "Количество_доставок")
    @SheetColumn("Количество доставок")
    String c31;
    //@Column(name = "Количество_возврата")
    @SheetColumn("Количество возврата")
    String c32;
    //@Column(name = "УпДТП")
    @SheetColumn("Услуги по доставке товара покупателю")
    String c33;
    //@Column(name = "Штрафы")
    @SheetColumn("Штрафы")
    String c34;
    //@Column(name = "Доплаты")
    @SheetColumn("Доплаты")
    String c35;
    //@Column(name = "ОШиД")
    @SheetColumn("Обоснование штрафов и доплат")
    String c36;
    //@Column(name = "Стикер_МП")
    @SheetColumn("Стикер МП")
    String c37;
    //@Column(name = "Номер_офиса")
    @SheetColumn("Номер офиса")
    String c38;
    //@Column(name = "НОД")
    @SheetColumn("Наименование офиса доставки")
    String c39;
    //@Column(name = "ИНН_партнера")
    @SheetColumn("ИНН партнера")
    String c40;
    //@Column(name = "Партнер")
    @SheetColumn("Партнер")
    String c41;
    //@Column(name = "Склад")
    @SheetColumn("Склад")
    String c42;
    //@Column(name = "Страна")
    @SheetColumn("Страна")
    String c43;
    //@Column(name = "Тип_коробов")
    @SheetColumn("Тип коробов")
    String c44;
    //@Column(name = "НТД")
    @SheetColumn("Номер таможенной декларации")
    String c45;
    //@Column(name = "ШК")
    @SheetColumn("ШК")
    String c46;
    //@Column(name = "Rid")
    @SheetColumn("Rid")
    String c47;
    //@Column(name = "Srid")
    @SheetColumn("Srid")
    String c48;

    public static <M extends Map<String, String>> WB fromMap(M map) {
        WB wb = new WB();
        for (Field f : WB.class.getDeclaredFields()) {
            Column column = f.getAnnotation(Column.class);
            if (column != null) {
                String key = column.name();
                Object value = map.get(key);
                try {
                    f.setAccessible(true);
                    f.set(wb, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    f.setAccessible(false);
                }
            }
        }
        return wb;
    }

//    public static class Nastya {
//        public static void main(String[] args) {
//            doWork("@ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"№\")\n" +
//                    "    //@ExcelCell(0)\n" +
//                    "    String c1;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Номер поставки\")\n" +
//                    "    //@ExcelCell(1)\n" +
//                    "    String c2;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Предмет\")\n" +
//                    "    //@ExcelCell(2)\n" +
//                    "    String c3;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Код номенклатуры\")\n" +
//                    "    //@ExcelCell(3)\n" +
//                    "    String c4;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Бренд\")\n" +
//                    "    //@ExcelCell(4)\n" +
//                    "    String c5;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Артикул поставщика\")\n" +
//                    "    //@ExcelCell(5)\n" +
//                    "    String c6;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Название\")\n" +
//                    "    //@ExcelCell(6)\n" +
//                    "    String c7;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Размер\")\n" +
//                    "    //@ExcelCell(7)\n" +
//                    "    String c8;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Баркод\")\n" +
//                    "    //@ExcelCell(8)\n" +
//                    "    String c9;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Тип документа\")\n" +
//                    "    //@ExcelCell(9)\n" +
//                    "    String c10;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Обоснование для оплаты\")\n" +
//                    "    //@ExcelCell(10)\n" +
//                    "    String c11;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Дата заказа покупателем\")\n" +
//                    "    //@ExcelCell(11)\n" +
//                    "    String c12;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Дата продажи\")\n" +
//                    "    //@ExcelCell(12)\n" +
//                    "    String c13;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Кол-во\")\n" +
//                    "    //@ExcelCell(13)\n" +
//                    "    String c14;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Цена розничная\")\n" +
//                    "    //@ExcelCell(14)\n" +
//                    "    String c15;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Вайлдберриз реализовал\")\n" +
//                    "    //@ExcelCell(15)\n" +
//                    "    String c16;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Товар (Пр)\")\n" +
//                    "    //@ExcelCell(16)\n" +
//                    "    String c17;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Согласованный продуктовый дисконт, %\")\n" +
//                    "    //@ExcelCell(17)\n" +
//                    "    String c18;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Промокод %\")\n" +
//                    "    //@ExcelCell(18)\n" +
//                    "    String c19;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Итоговая согласованная скидка\")\n" +
//                    "    //@ExcelCell(19)\n" +
//                    "    String c20;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Цена розничная с учетом согласованной скидки\")\n" +
//                    "    //@ExcelCell(20)\n" +
//                    "    String c21;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Скидка постоянного Покупателя (СПП)\")\n" +
//                    "    //@ExcelCell(21)\n" +
//                    "    String c22;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Размер кВВ, %\")\n" +
//                    "    //@ExcelCell(22)\n" +
//                    "    String c23;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Размер кВВ без НДС, % Базовый\")\n" +
//                    "    //@ExcelCell(23)\n" +
//                    "    String c24;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Итоговый кВВ без НДС, %\")\n" +
//                    "    //@ExcelCell(24)\n" +
//                    "    String c25;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Вознаграждение с продаж до вычета услуг поверенного, без НДС\")\n" +
//                    "    //@ExcelCell(25)\n" +
//                    "    String c26;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Возмещение Расходов услуг поверенного\")\n" +
//                    "    //@ExcelCell(26)\n" +
//                    "    String c27;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Вознаграждение Вайлдберриз (ВВ), без НДС\")\n" +
//                    "    //@ExcelCell(27)\n" +
//                    "    String c28;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"НДС с Вознаграждения Вайлдберриз\")\n" +
//                    "    //@ExcelCell(28)\n" +
//                    "    String c29;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"К перечислению Продавцу за реализованный Товар\")\n" +
//                    "    //@ExcelCell(29)\n" +
//                    "    String c30;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Количество доставок\")\n" +
//                    "    //@ExcelCell(30)\n" +
//                    "    String c31;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Количество возврата\")\n" +
//                    "    //@ExcelCell(31)\n" +
//                    "    String c32;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Услуги по доставке товара покупателю\")\n" +
//                    "    //@ExcelCell(32)\n" +
//                    "    String c33;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Штрафы\")\n" +
//                    "    //@ExcelCell(33)\n" +
//                    "    String c34;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Доплаты\")\n" +
//                    "    //@ExcelCell(34)\n" +
//                    "    String c35;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Обоснование штрафов и доплат\")\n" +
//                    "    //@ExcelCell(35)\n" +
//                    "    String c36;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Стикер МП\")\n" +
//                    "    //@ExcelCell(36)\n" +
//                    "    String c37;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Номер офиса\")\n" +
//                    "    //@ExcelCell(37)\n" +
//                    "    String c38;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Наименование офиса доставки\")\n" +
//                    "    //@ExcelCell(38)\n" +
//                    "    String c39;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"ИНН партнера\")\n" +
//                    "    //@ExcelCell(39)\n" +
//                    "    String c40;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Партнер\")\n" +
//                    "    //@ExcelCell(40)\n" +
//                    "    String c41;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Склад\")\n" +
//                    "    //@ExcelCell(41)\n" +
//                    "    String c42;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Страна\")\n" +
//                    "    //@ExcelCell(42)\n" +
//                    "    String c43;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Тип коробов\")\n" +
//                    "    //@ExcelCell(43)\n" +
//                    "    String c44;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Номер таможенной декларации\")\n" +
//                    "    //@ExcelCell(44)\n" +
//                    "    String c45;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"ШК\")\n" +
//                    "    //@ExcelCell(45)\n" +
//                    "    String c46;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Rid\")\n" +
//                    "    //@ExcelCell(46)\n" +
//                    "    String c47;\n" +
//                    "    @ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column(\"Srid\")\n" +
//                    "    //@ExcelCell(47)\n" +
//                    "    String c48;");
//        }

//        static void doWork(String clsStr) {
//            StringBuilder sb = new StringBuilder();
//            String[] split = clsStr.split("//@Column");
//            for (String s : split) {
//                System.out.println(s);
//                System.out.println(Arrays.toString(s.split("\\(\"")));
//                String[] keySplit = s.split("\\(\"");
//                if (keySplit.length < 2) continue;
//                String key = keySplit[1].split("\"\\)")[0];
//                String sheetColumn = "@SheetColumn(\"" + key + "\")";
//                String edited = s.replaceAll("//@ExcelCell\\(.*?\\)", sheetColumn);
//                sb.append("@ru.jekajops.ru.jekajops.wbtablemapper.util.annotations.Column").append(edited);
//            }
//            System.out.println("\n\n\n\n");
//            System.out.println(sb);
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WB wb = (WB) o;
        return index != null && Objects.equals(index, wb.index);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
