package com.atri.service.impl;

import com.atri.dao.Mapper;
import com.atri.entity.Record;
import com.atri.service.RecentRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecentRecordServiceImpl implements RecentRecordService {

    @Resource
    Mapper mapper;

    @Override
    public List<Record> getRecentRecordList() {
        return mapper.selectRecent();
    }

    @Override
    public int getMaxScore() {
        return mapper.selectMaxScore();
    }

    @Override
    public int addRecentRecord(String role, int score) {
        return mapper.insertRecentRecord(1, role, score, LocalDateTime.now());     // 将 user_id 默认设为 1
    }

    @Transactional
    @Override
    public int clearRecord() {
        int deletedCount = mapper.deleteRecent();
        mapper.resetAutoIncrement();
        if (deletedCount == 0) {
            throw new RuntimeException("删除记录失败qaq");
        }
        return deletedCount;
    }
}
