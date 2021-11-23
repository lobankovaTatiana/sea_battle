package com.seabattle.services;

import com.seabattle.entity.Cell;
import com.seabattle.entity.GameField;
import com.seabattle.entity.enums.CellStatus;
import com.seabattle.utils.GameUtils;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ShootingService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final GamePropertiesService gamePropertiesService;

    public void randomShot(String login) {
        GameField field = gamePropertiesService.getGameField(login);
        //ToDo: доделать добивание
        Cell res = null;
        while (Objects.isNull(res) && res.getStatus() != CellStatus.WATER) {
            int x = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
            int y = GameUtils.getRandomIntegerBetweenRange(0, GameUtils.SIZE - 1);
            res = new Cell(x, y, field.getCells().get(y).get(x));
        }
        messagingTemplate.convertAndSend("shot_" + login, res);
    }

    public void checkShot(String login, int x, int y) {
        GameField field = gamePropertiesService.getEnemyGameField(login);
        CellStatus status = field.getCells().get(y).get(x);
        // ToDo: чекать на ранение и убийство
        switch (status) {
            case WATER:
                field.getCells().get(y).set(x, CellStatus.MISS);
                break;
            case SHIP:
                field.getCells().get(y).set(x, CellStatus.INJURED);
                break;
            default:
                field.getCells().get(y).set(x, CellStatus.MISS);
        }
        messagingTemplate.convertAndSend("check_" + login, field);
    }
}
