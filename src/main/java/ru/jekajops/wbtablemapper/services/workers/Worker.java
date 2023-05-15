package ru.jekajops.wbtablemapper.services.workers;

import org.springframework.stereotype.Service;
import ru.jekajops.wbtablemapper.repository.NuSellerRepository;
import ru.jekajops.wbtablemapper.services.googlesheets.GoogleSheetsNuSeller;
import ru.jekajops.wbtablemapper.services.mappers.WBMapper;
import ru.jekajops.wbtablemapper.util.AppProperties;

@Service
public class Worker {
    private final GoogleSheetsNuSeller googleSheetsNuSeller;
    private final WBMapper nuSellerWBMapper;
    private final NuSellerRepository nuSellerRepository;
    private final String wbFilePath;

    public Worker(GoogleSheetsNuSeller googleSheetsNuSeller, WBMapper nuSellerWBMapper, NuSellerRepository nuSellerRepository, AppProperties wbProperties) {
        this.googleSheetsNuSeller = googleSheetsNuSeller;
        this.nuSellerWBMapper = nuSellerWBMapper;
        this.nuSellerRepository = nuSellerRepository;
        this.wbFilePath = wbProperties.getWbFilePath();
    }

    //@Scheduled(fixedRate = 5000)
    public void run() {
//        List<WB> wbTable = ExelMapper.mapWB(wbFilePath);
//        Iterable<NuSeller> oldNuSeller = nuSellerRepository.findAll();
//        List<NuSeller> nuSellerTable = nuSellerWBMapper.map(wbTable, oldNuSeller);
//        nuSellerRepository.saveAll(nuSellerTable).forEach(System.out::println);
//        googleSheetsNuSeller.writeModels(nuSellerTable);
    }
}
