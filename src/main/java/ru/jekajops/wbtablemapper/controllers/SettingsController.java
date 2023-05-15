package ru.jekajops.wbtablemapper.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jekajops.wbtablemapper.models.Setting;
import ru.jekajops.wbtablemapper.models.dto.UpdateSettingsFormData;
import ru.jekajops.wbtablemapper.repository.SettingsRepository;
import ru.jekajops.wbtablemapper.util.AppProperties;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/settings")
@Slf4j(topic = "Logger")
public class SettingsController extends AController {
    SettingsRepository settingsRepository;

    @Autowired
    public SettingsController(AppProperties appProperties, SettingsRepository settingsRepository) {
        super(appProperties);
        this.settingsRepository = settingsRepository;
    }

    @GetMapping("/")
    public String settings(Model model) {
        List<Setting> settings = settingsRepository.findAll();
        model.addAttribute("success", false);
        model.addAttribute("error", false);
        model.addAttribute("settingsForm", new UpdateSettingsFormData(settings));
        model.addAttribute("settingsDefault", settings);
        return "settings";
    }

    @PostMapping("/update")
    public String updateSettings(@ModelAttribute("settingsForm") UpdateSettingsFormData settingsForm, BindingResult bindingResult, Model model) {
        try {
            model.addAttribute("settingsForm", settingsForm);
            Optional.ofNullable(settingsForm.getSettings())
                    .map(settings -> settingsRepository.saveAllAndFlush(settings));
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("error", true);
        }
        return "settings";
    }

    @ExceptionHandler(Throwable.class)
    public String handleStorageFileNotFound(Throwable exc) {
        exc.printStackTrace();
        return "unknownError";
    }
}
