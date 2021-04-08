package com.bank.example.sqlLogger;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.Slf4JLogger;


public class CustomSLF4JLogger extends Slf4JLogger {
    private String lastSql = "";

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql) {
        if (category == Category.BATCH) {
            if (sql.equals(lastSql)) {
                return;
            } else {
                lastSql = sql;
            }
        }
        super.logSQL(connectionId, now, elapsed, category, prepared, sql);
    }
}
