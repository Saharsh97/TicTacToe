package MachineCoding.TicTacToe.services;

import MachineCoding.TicTacToe.models.Board;
import MachineCoding.TicTacToe.models.Cell;
import MachineCoding.TicTacToe.models.Move;
import MachineCoding.TicTacToe.models.Player;
import MachineCoding.TicTacToe.models.enums.CellState;

import java.util.Scanner;

public abstract class PlayerService {

    public abstract Move makeMove(Player player, Board board);
}
