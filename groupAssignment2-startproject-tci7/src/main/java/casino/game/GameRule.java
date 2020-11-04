package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameRule implements IGameRule{
    private MoneyAmount moneyAmount;
    private int maxBetsPerRound;

    public GameRule(MoneyAmount moneyAmount, int maxBetsPerRound){
        this.moneyAmount = moneyAmount;
        this.maxBetsPerRound = maxBetsPerRound;
    }

    @Override
    public BetResult determineWinner(Integer randomWinValue, Set<Bet> bets) throws NoBetsMadeException {
        if(bets.isEmpty()) throw new NoBetsMadeException();
        else {
            List<Bet> betsList = new ArrayList<>(bets);
            int winningBetIndex = (int) Math.round(Double.valueOf(randomWinValue) / 100 * (bets.size() - 1));
            BetResult betResult = new BetResult(betsList.get(winningBetIndex), moneyAmount);
            return betResult;
        }
    }

    @Override
    public int getMaxBetsPerRound() {
        return maxBetsPerRound;
    }
}
