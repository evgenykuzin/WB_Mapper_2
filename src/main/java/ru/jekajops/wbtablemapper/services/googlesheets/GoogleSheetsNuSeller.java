package ru.jekajops.wbtablemapper.services.googlesheets;

import lombok.Builder;
import org.springframework.stereotype.Component;
import ru.jekajops.wbtablemapper.models.NuSeller;
import ru.jekajops.wbtablemapper.services.googlesheets.exception.InitiationException;
import ru.jekajops.wbtablemapper.util.AppProperties;
import ru.jekajops.wbtablemapper.util.Table;
import ru.jekajops.wbtablemapper.util.annotations.Column;
import ru.jekajops.wbtablemapper.util.annotations.SubTable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GoogleSheetsNuSeller extends GoogleSheetsManager<NuSeller> {
    private static final String credentialsFilePath = "credentials.json";
    private static final String tableIdColName = "№";

    @Builder
    public GoogleSheetsNuSeller(AppProperties appProperties) throws InitiationException {
        super(
                credentialsFilePath,
                appProperties.getRemoteTableUrlGS().split("spreadsheets/d/")[1].split("/")[0],
                tableIdColName
        );
    }

    @Override
    public List<NuSeller> parseModels() {
        return parseModelsList(NuSeller::fromMap);
    }

    public void writeModels(List<NuSeller> models, Integer atIndex) {
        List<Table.Row> rows = models.stream().map(NuSeller::toRow).collect(Collectors.toList());
        List<Field> fields = Arrays.stream(NuSeller.class.getDeclaredFields()).collect(Collectors.toList());
        List<String> keys = new ArrayList<>();
        for (Field field : fields) {
            if (field.getAnnotation(SubTable.class) != null) {
                keys.addAll(Arrays.stream(field.getType().getDeclaredFields())
                        .map(f -> f.getAnnotation(Column.class))
                        .map(Column::value)
                        .collect(Collectors.toList()));
            } else if (field.getAnnotation(Column.class) != null) {
                Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::value)
                        .ifPresent(keys::add);
            }
        }
        List<List<Object>> data = rows.stream()
                .map(LinkedHashMap::values)
                .map(List::copyOf)
                .map(l->l.stream().map(v->(Object) v).collect(Collectors.toList()))
                .collect(Collectors.toList());

        Table table = new Table(tableIdColName, keys, data);
        writeTableHead("Товар\tАртикул поставщика\tДата выплаты\t Себестоимость\tСебестоимость суммарная\tСредняя цена продажи\tКоличество продаж\tОбщая выручка\tПеречислено от ВБ\tКомиссия ВБ\t\tДоля продаж от перечисления\tЛогистика\tХранение\tХранение + Логистика\t\tПлатная приемка\tВозвраты\tРеклама\tФонд налогов\tФонд Опер расходов\tФонд Рекламы\tЧистая прибыль итого\t\t\tВозврат инвестиций (ROI)\n" +
                "\t\t\t\t\t\t\t\t\tФакт\t%\t\t\t\tФакт\t%\t\t\t\t\t5%\t10%\tЧП с 1 ед\tСумма итого\tМАРЖА %\t", atIndex, 2);
        writeTable(table, atIndex+2);
    }

    public static void main(String[] args) throws InitiationException {
        var g = new GoogleSheetsNuSeller(new AppProperties());
        g.writeTableHead("", 170, 2);
    }
}
