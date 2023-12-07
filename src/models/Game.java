package models;

import exceptions.BotCountException;
import exceptions.PlayerCountDimensionMismatchException;
import exceptions.SymbolCountException;
import strategies.Strategies.winningStartegies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private Player winner;
    private WinningStrategy winningStrategy;
    public Board getBoard() {
        return board;
    }

    private Game(int dimension,
                 List<Player> players,
                 WinningStrategy winningStrategy){
        this.players = players;
        this.winningStrategy = winningStrategy;
        this.moves = new ArrayList<>();
        this.nextMovePlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.board = new Board(dimension);
    }

    public static Builder getBuilder(){
        return new Builder();
    }
    public static class Builder {
        private int dimension ;
        private List<Player> players;
        private WinningStrategy winningStrategy;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(WinningStrategy winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;

        }

        public Builder addPlayer(Player player){
            this.players.add(player);
            return this;
        }

//        public Builder addWinningStrategy(WinningStrategy winningStrategy){
//            this.winningStrategies.add(winningStrategy);
//            return this;
//        }
        private void validateBotCount() throws BotCountException {
            int botCount = 0;
            for(Player p : players){
                if(p.getPlayerType().equals(PlayerType.BOT)){
                    botCount += 1;
                }
            }
            if(botCount > 1){
                throw new BotCountException();
            }
        }

        private void validatePlayersCount() throws PlayerCountDimensionMismatchException {


            if(players.size() != dimension - 1){
                throw new PlayerCountDimensionMismatchException();
            }
        }

        private void validateSymbolsCount() throws SymbolCountException {
            Map<Character , Integer> symCount = new HashMap<>();

            for(Player p : players) {
                if (!symCount.containsKey(p.getSymbol().getaChar())) {
                    symCount.put(p.getSymbol().getaChar(), 0);
                }

                symCount.put(p.getSymbol().getaChar(),
                        symCount.get(p.getSymbol().getaChar()) + 1);

                if (symCount.get(p.getSymbol().getaChar()) > 1) {
                    throw new SymbolCountException();
                }
            }
        }
        private void validate() throws BotCountException, PlayerCountDimensionMismatchException, SymbolCountException {
            // validate single Bot players
            validateBotCount();
            // validate no of players == dimension - 1
            validatePlayersCount();
            // validate diff symbol for every player
            validateSymbolsCount();
        }
        public Game build() throws BotCountException, SymbolCountException, PlayerCountDimensionMismatchException {
            validate();
            return new Game(
                    this.dimension,
                    this.players,
                    this.winningStrategy
            );
        }
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public WinningStrategy getWinningStrategy() {
        return winningStrategy;
    }

    public void setWinningStrategy(WinningStrategy winningStrategy) {
        this.winningStrategy = winningStrategy;
    }

    public void displayBoard(){
        board.displayBoard();
    }

    public void makeMove(){
        Player currentPlayer = players.get(this.nextMovePlayerIndex);
        Move move = currentPlayer.makeMove(this.board);
        moves.add(move);

        if(winningStrategy.checkWinner(move,board)){
            setGameState(GameState.SUCCESS);
            setWinner(currentPlayer);
            return;
        }

        if(moves.size() == board.getSize() * board.getSize()){
            // Game has drawn
            setGameState(GameState.DRAW);
            return;
        }
        nextMovePlayerIndex = (nextMovePlayerIndex + 1) % players.size();
    }

}
