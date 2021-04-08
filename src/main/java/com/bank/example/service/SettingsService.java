package com.bank.example.service;

import com.bank.example.model.Settings;

public interface SettingsService extends GenericService<Long, Settings> {

    Settings getSettingsByAccountId(Long accountId);
}
