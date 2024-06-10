package MachineCoding.TicTacToe.services;

import MachineCoding.TicTacToe.models.Board;
import MachineCoding.TicTacToe.models.Game;
import MachineCoding.TicTacToe.models.Move;
import MachineCoding.TicTacToe.models.Player;
import MachineCoding.TicTacToe.models.enums.CellState;
import MachineCoding.TicTacToe.models.enums.GameState;
import MachineCoding.TicTacToe.models.enums.PlayerType;
import MachineCoding.TicTacToe.models.exceptions.BotCountException;
import MachineCoding.TicTacToe.models.exceptions.DimensionException;
import MachineCoding.TicTacToe.models.exceptions.DuplicateSymbolException;
import MachineCoding.TicTacToe.models.exceptions.PlayerCountException;
import MachineCoding.TicTacToe.models.winningStrategies.WinningStrategy;
import MachineCoding.TicTacToe.services.factories.PlayerServiceFactory;

import java.util.List;
import java.util.Scanner;

public class GameService {
    private static GameService INSTANCE = new GameService();
    private GameService(){}
    public static GameService getInstance(){
        return INSTANCE;
    }

    public void runGame(Game game){
        System.out.println("game has been started");
        displayBoard(game);
        while(game.getGameState() == GameState.IN_PROGRESS){
            makeMove(game);
            displayBoard(game);
            if(checkWinner(game)){
                break;
            }
            checkForUndo(game);
        }

        if(game.getGameState() == GameState.COMPLETED){
            System.out.println("Game is completed. Winner is " + game.getWinner().getName());
        }
        if(game.getGameState() == GameState.DRAW){
            System.out.println("Game is a Draw");
        }
    }

    public void displayBoard(Game game){
        game.getBoard().displayBoard();
    }

    public void makeMove(Game game){
        List<Player> players = game.getPlayers();
        int currentPlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = players.get(currentPlayerIndex);

        // make the player move.
        Board board = game.getBoard();
        // this player could be bot or human. get the respective service that handles the move.
        PlayerService playerService = PlayerServiceFactory.getPlayerServiceGivenPlayer(currentPlayer);
        Move move = playerService.makeMove(currentPlayer, board);
        game.addMove(move);

        // update the countMaps
        for(WinningStrategy winningStrategy: game.getWinningStrategies()){
            winningStrategy.updateCount(game.getBoard(), move);

            if(winningStrategy.checkWinner(board, move)){
                game.setWinner(currentPlayer);
                game.setGameState(GameState.COMPLETED);
                return;
            }
        }

        if(game.getMoves().size() == board.getSize() * board.getSize()){
            game.setGameState(GameState.DRAW);
            return;
        }

        int nextPlayerIndex = (currentPlayerIndex + 1) % (players.size());
        game.setCurrentPlayerIndex(nextPlayerIndex);
    }

    public boolean checkWinner(Game game){
        return game.getWinner() != null;
    }

    public void checkForUndo(Game game){
        List<Move> moves = game.getMoves();
        int movesSize = moves.size();
        Move lastMove = moves.get(movesSize-1);
        if(lastMove.getPlayer().getPlayerType() == PlayerType.BOT){
            return;
        }

        System.out.println("Do you want to Undo ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if(input.equals("Y")){
            performUndo(game);
        }
    }

    private void performUndo(Game game){
        // update moves list. remove last move.
        List<Move> moves = game.getMoves();
        int movesSize = moves.size();
        Move lastMove = moves.get(movesSize-1);
        moves.remove(movesSize-1);


        // reset the player index
        List<Player> players = game.getPlayers();
        int currentIndex = game.getCurrentPlayerIndex();
        int newIndex = currentIndex != 0 ? currentIndex - 1 : players.size()-1;
        game.setCurrentPlayerIndex(newIndex);

        // reset the cell
        lastMove.getCell().setPlayer(null);
        lastMove.getCell().setCellState(CellState.EMPTY);

        // decrement the countMap
        Board board = game.getBoard();
        for(WinningStrategy winningStrategy: game.getWinningStrategies()){
            winningStrategy.handleUndo(board, lastMove);
        }
    }
}
