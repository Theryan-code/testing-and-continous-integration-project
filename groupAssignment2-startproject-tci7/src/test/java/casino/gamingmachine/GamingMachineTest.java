package casino.gamingmachine;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.*;
import casino.game.IGame;
import casino.game.NoCurrentRoundException;
import casino.idfactory.GamingMachineID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GamingMachineTest {
    @Rule
    public Timeout globalTimeout = new Timeout(5000, TimeUnit.MILLISECONDS);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final int ARBITRARY_AMOUNT_IN_CENTS = 100000;

    /**
     * The same GamingMachineID passed as input to the constructor is expected to be returned by
     * GetGamingMachineID.
     *
     * GamingMachineID is mocked.
     *
     */
    @Test
    public void GetGamingMachineID_ShouldReturnGamingMachineIDEqualToGamingMachineIDAssigned(){
        GamingMachineID gamingMachineIDA = mock(GamingMachineID.class);
        GamingMachineID gamingMachineIDB = mock(GamingMachineID.class);
        GamingMachine gamingMachine = new GamingMachine(gamingMachineIDA);

        assertThat(gamingMachine.getGamingMachineID(), is(gamingMachineIDA));
        assertThat(gamingMachine.getGamingMachineID(), not(gamingMachineIDB));
    }

    /**
     * A GamblerCard is connected. It is expected that the GamblerCard connected is equal to the
     * GamblerCard stored as attribute.
     *
     * GamblerCard is mocked.
     *
     */
    @Test
    public void ConnectCard_ShouldSetGamblerCardAttributeToEqualToTheGamblerConnected(){
        GamingMachine gamingMachine = new GamingMachine(mock(GamingMachineID.class));
        IGamblerCard gamblerCardA = mock(GamblerCard.class);
        IGamblerCard gamblerCardB = mock(GamblerCard.class);

        gamingMachine.connectCard(gamblerCardA);

        assertThat(gamingMachine.getGamblerCardConnected(), is(gamblerCardA));
        assertThat(gamingMachine.getGamblerCardConnected(), not(gamblerCardB));
    }

    /**
     * Valid and invalid Bets are given by setting checkIfBetIsValid() of Cashier. It is
     * expected that acceptBet() is called if Bet is valid, and not called if Bet is invalid.
     *
     * Game, Cashier and GamblerCard is mocked. Verifies if  acceptBet() method of Game and
     * checkIfBetIsValid() method of Cashier is called.
     *
     * @throws NoPlayerCardException
     * @throws BetNotExceptedException
     * @throws NoCurrentRoundException
     */
    @Test
    public void PlaceBet_ShouldCallAcceptBetBetIfBetIsValid() throws NoPlayerCardException, BetNotExceptedException, NoCurrentRoundException {
        GamingMachine gamingMachine = new GamingMachine(mock(GamingMachineID.class));
        IGamblerCard gamblerCard = mock(IGamblerCard.class);

        ICashier cashierA = mock(ICashier.class);
        ICashier cashierB = mock(ICashier.class);
        IGame gameA = mock(IGame.class);
        IGame gameB = mock(IGame.class);

        when(cashierA.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(cashierB.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(false);
        when(cashierB.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenThrow(BetNotExceptedException.class);

        gamingMachine.connectCard(gamblerCard);
        gamingMachine.placeBet(ARBITRARY_AMOUNT_IN_CENTS, gameA, cashierA);
        gamingMachine.placeBet(ARBITRARY_AMOUNT_IN_CENTS, gameB, cashierB);

        verify(cashierA).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(gameA).acceptBet(any(Bet.class), any(IGamingMachine.class));

        verify(cashierB).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(gameB, never()).acceptBet(any(Bet.class), any(IGamingMachine.class));
    }

    /**
     * Active and inactive BettingRound is given by setting acceptBet() method of Game.
     * If an active BettingRound exists, Bet should be stored as attribute in GamingMachine and
     * true is returned. Otherwise attribute should be null and false is returned.
     *
     * Game, Cashier and GamblerCard is mocked.
     *
     * @throws NoPlayerCardException
     * @throws NoCurrentRoundException
     * @throws BetNotExceptedException
     */
    @Test
    public void PlaceBet_ShouldStoreBetAndReturnTrueIfActiveBettingRoundExists() throws NoPlayerCardException, NoCurrentRoundException, BetNotExceptedException {
        ICashier cashier = mock(ICashier.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        GamingMachine gamingMachineA = new GamingMachine(mock(GamingMachineID.class));
        GamingMachine gamingMachineB = new GamingMachine(mock(GamingMachineID.class));
        IGame gameA = mock(IGame.class);
        IGame gameB = mock(IGame.class);

        when(cashier.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(gameA.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenReturn(true);
        when(gameB.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenReturn(false);
        when(gameB.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenThrow(NoCurrentRoundException.class);

        gamingMachineA.connectCard(gamblerCard);
        gamingMachineB.connectCard(gamblerCard);

        boolean returnValueA = gamingMachineA.placeBet(ARBITRARY_AMOUNT_IN_CENTS, gameA, cashier);
        boolean returnValueB = gamingMachineA.placeBet(ARBITRARY_AMOUNT_IN_CENTS, gameB, cashier);

        verify(gameA).acceptBet(any(Bet.class), any(IGamingMachine.class));
        verify(gameB).acceptBet(any(Bet.class), any(IGamingMachine.class));

        assertThat(gamingMachineA.getOpenBet(), not(nullValue()));
        assertThat(returnValueA, is(true));

        assertThat(gamingMachineB.getOpenBet(), is(nullValue()));
        assertThat(returnValueB, is(false));
    }

    /**
     * An exception test. If GamblerCard attribute is null, NoPlayerCardException is expected.
     *
     * Game and Cashier is mocked.
     *
     * @throws NoPlayerCardException
     */
    @Test
    public void PlaceBet_ShouldThrowNoPlayerCardExceptionIfNoGamblerCardIsConnectedWhenMakingBet() throws NoPlayerCardException {
        expectedException.expect(NoPlayerCardException.class);
        GamingMachine gamingMachine = new GamingMachine(mock(GamingMachineID.class));
        ICashier cashier = mock(ICashier.class);
        IGame game = mock(IGame.class);

        gamingMachine.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashier);
    }

    /**
     * After the BetResult or null is received, Bet attribute should be null.
     *
     * Game, Cashier and GamblerCard is mocked.
     *
     * @throws NoPlayerCardException
     * @throws BetNotExceptedException
     * @throws NoCurrentRoundException
     * @throws InvalidAmountException
     */
    @Test
    public void AcceptWinner_ShouldClearOpenBetsMade() throws NoPlayerCardException, BetNotExceptedException, NoCurrentRoundException, InvalidAmountException {
        GamingMachine gamingMachineA = new GamingMachine(mock(GamingMachineID.class));
        GamingMachine gamingMachineB = new GamingMachine(mock(GamingMachineID.class));

        ICashier cashier = mock(ICashier.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        IGame game = mock(IGame.class);

        when(cashier.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(game.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenReturn(true);

        gamingMachineA.connectCard(gamblerCard);
        gamingMachineB.connectCard(gamblerCard);
        gamingMachineA.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashier);
        gamingMachineB.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashier);

        gamingMachineA.acceptWinner(mock(BetResult.class), cashier);
        gamingMachineB.acceptWinner(null, cashier);

        verify(cashier, times(2)).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(game, times(2)).acceptBet(any(Bet.class), any(IGamingMachine.class));

        assertThat(gamingMachineA.getOpenBet(), is(nullValue()));
        assertThat(gamingMachineB.getOpenBet(), is(nullValue()));
    }

    /**
     * One winning result where winner Bet is on this machine, and one winning result where
     * winner Bet is not on this machine is given. If winner is on this machine, addAmount()
     * of Cashier is called.
     *
     * Game, Cashier, GamblerCard and MoneyAmount is mocked. Verifies if addAmount() is
     * called when winning Bet is on the machine and if addAmount() is not called when
     * winning Bet is not on the machine.
     *
     * @throws BetNotExceptedException
     * @throws NoCurrentRoundException
     * @throws NoPlayerCardException
     * @throws InvalidAmountException
     */
    @Test
    public void AcceptWinner_ShouldCallAddAmountIfWinnerIsInThisGamingMachine() throws BetNotExceptedException, NoCurrentRoundException, NoPlayerCardException, InvalidAmountException {
        GamingMachine gamingMachineA = new GamingMachine(mock(GamingMachineID.class));
        GamingMachine gamingMachineB = new GamingMachine(mock(GamingMachineID.class));

        ICashier cashierA = mock(ICashier.class);
        ICashier cashierB = mock(ICashier.class);

        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        IGame game = mock(IGame.class);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);

        when(cashierA.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(cashierB.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(game.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenReturn(true);

        gamingMachineA.connectCard(gamblerCard);
        gamingMachineB.connectCard(gamblerCard);

        gamingMachineA.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashierA);
        gamingMachineB.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashierB);

        BetResult winResultA = new BetResult(gamingMachineA.getOpenBet(), moneyAmount);
        BetResult winResultB = new BetResult(mock(Bet.class), moneyAmount);

        gamingMachineA.acceptWinner(winResultA, cashierA);
        gamingMachineB.acceptWinner(winResultB, cashierB);

        verify(cashierA).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(cashierB).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(game, times(2)).acceptBet(any(Bet.class), any(IGamingMachine.class));

        verify(cashierA).addAmount(gamblerCard, moneyAmount);
        verify(cashierB, never()).addAmount(gamblerCard, moneyAmount);
    }

    /**
     * An exception test. Attempt to disconnect GamblerCard is made when there is an open Bet.
     * CurrentBetMadeException is expected.
     *
     * Game, Cashier and GamblerCard is mocked.
     *
     * @throws NoPlayerCardException
     * @throws BetNotExceptedException
     * @throws NoCurrentRoundException
     * @throws CurrentBetMadeException
     */
    @Test
    public void DisconnectCard_ShouldThrowCurrentBetMadeExceptionAndNotSetGamblerCardAttributeToNullWhenOpenBetsExist() throws NoPlayerCardException, BetNotExceptedException, NoCurrentRoundException, CurrentBetMadeException{
        expectedException.expect(CurrentBetMadeException.class);
        GamingMachine gamingMachine = new GamingMachine(mock(GamingMachineID.class));
        ICashier cashier = mock(ICashier.class);
        IGame game = mock(IGame.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);

        when(cashier.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(game.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenReturn(true);

        gamingMachine.connectCard(gamblerCard);
        gamingMachine.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashier);
        gamingMachine.disconnectCard();

        verify(cashier).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(game).acceptBet(any(Bet.class), any(IGamingMachine.class));

        assertThat(gamingMachine.getGamblerCardConnected(), is(gamblerCard));
    }

    /**
     * Attempt to disconnect GamblerCard is made when there is no open Bet. It is expected that
     * the GamblerCard attribute equals to null and no exception is thrown.
     *
     * Game, Cashier and GamblerCard is mocked.
     *
     * @throws BetNotExceptedException
     * @throws NoCurrentRoundException
     * @throws NoPlayerCardException
     * @throws CurrentBetMadeException
     * @throws InvalidAmountException
     */
    @Test
    public void DisconnectCard_ShouldNotThrowAnyExceptionAndSetGamblreCardAttributeToNullWhenOpenBetsDoesNotExist() throws BetNotExceptedException, NoCurrentRoundException, NoPlayerCardException, CurrentBetMadeException, InvalidAmountException {
        GamingMachine gamingMachineA = new GamingMachine(mock(GamingMachineID.class));
        GamingMachine gamingMachineB = new GamingMachine(mock(GamingMachineID.class));
        ICashier cashier = mock(ICashier.class);
        IGame game = mock(IGame.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);

        when(cashier.checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class))).thenReturn(true);
        when(game.acceptBet(any(Bet.class), any(IGamingMachine.class))).thenReturn(true);

        gamingMachineA.connectCard(gamblerCard);
        gamingMachineB.connectCard(gamblerCard);

        gamingMachineA.placeBet(ARBITRARY_AMOUNT_IN_CENTS, game, cashier);
        gamingMachineA.acceptWinner(null, cashier);

        gamingMachineA.disconnectCard();
        gamingMachineB.disconnectCard();

        verify(cashier).checkIfBetIsValid(any(IGamblerCard.class), any(Bet.class));
        verify(game).acceptBet(any(Bet.class), any(IGamingMachine.class));

        assertThat(gamingMachineA.getGamblerCardConnected(), is(nullValue()));
        assertThat(gamingMachineB.getGamblerCardConnected(), is(nullValue()));
    }
}