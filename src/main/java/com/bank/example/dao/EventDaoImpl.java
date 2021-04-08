package com.bank.example.dao;

import com.bank.example.model.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventDaoImpl extends AbstractDao<Long, Event> implements EventDao {
}
