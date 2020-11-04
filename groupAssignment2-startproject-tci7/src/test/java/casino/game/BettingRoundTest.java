package casino.game;

import casino.bet.Bet;
import casino.idfactory.BettingRoundID;
import gamblingauthoritiy.BetToken;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static casino.game.BettingRoundAssert.assertRyan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class BettingRoundTest {
    @Rule
    public Timeout globalTimeout = new Timeout(10000, TimeUnit.MILLISECONDS);
    private static Bet bet10 = mock(Bet.class); //dummy object
    private static Bet bet20 = mock(Bet.class); //dummy object
    private static Bet bet30 = mock(Bet.class); //dummy object
    private static Object[] generateBets(){
        return new Object[]{
                new Object[]{
                        new HashSet<>(Arrays.asList(bet10, bet20, bet30))
                }
        };
    }
    // Naming sense [UnitOfWork_StateUnderTest_ExpectedBehavior]
    /**
     * @verifies get current betting round id
     * @see BettingRound#getBettingRoundID()
     */
    @Test
    public void getBettingRoundID_shouldGetCurrentBettingRoundId() {
        //Arrange
        BettingRoundID bettingRoundID = mock(BettingRoundID.class); //dummy object
        BetToken betToken = mock(BetToken.class); //dummy object
        BettingRound bettingRound = new BettingRound(bettingRoundID, betToken); //SUT
        //Act
        BettingRoundID bettingRoundIDTest = bettingRound.getBettingRoundID(); //direct output
        //Assert
        assertEquals("betting round id is successfully added", bettingRoundID , bettingRoundIDTest); //direct output verification
        assertRyan(bettingRound).hasCorrectID(bettingRoundID);
    }

    /**
     * @verifies add a bet to the current bettinground.
     * @see BettingRound#placeBet(casino.bet.Bet)
     */
    @Test
    public void placeBet_shouldAddABetToTheCurrentBettinground() throws IllegalArgumentException{
        //Arrange
        BettingRoundID bettingRoundID = mock(BettingRoundID.class); //dummy object
        BetToken betToken = mock(BetToken.class); //dummy object
        Bet bet = mock(Bet.class); //dummy object
        BettingRound bettingRound = new BettingRound(bettingRoundID, betToken); //SUT
        //Act
        boolean check = bettingRound.placeBet(bet); //direct output
        //Assert
        assertThat(check, is(true)); //direct output verification
    }

    /**
     * An exception test
     * @verifies add a bet to the current bettinground.
     * @see BettingRound#placeBet(casino.bet.Bet)
     */
    @Test(expected = IllegalArgumentException.class)
    public void placeInvalidBet_shouldThrowIllegalArgumentException() throws IllegalArgumentException{
        //Arrange
        BettingRoundID bettingRoundID = mock(BettingRoundID.class); //dummy object
        BetToken betToken = mock(BetToken.class); //dummy object
        BettingRound bettingRound = new BettingRound(bettingRoundID, betToken); //SUT
        //Act
        boolean check = bettingRound.placeBet(null); //direct output
        //Assert
        assertThat(check, is(true)); //direct output verification
    }

    /**
     * A parameterized test the correct values are returned.
     * @verifies return set of all bets made in this betting round.
     * @see BettingRound#getAllBetsMade()
     */
    @Test
    @Parameters(method = "generateBets")
    public void getAllBetsMade_shouldReturnSetOfAllBetsMadeInThisBettingRound(HashSet<Bet> betsArray) {
        //Arrange
        BettingRoundID bettingRoundID = mock(BettingRoundID.class); //dummy object
        BetToken betToken = mock(BetToken.class); //dummy object
        BettingRound bettingRound = new BettingRound(bettingRoundID, betToken); //SUT
        //Act
        bettingRound.placeBet(bet10); //direct inputs
        bettingRound.placeBet(bet20); //direct inputs
        bettingRound.placeBet(bet30); //direct inputs
        //Assert
        assertEquals("betting round id is successfully added", bettingRound.getAllBetsMade(), betsArray); //direct output verification
    }

    /**
     * @verifies return betToken from this betting round.
     * @see BettingRound#getBetToken()
     */
    @Test
    public void getBetToken_shouldReturnBetTokenFromThisBettingRound() {
        //Arrange
        BettingRoundID bettingRoundID = mock(BettingRoundID.class); //dummy object
        BetToken betToken = mock(BetToken.class); //dummy object
        BettingRound bettingRound = new BettingRound(bettingRoundID, betToken); //SUT
        //Act
        BetToken betTokenTest = bettingRound.getBetToken(); //direct output
        //Assert
        assertEquals("betting token is successfully get", betTokenTest, betToken); //direct output verification
        assertRyan(bettingRound).hasCorrectBetToken(betToken);
    }

    /**
     * @verifies return number of bets made in the betting round
     * @see BettingRound#numberOFBetsMade()
     */
    @Test
    public void numberOFBetsMade_shouldReturnNumberOfBetsMadeInTheBettingRound(){
        //Arrange
        BettingRoundID bettingRoundID = mock(BettingRoundID.class); //dummy object
        BetToken betToken = mock(BetToken.class); //dummy object
        Bet bet = mock(Bet.class); //dummy object
        Bet bet2 = mock(Bet.class); //dummy object
        BettingRound bettingRound = new BettingRound(bettingRoundID, betToken); //SUT
        //Act
        bettingRound.placeBet(bet); //direct input
        bettingRound.placeBet(bet2); //direct input
        int NumberOfBetsMade = bettingRound.numberOFBetsMade(); //direct output
        //Assert
        assertEquals("number of bets is correct", 2, NumberOfBetsMade); //direct output verification
        assertRyan(bettingRound).hasCorrectNumberOfBets(NumberOfBetsMade);
    }
}