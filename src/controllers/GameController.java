package controllers;

import exceptions.BotCountException;
import exceptions.PlayerCountDimensionMismatchException;
import exceptions.SymbolCountException;
import models.Board;
import models.Game;
import models.GameState;
import models.Player;
import strategies.Strategies.winningStartegies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(List<Player> playerList, int size, WinningStrategy winningStrategy) throws BotCountException, PlayerCountDimensionMismatchException, SymbolCountException {
        return Game.getBuilder().setDimension(size).setPlayers(playerList).setWinningStrategies(winningStrategy).build();
    }

    public void makeMove(Game game){
        game.makeMove();
    }

    public void displayBoard(Game game){
        game.displayBoard();
    }
    public void undoMove(){

    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }
}
