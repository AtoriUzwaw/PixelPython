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

/**
 * RecentRecordServiceImpl类，实现了RecentRecordService接口。
 * 提供了与游戏记录相关的实际操作，数据通过Mapper与数据库进行交互。
 */
@Service
@Log
public class RecentRecordServiceImpl implements RecentRecordService {

    @Resource  // 自动注入Mapper bean，用于数据库操作
    Mapper mapper;

    /**
     * 获取最近的游戏记录列表。
     * 通过调用Mapper的selectRecent()方法从数据库中获取数据。
     *
     * @return 返回最近的 {@link Record} 列表。
     */
    @Override
    public List<Record> getRecentRecordList() {
        return mapper.selectRecent();
    }

    /**
     * 获取最近游戏记录中的最大得分。
     * 通过调用Mapper的selectMaxScore()方法获取最高得分。
     *
     * @return 返回最大得分，如果没有记录则返回null。
     */
    @Override
    public Integer getMaxScore() {
        return mapper.selectMaxScore();
    }

    /**
     * 添加一条新的游戏记录。
     * 记录的用户ID默认设置为1，得分与当前时间一起保存。
     *
     * @param role 游戏角色（如玩家角色）。
     * @param score 游戏得分。
     * @return 插入操作的影响行数。
     */
    @Override
    public int addRecentRecord(String role, int score) {
        // 默认未接入在线模式，将用户 ID 统一设置为 1
        return mapper.insertRecentRecord(1, role, score, LocalDateTime.now());
    }

    /**
     * 清除所有游戏记录。
     * 此方法包含删除记录和重置自增ID的操作。
     * 如果删除失败，会触发回滚，确保事务的原子性。
     *
     * @return 返回删除的记录条数。
     */
    @Transactional  // 使用事务管理，确保操作的原子性
    @Override
    public int clearRecord() {
        int deletedCount = 0;
        try {
            // 先查询当前最大得分，如果没有记录，抛出异常
            Integer maxScore = mapper.selectMaxScore();
            if (maxScore == null) throw new RecordDeletionException("没有记录可以删除qaq");

            // 删除最近的记录，并重置自增ID
            deletedCount = mapper.deleteRecent();
            mapper.resetAutoIncrement();

        } catch (RecordDeletionException e) {
            log.info(e.getMessage());  // 记录自定义异常信息
        } catch (Exception e) {
            log.warning(e.getMessage());  // 记录其他异常信息
            // 出现异常时回滚事务，确保数据的一致性
            throw new RuntimeException("系统错误，删除记录失败qaq", e);
        }
        return deletedCount;
    }
}
