package com.bank.example.service;

import com.bank.example.dao.EventDao;
import com.bank.example.model.Event;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends AbstractService<Long, Event> implements EventService {

    public EventServiceImpl(EventDao eventDao) {
        super(eventDao);
    }
}
