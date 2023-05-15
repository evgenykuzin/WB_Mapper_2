package ru.jekajops.wbtablemapper.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.jekajops.wbtablemapper.models.NuSeller;
import ru.jekajops.wbtablemapper.models.WB;
import ru.jekajops.wbtablemapper.repository.NuSellerRepository;
import ru.jekajops.wbtablemapper.repository.SettingsRepository;
import ru.jekajops.wbtablemapper.repository.WBRepository;
import ru.jekajops.wbtablemapper.services.googlesheets.GoogleSheetsNuSeller;
import ru.jekajops.wbtablemapper.services.mappers.ExelMapper;
import ru.jekajops.wbtablemapper.services.mappers.NuSellerWBMapper;
import ru.jekajops.wbtablemapper.util.AppProperties;
import ru.jekajops.wbtablemapper.util.FileManager;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j(topic = "Logger")
public class MainController extends AController {
    WBRepository wbRepository;
    NuSellerWBMapper nuSellerWBMapper;
    NuSellerRepository nuSellerRepository;
    GoogleSheetsNuSeller googleSheetsNuSeller;
    SettingsRepository settingsRepository;

    public MainController(
            AppProperties appProperties,
            WBRepository wbRepository,
            NuSellerWBMapper nuSellerWBMapper,
            NuSellerRepository nuSellerRepository,
            GoogleSheetsNuSeller googleSheetsNuSeller,
            SettingsRepository settingsRepository
    ) {
        super(appProperties);
        this.wbRepository = wbRepository;
        this.nuSellerWBMapper = nuSellerWBMapper;
        this.nuSellerRepository = nuSellerRepository;
        this.googleSheetsNuSeller = googleSheetsNuSeller;
        this.settingsRepository = settingsRepository;
    }

    @GetMapping("/")
    public String menu(Model model) throws IOException {
        model.addAttribute("remoteTableUrlGS", remoteTableUrlGS);
        return "mainmenu";
    }

    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("date") String date,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        model.addAttribute("remoteTableUrlGS", remoteTableUrlGS);
        try(InputStream is = new BufferedInputStream(file.getInputStream())) {
            List<WB> wb = ExelMapper.mapWB(is);
            wbRepository.saveAllSafe(wb);
            Integer index = googleSheetsNuSeller.getLastRowIndex() + 3;
            List<NuSeller> nuSeller = nuSellerWBMapper.map(wb, index, date);
            nuSellerRepository.saveAllSafe(nuSeller);
            googleSheetsNuSeller.writeModels(nuSeller, index);
            log.info("+Update tables success");
        } catch (Throwable e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            return "mainmenu";
        }
        model.addAttribute("success", true);
        return "mainmenu";
    }

    @RequestMapping(value="/download", method=RequestMethod.GET)
    public void downloadFile(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", wbFilePath);
            response.setHeader(headerKey, headerValue);
            FileInputStream inputStream;
            try {
                inputStream = new FileInputStream(FileManager.getFromResources(wbFilePath));
                try {
                    int c;
                    while ((c = inputStream.read()) != -1) {
                        response.getWriter().write(c);
                    }
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    response.getWriter().close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(Throwable.class)
    public String handleStorageFileNotFound(Throwable exc) {
        exc.printStackTrace();
        return "unknownError";
    }
}
