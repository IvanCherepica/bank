package com.bank.example.dao;

import com.bank.example.model.Card;

public interface CardDao extends GenericDao<Long, Card> {

    Card getDefaultCardByAccountId(Long accountId);
}
