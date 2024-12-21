package com.atri.dao;

import com.atri.entity.Record;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

public interface Mapper {
    @Select("select id, role, score, date_time from recent")
    List<Record> selectRecent();

    @Select("select max(score) from recent")
    int selectMaxScore();

    @Insert("insert into recent (user_id, role, score, date_time) " +
            "values (#{user_id}, #{role}, #{score}, #{date_time})")
    int insertRecentRecord(@Param("user_id") int userId,
                           @Param("role") String role,
                           @Param("score") int score,
                           @Param("date_time")LocalDateTime dateTime);

    @Delete("delete from recent")
    int deleteRecent();

    @Delete("delete from sqlite_sequence where name = 'recent'")
    void resetAutoIncrement();

}
