package ru.jekajops.wbtablemapper.services.mappers;

import ru.jekajops.wbtablemapper.models.OutputExel;
import ru.jekajops.wbtablemapper.models.WB;

import java.util.List;
import java.util.Map;

public class OutputExelWBMapper implements WBMapper<OutputExel> {

    @Override
    public WB mapToWB(OutputExel o) {
        return null;
    }

    @Override
    public OutputExel mapToOutput(WB o) {
        return null;
    }

    @Override
    public List<OutputExel> mapAllToOutput(List<WB> o, Object... adarr) {
        return mapAllToOutput(o, (List<Map<String, String>>) adarr[0]);
    }

    public List<OutputExel> mapAllToOutput(List<WB> o, List<Map<String, String>> adv) {

    }

}
