package ru.jekajops.wbtablemapper.services.mappers;

import ru.jekajops.wbtablemapper.models.WB;

import java.util.List;

public interface WBMapper<T> {
    WB mapToWB(T o);
    T mapToOutput(WB o);
    List<T> mapAllToOutput(List<WB> o, Object... additionDataArray);
}
