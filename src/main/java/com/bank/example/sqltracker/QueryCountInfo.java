package com.bank.example.sqltracker;

import lombok.Getter;

import static com.bank.example.sqltracker.QueryCountInfoHolder.getQueryInfo;

@Getter
public class QueryCountInfo {
    private int selectCount;
    private int nextvalCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int callCount;
    private int commitCount;

    public void incrementSelectCount() {
        selectCount++;
    }

    public void incrementInsertCount() {
        insertCount++;
    }

    public void incrementNextvalCount() {
        nextvalCount++;
    }

    public void incrementUpdateCount() {
        updateCount++;
    }

    public void incrementDeleteCount() {
        deleteCount++;
    }

    public void incrementCommitCount() {
        commitCount++;
    }

    public void incrementCallCount() {
        callCount++;
    }

    public void clear() {
        selectCount = 0;
        nextvalCount = 0;
        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        callCount = 0;
        commitCount = 0;
    }

    public int countAll() {
        return selectCount + nextvalCount + insertCount + updateCount + deleteCount + callCount;
    }

    public String getReport() {
        StringBuilder report = new StringBuilder("\nSQL COUNT:");
        report.append("\n    select count: ").append(getSelectCount());
        report.append("\n    insert count: ").append(getInsertCount());
        report.append("\n    update count: ").append(getUpdateCount());
        report.append("\n    delete count: ").append(getDeleteCount());
        report.append("\n    nextVal count: ").append(getNextvalCount());
        report.append("\n    call count: ").append(getCallCount());
        report.append("\n    commit count: ").append(getCommitCount());
        report.append("\n TOTAL: ").append((countAll()));
        return report.toString();
    }
}
