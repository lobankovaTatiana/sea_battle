package com.seabattle.services;

import com.seabattle.entity.Game;
import com.seabattle.entity.GameField;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GamePropertiesService {
    private Hashtable<String, GameField> gameFieldsMap = new Hashtable<>();
    private Hashtable<String, Game> gamesMap = new Hashtable<>();

    public void addGames(Game game) {
        gamesMap.put(game.getLogin(), game);
        addGameField(new GameField(), game.getLogin());
    }

    public void addEnemy(String login, String enemy) {
        gamesMap.get(login).setEnemy(enemy);
    }

    public List<String> getAvailableGames() {
        return gamesMap.values().stream().
                filter(g -> g.isMultiplayer() && StringUtils.hasText(g.getEnemy())).
                map(Game::getLogin).
                collect(Collectors.toList());
    }

    public GameField getGameField(String login) {
        return gameFieldsMap.get(login);
    }

    public void addGameField(GameField field, String login) {
        gameFieldsMap.put(login, field);

    }

    public boolean checkLogin(String login) {
        return !gamesMap.containsKey(login);
    }
}
