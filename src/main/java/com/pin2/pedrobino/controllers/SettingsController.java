package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.settings.Settings;
import com.pin2.pedrobino.domain.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/admin-settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public Settings getSettings() {
        return settingsService.getSettings();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public Settings saveSettings(@RequestBody Settings settings) {
        return settingsService.saveSettings(settings);
    }

}
