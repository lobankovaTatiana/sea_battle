package com.seabattle.services;

import com.seabattle.entity.GameField;
import com.seabattle.entity.enums.CellStatus;
import com.seabattle.utils.GameUtils;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Log
public class ArrangementService {

    public GameField getRandomArrangement(GameField field) {
        return getArrangement(field, true);
    }

    public GameField getDiagonalArrangement(GameField field) {
        return getArrangement(field, false);
    }

    private GameField getArrangement(GameField field, boolean useDiagonal) {
        for (int i = 1; i <= 4; i++) {
            setShips(GameUtils.ships().get(i), i, field, useDiagonal);
        }
        return field;
    }

    public GameField getCoastArrangement(GameField field) {
        for (int i = 2; i <= 4; i++) {
            setShips(GameUtils.ships().get(i), i, field, false);
            int c = GameUtils.ships().get(i);
            while ( c > 0) {
                boolean q = false;
                while (!q) {
                    int side = GameUtils.getRandomIntegerBetweenRange(0, 1);
                    if(side == 1) {
                        List<Integer> xList = Arrays.asList(0, 9);
                        int y = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
                        int x = xList.get( GameUtils.getRandomIntegerBetweenRange(0, 1));
                        q = checkLocationShip(side, x, y, i, field, false);
                        if (q) {
                            setShip(x, y, i, side, field);
                        }
                    } else {
                        List<Integer> yList = Arrays.asList(0, 9);
                        int x = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
                        int y = yList.get( GameUtils.getRandomIntegerBetweenRange(0, 1));
                        q = checkLocationShip(side, x, y, i, field, false);
                        if (q) {
                            setShip(x, y, i, side, field);
                        }
                    }
                }
                c--;
            }
        }
        // однопалубные расставляем в любом месте кроме диагоналей
        setShips(4, 1, field, false);
        return field;
    }

    private void setShips(int c, int l, GameField field, boolean useDiagonal) {
        log.info("Try to set ship count = " + c + ", length = " + l);
        while (c > 0) {
            boolean q = false;
            while (!q) {
                int side = GameUtils.getRandomIntegerBetweenRange(0, 1);
                int x = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
                int y = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
                log.info("Check location side = " + side + ", x = " + x + ", y = " + y);

                q = checkLocationShip(side, x, y, l, field, useDiagonal);
                if (q) {
                    log.info("Set ship x = " + x + ", y = " + y + ", length = " + l + ", side = " + side);
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

    private boolean checkLocationShip(int side, int x, int y, int l, GameField field, boolean useDiagonal) {
        int err = 0;
        switch (side) {
            case 0:
                if (x + l <= GameUtils.SIZE) {
                    for (int i = 0; i < l; i++)
                        if ((!useDiagonal && (checkMainDiagonal(x + i, y) || checkSecondaryDiagonal(x + i, y))) || !checkCellArea(x + i, y, field))
                            err++;
                } else {
                    err++;
                }
                break;
            case 1:
                if (y + l <= GameUtils.SIZE) {
                    for (int i = 0; i < l; i++)
                        if ((!useDiagonal && (checkMainDiagonal(x, y + i) || checkSecondaryDiagonal(x, y + i))) || !checkCellArea(x, y + i, field))
                            err++;
                } else {
                    err++;
                }
                break;
        }
        return err == 0;
    }

    private boolean checkMainDiagonal(int x, int y) {
        log.info("checkMainDiagonal = " + (x == y));
        return x == y;
    }

    private boolean checkSecondaryDiagonal(int x, int y) {
        log.info("checkSecondaryDiagonal = " + (x == (GameUtils.SIZE - y + 1)));
        return x == (GameUtils.SIZE - y - 1);
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
