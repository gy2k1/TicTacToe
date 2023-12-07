import controllers.GameController;
import exceptions.BotCountException;
import exceptions.PlayerCountDimensionMismatchException;
import exceptions.SymbolCountException;
import models.*;
import strategies.Strategies.winningStartegies.OrderOneWinningStrategy;

import java.util.ArrayList;
import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws BotCountException, SymbolCountException, PlayerCountDimensionMismatchException {
        GameController gameController = new GameController();
        int sizeOfBoard=3;
        Player gaurav = new Player("Gaurav", 1, new Symbol('X'), PlayerType.HUMAN);
        Player abhishek = new Player("Abhishek", 2, new Symbol('O'), PlayerType.HUMAN);
        Game game = gameController.startGame(new ArrayList<>(Arrays.asList(gaurav,abhishek)),sizeOfBoard,new OrderOneWinningStrategy(3));
        while(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
            gameController.displayBoard(game);
            gameController.makeMove(game);
        }
        if(gameController.checkState(game).equals(GameState.SUCCESS)){
            System.out.println("Winner is "+game.getWinner().getName());
        } else if (gameController.checkState(game).equals(GameState.DRAW)){
            System.out.println("Game is Drawn");
        }
    }
}