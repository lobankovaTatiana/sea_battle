package com.seabattle.entity;

import com.seabattle.entity.enums.Level;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {
    private String login;
    private Level level;
    private boolean multiplayer;
    private String enemy;

    public Game(String login, Level level, boolean multiplayer) {
        this.login = login;
        this.level = level;
        this.multiplayer = multiplayer;
    }
}
