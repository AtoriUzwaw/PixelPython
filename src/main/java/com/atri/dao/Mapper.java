package com.atri.dao;

import com.atri.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface Mapper {
    @Select("select id, score, date_time from recent")
    List<Record> selectRecent();
}
