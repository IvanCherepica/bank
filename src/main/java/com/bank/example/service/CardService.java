package com.bank.example.service;

import com.bank.example.model.Card;

public interface CardService extends GenericService<Long, Card> {

    Card getDefaultCardByAccountId(Long accountId);
}
