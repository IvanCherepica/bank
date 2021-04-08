package com.bank.example.sqlformatter;

import com.bank.example.sqltracker.QueryCountInfoHolder;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

public class SimpleFormatter implements MessageFormattingStrategy {
    private static final Formatter HIBERNATE_SQL_FORMATTER = new BasicFormatterImpl() {
        @Override
        public String format(String source) {
            return super.format(source)
                    .replaceAll(" */\\*", " /\\*")
                    .replaceAll("\n *\\*/", " */\n");
        }
    };

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        if (category.equals("commit")) {
            QueryCountInfoHolder.getQueryInfo().incrementCommitCount();
        }
        return String.format("%n Hibernate: %s %s %n{elapsed: %dms}", category, HIBERNATE_SQL_FORMATTER.format(sql), elapsed);
    }
}
