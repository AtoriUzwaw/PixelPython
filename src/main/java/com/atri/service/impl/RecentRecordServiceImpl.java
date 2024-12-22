package com.atri.service.impl;

import com.atri.dao.Mapper;
import com.atri.entity.Record;
import com.atri.service.RecentRecordService;
import com.atri.util.RecordDeletionException;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log
public class RecentRecordServiceImpl implements RecentRecordService {

    @Resource
    Mapper mapper;

    @Override
    public List<Record> getRecentRecordList() {
        return mapper.selectRecent();
    }

    @Override
    public Integer getMaxScore() {
        return mapper.selectMaxScore();
    }

    @Override
    public int addRecentRecord(String role, int score) {
        // 将 user_id 默认设为 1
        return mapper.insertRecentRecord(1, role, score, LocalDateTime.now());
    }

    @Transactional  // 使用实务操作，保证原子性
    @Override
    public int clearRecord() {
        int deletedCount = 0;
        try {
            Integer maxScore = mapper.selectMaxScore();
            if (maxScore == null) throw new RecordDeletionException("重复删除记录qaq");
            deletedCount = mapper.deleteRecent();
            mapper.resetAutoIncrement();
        } catch (RecordDeletionException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            log.warning(e.getMessage());
            // 触发回滚
            throw new RuntimeException("系统错误，删除失败qaq", e);
        }
        return deletedCount;
    }
}
