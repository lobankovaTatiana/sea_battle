package com.seabattle.services;

import com.seabattle.entity.GameField;
import com.seabattle.entity.enums.CellStatus;
import com.seabattle.utils.GameUtils;
import org.springframework.stereotype.Service;

@Service
public class ArrangementService {

    public GameField getRandomArrangement(GameField field) {
        for (int i = 1; i <= 4; i++) {
            setShips(GameUtils.ships().get(i), i, field);

        }
        return field;
    }

    private void setShips(int c, int l, GameField field) {
        while (c > 0) {
            boolean q = false;
            while (!q) {
                int side = GameUtils.getRandomIntegerBetweenRange(0, 1);
                int x = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
                int y = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);

                q = checkLocationShip(side, x, y, l, field);

                if (q) {
                    setShip(x, y, l, side, field);
                }
            }
            c--;
        }
    }

    private void setShipInCell(int x, int y, GameField field) {
        field.getCells().get(y).set(x, CellStatus.SHIP);
    }

    private void setShip(int x, int y, int l, int side, GameField field) {
        switch (side) {
            case 0:
                for (int i = 0; i < l; i++)
                    setShipInCell(x + i, y, field);
                break;
            case 1:
                for (int i = 0; i < l; i++)
                    setShipInCell(x, y + i, field);
                break;
        }
    }

    private boolean checkLocationShip(int side, int x, int y, int l, GameField field) {
        int err = 0;
        switch (side) {
            case 0:
                if (x + l <= GameUtils.SIZE) {
                    for (int i = 0; i < l; i++)
                        if (!checkCellArea(x + i, y, field))
                            err++;
                } else {
                    err++;
                }
                break;
            case 1:
                if (y + l <= GameUtils.SIZE) {
                    for (int i = 0; i < l; i++)
                        if (!checkCellArea(x, y + i, field))
                            err++;
                } else {
                    err++;
                }
                break;
        }
        return err == 0;
    }

    private boolean checkCellArea(int x, int y, GameField field) {
        return checkCell(x - 1, y - 1, field) && checkCell(x, y - 1, field) && checkCell(x + 1, y - 1, field) &&
                checkCell(x - 1, y, field) && checkCell(x, y, field) && checkCell(x + 1, y, field) &&
                checkCell(x - 1, y + 1, field) && checkCell(x, y + 1, field) && checkCell(x + 1, y + 1, field);
    }

    private boolean checkCell(int x, int y, GameField field) {
        if (x >= 0 && x < GameUtils.SIZE && y >= 0 && y < GameUtils.SIZE)
            return field.getCells().get(y).get(x) == CellStatus.WATER;
        else
            return true;
    }
}
