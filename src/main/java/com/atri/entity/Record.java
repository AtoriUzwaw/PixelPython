package com.atri.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成绩记录类，存储游戏成绩的相关信息。
 */
@Data
@AllArgsConstructor
public class Record {
    private int id;
    private String role;
    private int score;
    private LocalDateTime dateTime;
}

