package com.atri.service.impl;

import com.atri.dao.Mapper;
import com.atri.entity.Record;
import com.atri.service.RecentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecentServiceImpl implements RecentService {

    @Resource
    Mapper mapper;

    @Override
    public List<Record> getRecentList() {
        return mapper.selectRecent();
    }
}
