package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;

import java.util.Set;

public class GameRules implements IGameRule{
    @Override
    public BetResult determineWinner(Integer randomWinValue, Set<Bet> bets) throws NoBetsMadeException {
        return null;
    }

    @Override
    public int getMaxBetsPerRound() {
        return 0;
    }
}
