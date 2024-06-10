package MachineCoding.TicTacToe.controllers;

import MachineCoding.TicTacToe.services.GameService;
import MachineCoding.TicTacToe.models.Game;
import MachineCoding.TicTacToe.models.Player;
import MachineCoding.TicTacToe.models.exceptions.BotCountException;
import MachineCoding.TicTacToe.models.exceptions.DimensionException;
import MachineCoding.TicTacToe.models.exceptions.DuplicateSymbolException;
import MachineCoding.TicTacToe.models.exceptions.PlayerCountException;
import MachineCoding.TicTacToe.models.winningStrategies.WinningStrategy;

import java.util.List;

// controller's responsibility is to just forward the request to the appropriate logic -> model, services
public class GameController {
    GameService gameService = GameService.getInstance();

    public Game createGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) throws PlayerCountException, DuplicateSymbolException, BotCountException, DimensionException {
        return gameService.createGame(dimension, players, winningStrategies);
    }

    public void runGame(Game game){
        gameService.runGame(game);
    }

    public void displayBoard(Game game){
        gameService.displayBoard(game);
    }

    public void makeMove(Game game){
        gameService.makeMove(game);
    }

    public void checkWinner(Game game){
        gameService.makeMove(game);
    }

    public void checkForUndo(Game game){
        gameService.checkForUndo(game);
    }
}
