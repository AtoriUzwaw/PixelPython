package com.atri.service;

import com.atri.entity.Record;

import java.util.List;

/**
 * RecentRecordService接口，提供与游戏记录相关的操作。
 * 包括获取最近的记录、添加新的记录、以及清除所有记录等功能。
 */
public interface RecentRecordService {

    /**
     * 获取最近的游戏记录列表。
     *
     * @return 返回包含最近游戏记录的 {@link Record} 列表。
     */
    List<Record> getRecentRecordList();

    /**
     * 获取最近游戏记录中的最大得分。
     *
     * @return 返回最近记录中的最高得分。
     */
    Integer getMaxScore();

    /**
     * 添加一条新的游戏记录。
     *
     * @param role 游戏角色（例如玩家的角色）。
     * @param score 游戏得分。
     * @return 插入操作的结果（影响的行数）。
     */
    @SuppressWarnings("all")  // 忽略当前方法返回类型的警告
    int addRecentRecord(String role, int score);

    /**
     * 清除所有游戏记录。
     *
     * @return 被删除的记录条数。
     */
    @SuppressWarnings("all")  // 忽略当前方法返回类型的警告
    int clearRecord();
}

