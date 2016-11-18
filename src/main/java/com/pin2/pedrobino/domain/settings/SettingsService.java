package com.pin2.pedrobino.domain.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings getSettings() {
        return (settingsRepository.findAll().size() > 0)
                ? settingsRepository.findAll().get(0)
                : null;
    }

    public Settings saveSettings(Settings settings) {

        if (getSettings() != null) {
            settings.setId(getSettings().getId());
        }

        return settingsRepository.save(settings);
    }
}
