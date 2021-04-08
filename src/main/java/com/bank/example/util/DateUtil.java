package com.bank.example.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    private static final LocalDateTime initDate = LocalDateTime.of(2021, 1, 1,0,0,0);

    private static final Map<String, DateGenerator> generatorMap = new HashMap<>();

    public static DateUtilGetter forSpace(String id) {
        if (!generatorMap.containsKey(id)) {
            generatorMap.put(id, new DateGenerator());
        }

        return new DateUtilGetter(generatorMap.get(id));
    }

    public static class DateUtilGetter {

        private final DateGenerator dateGenerator;

        DateUtilGetter(DateGenerator dateGenerator) {
            this.dateGenerator = dateGenerator;
        }

        public LocalDateTime getNextDateTime() {
            return dateGenerator.getNextDateTime();
        }
    }

    private static class DateGenerator {

        private int currentDay;

        LocalDateTime getNextDateTime() {
            return initDate.plusDays(currentDay++);
        }
    }
}
