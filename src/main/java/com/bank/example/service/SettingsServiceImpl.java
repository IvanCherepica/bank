package com.bank.example.service;

import com.bank.example.dao.SettingsDao;
import com.bank.example.model.Settings;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl extends AbstractService<Long, Settings> implements SettingsService {

    private final SettingsDao settingsDao;

    public SettingsServiceImpl(SettingsDao settingsDao) {
        super(settingsDao);
        this.settingsDao = settingsDao;
    }

    @Override
    public Settings getSettingsByAccountId(Long accountId) {
        return settingsDao.getSettingsByAccountId(accountId);
    }
}
