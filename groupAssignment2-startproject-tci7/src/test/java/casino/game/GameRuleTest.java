package casino.game;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class GameRuleTest {
    @Rule
    public Timeout globalTimeout = new Timeout(1500, TimeUnit.MILLISECONDS);

    private static final int ARBITRARY_MAX_BETS_PER_ROUND = 7;

    /**
     * A parameterized test. Different Sets of Bets and numbers (which simulates the randomly generated
     * number) are given. The right winner according to the number is expected to be chosen.
     *
     * The Bets contained in the Set is mocked.
     *
     * @param randomWinValue
     * @param maxBetsPerRound
     * @param winningBetIndex
     * @param betsArray
     * @throws NoBetsMadeException
     */
    @Test
    @Parameters(method = "generateRandomIntegerMaxBetsWinningBetIndexAndSetOfBets")
    public void DetermineWinner_ShouldPickOneBetFromSetOfBetsBasedOnRandomNumberGiven(int randomWinValue, int maxBetsPerRound, int winningBetIndex, Bet[] betsArray) throws NoBetsMadeException {
        GameRule gameRule = new GameRule(mock(MoneyAmount.class), maxBetsPerRound);

        Set<Bet> bets = new LinkedHashSet<>(Arrays.asList(betsArray));

        assertEquals("Incorrect Bet is chosen as winner.", gameRule.determineWinner(randomWinValue, bets).getWinningBet(), betsArray[winningBetIndex]);
    }

    private static Object[] generateRandomIntegerMaxBetsWinningBetIndexAndSetOfBets(){
        return new Object[]{
            new Object[]{0, 10, 0, new Bet[]{mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class)}},
            new Object[]{10, 10, 1, new Bet[]{mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class)}},
            new Object[]{70, 10, 6, new Bet[]{mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class)}},
            new Object[]{90, 5, 4, new Bet[]{mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class)}},
            new Object[]{50, 20, 10, new Bet[]{mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class), mock(Bet.class)}},
        };
    }

    /**
     * An exception test. An empty set of bets will be given and NoBetsMadeException is expected to be
     * returned.
     *
     * MoneyAmount is mocked.
     *
     * @throws NoBetsMadeException
     */
    @Test(expected = NoBetsMadeException.class)
    public void DetermineWinner_ShouldThrowNoBetsMadeExceptionWhenGivenEmptySetOfBets() throws NoBetsMadeException {
        GameRule gameRule = new GameRule(mock(MoneyAmount.class), ARBITRARY_MAX_BETS_PER_ROUND);

        Set<Bet> bets = new LinkedHashSet<>();

        gameRule.determineWinner(70, bets);
    }

    /**
     * A parameterized test where different values of integers are given. The integer is passed as
     * input to the constructor. GexMaxBeetsPerRound is expected to return the same integer value.
     *
     * MoneyAmount is mocked.
     *
     * @param maxBetsPerRound
     */
    @Test
    @Parameters(method = "generateMaxBetsPerRound")
    public void GetMaxBetsPerRound_ShouldReturnNumberOfBetsEqualToNumberOfBetsAssigned(int maxBetsPerRound){
        GameRule gameRule = new GameRule(mock(MoneyAmount.class), maxBetsPerRound);

        assertEquals("Value of maxBetsPerRound returned with getMaxBetsPerRound() does not equal to value assigned using constructor.", gameRule.getMaxBetsPerRound(), maxBetsPerRound);
    }

    private static Object[] generateMaxBetsPerRound(){
        return new Object[]{
                new Object[]{3},
                new Object[]{5},
                new Object[]{10},
                new Object[]{20},
        };
    }
}