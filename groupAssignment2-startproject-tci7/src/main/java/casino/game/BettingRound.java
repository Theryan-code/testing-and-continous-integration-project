package casino.game;

import casino.idfactory.BettingRoundID;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.IBetLoggingAuthority;
import casino.bet.Bet;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class BettingRound implements IBettingRound {
    private BettingRoundID bettingRoundID;
    private BetToken betToken;
    public HashSet<Bet> betSet = new HashSet<>();

    public BettingRound(BettingRoundID bettingRoundID, BetToken betToken) {
        this.bettingRoundID = bettingRoundID;
        this.betToken = betToken;
    }

    public BettingRound(){}

    /**
     * @should get current betting round id
     */
    @Override
    public BettingRoundID getBettingRoundID() {
        return bettingRoundID;
    }

    /**
     * @should add a bet to the current bettinground.
     *
     * @param bet
     * @return true if bet is made, otherwise folse
     * @throws IllegalArgumentException when Bet is null
     */
    @Override
    public boolean placeBet(Bet bet) throws IllegalArgumentException {
        if (bet == null){
            throw new IllegalArgumentException("Bet cannot be null");
        }
        betSet.add(bet);
        System.out.println(betSet.size());
        return true;
    }

    /**
     *
     * @should return set of all bets made in this betting round.
     */
    @Override
    public Set<Bet> getAllBetsMade() {
        return betSet;
    }

    /**
     * @should return betToken from this betting round.
     */
    @Override
    public BetToken getBetToken() {
        return betToken;
    }

    /**
     *
     * @should return number of bets made in the betting round
     */
    @Override
    public int numberOFBetsMade() {
        return betSet.size();
    }
}
