package com.atri.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Record {
    int id;
    String role;
    int score;
    LocalDateTime dateTime;
}
