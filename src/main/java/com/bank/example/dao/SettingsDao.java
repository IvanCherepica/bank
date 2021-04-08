package com.bank.example.dao;

import com.bank.example.model.Settings;

public interface SettingsDao extends GenericDao<Long, Settings> {

    Settings getSettingsByAccountId(Long accountId);
}
