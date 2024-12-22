package com.atri.service;

import com.atri.entity.Record;

import java.util.List;

public interface RecentRecordService {
    List<Record> getRecentRecordList();

    Integer getMaxScore();

    @SuppressWarnings("all")
    int addRecentRecord(String role, int score);

    @SuppressWarnings("all")
    int clearRecord();
}
