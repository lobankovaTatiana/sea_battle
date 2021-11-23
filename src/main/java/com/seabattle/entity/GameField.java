package com.seabattle.entity;

import com.seabattle.entity.enums.CellStatus;
import com.seabattle.utils.GameUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameField {
    private List<List<CellStatus>> cells;

    public GameField() {
        this.cells = new ArrayList<>();
        for (int i = 0; i < GameUtils.SIZE; i++) {
            List<CellStatus> row = new ArrayList<>();
            for (int j = 0; j < GameUtils.SIZE; j++) {
                row.add(CellStatus.WATER);
            }
            this.cells.add(row);
        }
    }
}
