package ru.jekajops.wbtablemapper.services.mappers;

import org.springframework.stereotype.Component;
import ru.jekajops.wbtablemapper.models.*;
import ru.jekajops.wbtablemapper.services.SettingsService;
import ru.jekajops.wbtablemapper.util.annotations.Column;
import ru.jekajops.wbtablemapper.util.annotations.Mapping;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Component
public class NuSellerWBMapper implements WBMapper<NuSeller> {
    private final SettingsService settingsService;

    public NuSellerWBMapper(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public WB mapToWB(NuSeller o) {
        return WB.builder().build();
    }

    @Override
    public NuSeller mapToOutput(WB o) {
        NuSeller nuSeller = new NuSeller();
        for (Field f : NuSeller.class.getDeclaredFields()) {
            Mapping mapping = f.getAnnotation(Mapping.class);
            Column column = f.getAnnotation(Column.class);
            if (mapping == null || column == null) break;
            Class<?> fromCls = mapping.from();
            if (!o.getClass().equals(fromCls)) break;
            String wbValue = getValueByMapping(o, fromCls, mapping.value());
            setValueToField(f, nuSeller, wbValue);
        }
        return nuSeller;
    }

    @Override
    public List<NuSeller> mapAllToOutput(List<WB> o, Object... adarr) {
        return map(o, (Integer) adarr[0], (String) adarr[1], (Map<String, String>) adarr[2], (Map<String, String>) adarr[3]);
    }

    public List<NuSeller> map(List<WB> wb,
                              Integer startIndex,
                              String payOutDate,
                              Map<String, String> costPricesMap,
                              Map<String, String> advertisementMap) {
        List<NuSeller> result = new ArrayList<>();
        UUID weekUUID = UUID.randomUUID();
        int index = 1;
        Map<String, List<WB>> map = wb.stream().peek(w -> {
            if (w.getC3() == null) {
                w.setC3("UNKNOWN");
            }
        }).collect(Collectors.groupingBy(WB::getC6));
        for (String s : map.keySet()) {
            int i = index + startIndex;
            List<WB> wbs = map.get(s);
            NuSeller nuSeller = new NuSeller();
            nuSeller.setIndex(index);
            nuSeller.setProduct(wbs + " - " + wbs.size());
            nuSeller.setProducerArticle(wbs.stream()
                    .map(WB::getC6)
                    .distinct()
                    .collect(Collectors.joining(",")));
            nuSeller.setSellsCount(summingItems(wbs, WB::getC14));
            nuSeller.setTotalRevenues(summingItems(wbs, WB::getC16));
            nuSeller.setPaidFromWB(summingItems(wbs, WB::getC30));
            nuSeller.setPayoutDate(payOutDate);
            nuSeller.setLogistics(summingItems(wbs, WB::getC33));
            nuSeller.setReturns(summingItems(wbs.stream().filter(w -> "Возврат".equals(w.getC10())), WB::getC30));
            nuSeller.setCostPrice(Optional.ofNullable(costPricesMap.get(s)).orElse("0"));
            nuSeller.setAdvertisement(Optional.ofNullable(advertisementMap.get(s)).orElse("0"));
            nuSeller.setSumCostPrice(format("=E%s*H%s", i, i));
            nuSeller.setAvgSellingPrice(format("=IFERROR((E%s/H%s);0)", i, i));
            nuSeller.setCommissionWB(FactPercentTableCommission.builder()
                            .fact(format("=I%s-J%s", i, i))
                            .percent(format("=IFERROR(((I%s-J%s)/I%s);0)", i, i, i))
                            .build());
            nuSeller.setShareOfSellsFromTransfer(format("=IFERROR((J%s/$J$17);0)", i));
            nuSeller.setStorage(format("=M%s*$O$17", i));
            nuSeller.setStorageAndLogistics(
                    FactPercentTableStorageAnalitics.builder()
                            .fact(format("=N%s+O%s", i, i))
                            .percent(format("=IFERROR((P%s/I%s);0)", i, i))
                    .build()
            );
            nuSeller.setTaxFund(format("=H%s*0.072", i));
            nuSeller.setOperExpensesFund(format("=(J%s-P%s-R%s-S%s)*%s", i, i, i, i, 5));
            nuSeller.setAdvertisementFund(format("=(J%s-P%s-R%s-S%s)*%s", i, i, i, i, 10));
            nuSeller.setFinalNetProfit(
                    FinalNetProfit.builder()
                            .cpFromOne(format("=IFERROR((X%s/H%s);0)", i, i))
                            .finalSum(format("=I%s-K%s-N%s-O%s-R%s-S%s-F%s-T%s-U%s-V%s", i, i, i, i, i, i, i, i, i, i))
                            .margin(format("=IFERROR((X%s/I%s);0)", i, i))
                            .build()
            );
            nuSeller.setReturnsOnInvestment(format("=IFERROR((W%s/E%s);0)", i, i));
            nuSeller.setWeekUUID(weekUUID);
            result.add(nuSeller);
            index++;
        }
        return result;
    }

    public List<NuSeller> map(List<WB> wb,
                              Integer lastIndex,
                              String payOutDate) {
        return map(
                wb,
                lastIndex,
                payOutDate,
                settingsService.findSettingsMap(Setting.Group.COST_PRICE),
                settingsService.findSettingsMap(Setting.Group.ADVERTISEMENT)
        );
    }

    private String summingItems(List<WB> wbs, Function<WB, String> mapToField) {
        return summingItems(wbs.stream(), mapToField);
    }

    private String summingItems(Stream<WB> wbs, Function<WB, String> mapToField) {
        return String.valueOf(wbs
                .map(mapToField)
                .filter(Objects::nonNull)
                .map(s -> s.replaceFirst(",", "."))
                .mapToDouble(Double::valueOf)
                .sum());
    }

    private synchronized String getValueByMapping(Object o, Class<?> fromCls, String mappingName) {
        return Arrays.stream(fromCls.getDeclaredFields())
                .filter(f -> Optional.ofNullable(f.getAnnotation(Column.class)).map(Column::value).filter(mappingName::equals).isPresent())
                .findFirst()
                .map(f -> getValueFromField(f, o))
                .map(Objects::toString)
                .orElse("");
    }

    private synchronized Object getValueFromField(Field f, Object o) {
        try {
            f.setAccessible(true);
            return f.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            f.setAccessible(false);
        }
        return null;
    }

    private synchronized void setValueToField(Field f, Object o, String value) {
        try {
            f.setAccessible(true);
            f.set(o, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            f.setAccessible(false);
        }
    }

}
