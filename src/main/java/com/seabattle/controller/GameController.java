package com.seabattle.controller;

import com.seabattle.entity.Game;
import com.seabattle.entity.enums.Level;
import com.seabattle.services.ArrangementService;
import com.seabattle.services.GamePropertiesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    private final GamePropertiesService gamePropertiesService;
    private final ArrangementService arrangementService;

    @GetMapping(path = "/check/{login}")
    public ResponseEntity checkLogin(@PathVariable String login) {
        return ResponseEntity.ok(gamePropertiesService.checkLogin(login));
    }

    @GetMapping(path = "/game/{login}")
    public ResponseEntity addGame(@PathVariable String login, @RequestParam boolean multiplayer, @RequestParam(required = false) Level level) {
        gamePropertiesService.addGames(new Game(login, level, multiplayer));
        return ResponseEntity.ok(login);
    }

    @GetMapping(path = "/game/{login}/field/random")
    public ResponseEntity getRandomArrangement(@PathVariable String login) {
        return ResponseEntity.ok(arrangementService.getRandomArrangement(gamePropertiesService.getGameField(login)));
    }

    @MessageMapping("/shot")
    public void executeShot(Map<String, String> params) {
        String login = params.get('login');
        int x = Integer.parseInt(params.get('x'));
        int y = Integer.parseInt(params.get('y'));
    }
}
