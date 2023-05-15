package ru.jekajops.wbtablemapper.services.util;

import ru.jekajops.wbtablemapper.util.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface DataManager {
        Table parseTable();

        void writeTable(Table table, Integer atIndex);

        List<String> getKeys(List<List<Object>> data);

        default <P> List<P> parseModelsList(Function<Table.Row, P> mapFunction) {
            return parseTable().values().stream()
                    .map(mapFunction)
                    .collect(Collectors.toList());
        }

        default List<String> defaultGetKeys(List<List<Object>> data) {
            if (data == null || data.isEmpty()) return new ArrayList<>();
            List<Object> keys = data.get(0);
            data.remove(keys);
            return keys.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

}
