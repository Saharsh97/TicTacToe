package MachineCoding.TicTacToe.services;

import MachineCoding.TicTacToe.models.Board;
import MachineCoding.TicTacToe.models.Cell;
import MachineCoding.TicTacToe.models.Move;
import MachineCoding.TicTacToe.models.Player;
import MachineCoding.TicTacToe.models.enums.CellState;

import java.util.Scanner;

public class HumanPlayerService extends PlayerService {
    private static HumanPlayerService INSTANCE = new HumanPlayerService();
    private HumanPlayerService(){}
    public static HumanPlayerService getInstance(){
        return INSTANCE;
    }

    public Move makeMove(Player player, Board board){
        Cell chosenCell = getValidChosenCell(player, board);

        chosenCell.setPlayer(player);
        chosenCell.setCellState(CellState.OCCUPIED);

        Move move = new Move(chosenCell, player);
        return move;
    }

    private Cell getValidChosenCell(Player player, Board board){
        while(true) {
            System.out.println("Player " + player.getName() + ", make your move");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Row: ");
            int row = scanner.nextInt();

            System.out.print("Enter Column: ");
            int column = scanner.nextInt();

            // 1. validate the row, column inputs, if it is within boundary. ask for inputs again.
            if(!isValidInput(row, column, board.getSize())){
                continue;
            }

            Cell chosenCell = board.getBoard().get(row).get(column);

            // 2. validate that this cell is empty. if not empty, ask for input again.
            if(isValidCellChosen(chosenCell)){
                return chosenCell;
            }
        }
    }

    private boolean isValidInput(int row, int column, int size){
        if(row >= 0 && row < size && column >= 0 && column < size){
            return true;
        }
        System.out.println("\nPlease provide the inputs within boundary! Please try again...\n");
        return false;
    }

    private boolean isValidCellChosen(Cell chosenCell){
        if (chosenCell.getCellState() != CellState.EMPTY) {
            System.out.println("\nThis cell is already occupied! Please try again...\n");
            return false;
        }
        return true;
    }
}
