package MachineCoding.TicTacToe.services.factories;

import MachineCoding.TicTacToe.models.Player;
import MachineCoding.TicTacToe.models.enums.PlayerType;
import MachineCoding.TicTacToe.services.BotPlayerService;
import MachineCoding.TicTacToe.services.HumanPlayerService;
import MachineCoding.TicTacToe.services.PlayerService;

public class PlayerServiceFactory {
    public static PlayerService getPlayerServiceGivenPlayer(Player player){
        if(player.getPlayerType() == PlayerType.BOT){
            return BotPlayerService.getInstance();
        }
        return HumanPlayerService.getInstance();
    }
}
