package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.IGamingMachine;
import casino.idfactory.BetID;
import casino.idfactory.BettingRoundID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetTokenAuthority;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;


public class DefaultGameTest {
    //arrange
    BetTokenAuthority mockBetTokenAuthority = mock(BetTokenAuthority.class);
    BetLoggingAuthority mockBetLoggingAuthority = mock(BetLoggingAuthority.class);
    DefaultGame testGame = new DefaultGame(mockBetTokenAuthority, mockBetLoggingAuthority);
    BettingRound currentBettingRound = mock(BettingRound.class);

    /**
     * @verifies Get BetToken From BetToken Authority
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldGetBetTokenFromBetTokenAuthority() throws Exception {
        //act
        testGame.startBettingRound();

        //assert
        verify(mockBetTokenAuthority).getBetToken(testGame.getNewBettingRoundId());
    }

    /**
     * @verifies create a betting round and add it to the list
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldCreateABettingRoundAndAddItToTheList() throws Exception {
        //act
        testGame.startBettingRound();

        //assert
        assertThat(testGame.getBettingRoundSet().size()).isEqualTo(1);
    }

    /**
     * @verifies start logging the betting round
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldStartLoggingTheBettingRound() throws Exception {
        //act
        testGame.startBettingRound();

        //assert
        verify(mockBetLoggingAuthority).logStartBettingRound(testGame.getCurrentBettingRound());
    }

    /**
     * @verifies throw NoCurrentRoundExceptionIfRoundIsFinished
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
//    @Test(expected = NoCurrentRoundException.class)
//    public void acceptBet_shouldThrowNoCurrentRoundExceptionIfRoundIsFinished() throws Exception {
//        //arrange
//        Bet mockBet = mock(Bet.class);
//        IGamingMachine mockIGamingMachine = mock(IGamingMachine.class);
//        GameRules gameRules = mock(GameRules.class);
//        GameRules gameRule = mock(GameRules.class);
//        when(gameRule.getMaxBetsPerRound()).thenReturn(2);
//        when(currentBettingRound.numberOFBetsMade()).thenReturn(2);
//
//        //act, assert
//        testGame.acceptBet(mockBet, mockIGamingMachine);
//    }

    /**
     * @verifies place the bet in betting round
     * @see DefaultGame#acceptBet(Bet, IGamingMachine)
     */
    @Test
    public void acceptBet_shouldPlaceTheBetInBettingRound() throws Exception {
//        //arrange
//        BetID betID = (BetID) IDFactory.generateID("betid");
//        Bet bet = new Bet(betID, new MoneyAmount(70));
//        IGamingMachine mockIGamingMachine = mock(IGamingMachine.class);
//
//
//        //act
//        testGame.acceptBet(bet, mockIGamingMachine);
//
//        //assert
//        verify(currentBettingRound).placeBet(bet);
    }

    /**
     * @verifies indicate if a round is finished
     * @see DefaultGame#isBettingRoundFinished(BettingRound, GameRules)
     */
    @Test
    public void isBettingRoundFinished_shouldIndicateIfARoundIsFinished() throws Exception {
        //arrange
        GameRules gameRule = mock(GameRules.class);

        //act
        when(gameRule.getMaxBetsPerRound()).thenReturn(2);
        when(currentBettingRound.numberOFBetsMade()).thenReturn(2);
        testGame.isBettingRoundFinished(currentBettingRound, gameRule);

        //assert
        assertThat(testGame.isBettingRoundFinished(currentBettingRound, gameRule)).isEqualTo(true);
    }

    /**
     * @verifies determine a winner when the method of game rule is called
     * @see DefaultGame#determineWinner(BettingRound, GameRules)
     */
    @Test
    public void determineWinner_shouldDetermineAWinnerWhenTheMethodOfGameRuleIsCalled() throws Exception {
        //arrange
        GameRules gameRules = mock(GameRules.class);

        //act
        testGame.determineWinner(currentBettingRound, gameRules);

        //assert
        verify(gameRules).determineWinner
                (mockBetTokenAuthority.getRandomInteger(mockBetTokenAuthority.getBetToken(testGame.getNewBettingRoundId())),
                        currentBettingRound.getAllBetsMade());
    }

    /**
     * @verifies send bets for logging
     * @see DefaultGame#determineWinner(BettingRound, GameRules)
     */
    @Test
    public void determineWinner_shouldSendBetsForLogging() throws Exception {
        //arrange
        GameRules gameRules = mock(GameRules.class);
        //BetResult betResult = new BetResult(new Bet(mock(BetID.class), new MoneyAmount(70)), new MoneyAmount(800));

        //act
        testGame.determineWinner(currentBettingRound, gameRules);

        //assert
        verify(mockBetLoggingAuthority).logEndBettingRound(currentBettingRound, null);
    }

    /**
     * @verifies send notifications to all gaming machines
     * @see DefaultGame#determineWinner(BettingRound, GameRules)
     */
    @Test
    public void determineWinner_shouldSendNotificationsToAllGamingMachines() throws Exception {
        //TODO auto-generated

    }

    /**
     * @verifies end the betting round
     * @see DefaultGame#determineWinner(BettingRound, GameRules)
     */
    @Test
    public void determineWinner_shouldEndTheBettingRound() throws Exception {
        //arrange
        GameRules gameRule = mock(GameRules.class);

        //act
        when(gameRule.getMaxBetsPerRound()).thenReturn(2);
        when(currentBettingRound.numberOFBetsMade()).thenReturn(2);
        testGame.determineWinner(currentBettingRound, gameRule);

        //assert
        assertThat(testGame.isBettingRoundFinished(currentBettingRound, gameRule)).isEqualTo(true);
    }

    /**
     * @verifies start a new round
     * @see DefaultGame#determineWinner(BettingRound, GameRules)
     */
    @Test
    public void determineWinner_shouldStartANewRound() throws Exception {
        //arrange


        //act

        //assert
    }
}
