package com.bank.example.dao;

import com.bank.example.model.Settings;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsDaoImpl extends AbstractDao<Long, Settings> implements SettingsDao {
    @Override
    public Settings getSettingsByAccountId(Long accountId) {
        return entityManager.createQuery(
                "SELECT s FROM Settings s WHERE s.account.id = :accountId",
                Settings.class
        )
                .setParameter("accountId", accountId)
                .getSingleResult();
    }
}
