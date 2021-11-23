package com.seabattle.entity;

import com.seabattle.entity.enums.CellStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    private Integer x;
    private Integer y;
    private CellStatus status;
}
