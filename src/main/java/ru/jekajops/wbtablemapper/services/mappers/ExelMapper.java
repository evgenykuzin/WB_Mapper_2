package ru.jekajops.wbtablemapper.services.mappers;

import io.github.millij.poi.SpreadsheetReadException;
import io.github.millij.poi.ss.reader.SpreadsheetReader;
import io.github.millij.poi.ss.reader.XlsxReader;
import io.github.millij.poi.ss.writer.SpreadsheetWriter;
import ru.jekajops.wbtablemapper.models.NuSeller;
import ru.jekajops.wbtablemapper.models.WB;
import ru.jekajops.wbtablemapper.util.FileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class ExelMapper {
    public synchronized static <T> List<T> mapFromXlsx(String xlsxFileName, Class<T> modelClass) {
        SpreadsheetReader reader = new XlsxReader();
        try {
            File file = FileManager.getFromResources(xlsxFileName);
            return reader.read(modelClass, file);
        } catch (SpreadsheetReadException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static <T> List<T> mapFromXlsx(InputStream is, Class<T> modelClass) {
        SpreadsheetReader reader = new XlsxReader();
        try {
            return reader.read(modelClass, is);
        } catch (SpreadsheetReadException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public synchronized static List<NuSeller> mapNuSeller(String xlsxFileName) {
        return mapFromXlsx(xlsxFileName, NuSeller.class);
    }

    public synchronized static List<WB> mapWB(String xlsxFileName) {
        return mapFromXlsx(xlsxFileName, WB.class);
    }

    public static List<NuSeller> mapNuSeller(InputStream is) {
        return mapFromXlsx(is, NuSeller.class);
    }

    public static List<WB> mapWB(InputStream is) {
        return mapFromXlsx(is, WB.class);
    }

    public static <T> void mapToXlsx(List<T> items, Class<T> modelClass, File file) {
        try {
            SpreadsheetWriter writer = new SpreadsheetWriter(file);
            writer.addSheet(modelClass, items);
            writer.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
