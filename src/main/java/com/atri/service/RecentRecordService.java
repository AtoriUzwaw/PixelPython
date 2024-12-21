package com.atri.service;

import com.atri.entity.Record;

import java.util.List;

public interface RecentRecordService {
    List<Record> getRecentRecordList();

    int getMaxScore();

    int addRecentRecord(String role, int score);

    int clearRecord();
}
