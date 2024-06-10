package MachineCoding.TicTacToe.services;

import MachineCoding.TicTacToe.models.Board;
import MachineCoding.TicTacToe.models.Bot;
import MachineCoding.TicTacToe.models.Move;
import MachineCoding.TicTacToe.models.Player;
import MachineCoding.TicTacToe.models.botPlayingStrategy.BotPlayingStrategy;
import MachineCoding.TicTacToe.models.factories.BotPlayingStrategyFactory;

public class BotPlayerService extends PlayerService {
    private static BotPlayerService INSTANCE = new BotPlayerService();
    private BotPlayerService(){}
    public static BotPlayerService getInstance(){
        return INSTANCE;
    }

    @Override
    public Move makeMove(Player player, Board board){
        BotPlayingStrategy playingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategyByDifficultyLevel((Bot)player);
        return playingStrategy.makeBotMove(board);
    }
}
