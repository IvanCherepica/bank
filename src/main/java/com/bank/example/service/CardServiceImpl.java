package com.bank.example.service;

import com.bank.example.dao.CardDao;
import com.bank.example.model.Card;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl extends AbstractService<Long, Card> implements CardService {

    private final CardDao cardDao;

    public CardServiceImpl(CardDao cardDao) {
        super(cardDao);
        this.cardDao = cardDao;
    }

    @Override
    public Card getDefaultCardByAccountId(Long accountId) {
        return cardDao.getDefaultCardByAccountId(accountId);
    }
}
