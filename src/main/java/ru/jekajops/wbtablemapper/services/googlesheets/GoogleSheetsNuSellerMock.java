package ru.jekajops.wbtablemapper.services.googlesheets;

import org.springframework.stereotype.Component;
import ru.jekajops.wbtablemapper.models.NuSeller;
import ru.jekajops.wbtablemapper.services.googlesheets.exception.InitiationException;
import ru.jekajops.wbtablemapper.util.AppProperties;
import ru.jekajops.wbtablemapper.util.Table;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class GoogleSheetsNuSellerMock extends GoogleSheetsNuSeller {
    public GoogleSheetsNuSellerMock(AppProperties appProperties) throws InitiationException {
        super(appProperties);
    }

    @Override
    protected void init(String credentialsFilePath, String spreadsheetId, String tableIdColName, Comparator<Table.Row> comparator, BiFunction<List<String>, Collection<List<Object>>, Table> tableGenerator) throws InitiationException {

    }

    @Override
    public List<NuSeller> parseModels() {
        return Collections.emptyList();
    }

    @Override
    public void writeModels(List<NuSeller> models, Integer atIndex) {

    }

    @Override
    public void writeTable(Table table, Integer atIndex) {

    }
}
