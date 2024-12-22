package com.atri.dao;

import com.atri.entity.Record;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mapper 接口，用于与数据库交互，执行针对 `recent` 表的增删改查操作。
 */
public interface Mapper {

    /**
     * 查询最近的记录。
     *
     * @return 返回按 `id` 降序排列的最近记录列表。
     */
    @Select("select id, role, score, date_time from recent order by id desc ")
    List<Record> selectRecent();

    /**
     * 查询 `recent` 表中的最大分数。
     *
     * @return 返回 `recent` 表中的最大分数。如果表为空，则返回 `null`。
     */
    @Select("select max(score) from recent")
    Integer selectMaxScore();

    /**
     * 插入一条新的记录到 `recent` 表。
     *
     * @param userId 用户的 ID。
     * @param role 用户的角色。
     * @param score 用户的分数。
     * @param dateTime 记录的日期和时间。
     * @return 插入操作成功时返回 1，失败时返回 0。
     */
    @Insert("insert into recent (user_id, role, score, date_time) " +
            "values (#{user_id}, #{role}, #{score}, #{date_time})")
    int insertRecentRecord(@Param("user_id") int userId,
                           @Param("role") String role,
                           @Param("score") int score,
                           @Param("date_time") LocalDateTime dateTime);

    /**
     * 删除所有的记录。
     *
     * @return 删除操作成功时返回删除的行数，失败时返回 0。
     */
    @Delete("delete from recent")
    int deleteRecent();

    /**
     * 重置 `recent` 表的自增序列。
     */
    @Delete("delete from sqlite_sequence where name = 'recent'")
    void resetAutoIncrement();
}

