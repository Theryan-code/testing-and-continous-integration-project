package casino.cashier;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.CardID;
import gamblingauthoritiy.IBetLoggingAuthority;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class CashierTest {
    // Naming sense [UnitOfWork_StateUnderTest_ExpectedBehavior]
    @Rule
    public Timeout globalTimeout = new Timeout(10000, TimeUnit.MILLISECONDS);
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public static Object[] generateMoneyAmount(){
        return new Object[]{
                new Object[]{ new MoneyAmount(10), new MoneyAmount(15),new MoneyAmount(20)},
                new Object[]{ new MoneyAmount(2), new MoneyAmount(8),new MoneyAmount(12)},
                new Object[]{ new MoneyAmount(5), new MoneyAmount(6), new MoneyAmount(7)},
        };
    }
    public static Object[] invalidMoneyAmount(){
        return new Object[]{
                new Object[]{ new MoneyAmount(-6)},
                new Object[]{ new MoneyAmount(-3)},
                new Object[]{ new MoneyAmount(-10)},
                null
        };
    }

    /**
     * @verifies Cards are distributed by a Cashier to a gambler bankteller keeps track of which cards are handed out.
     * Note: also use the appropiate required methods from the gambling authority API
     * @see Cashier#distributeGamblerCard()
     */
    @Test
    public void distributeGamblerCard_CardAreDistributedByACashierToAGambler_CardAreLogged() {
        //ARRANGE
        IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class); //dummy object
        Cashier cashier = new Cashier(iBetLoggingAuthority); //SUT
        //ACT
        IGamblerCard gamblerCard = cashier.distributeGamblerCard(); //direct input
        //ASSERT
        verify(iBetLoggingAuthority).logHandOutGamblingCard(gamblerCard.getCardID()); //indirect output verification
    }

    /**
     * @verifies When handing in the card at a Bank teller, all betID’s on it are logged.
     * The total amount of money credit is physically handed to the gambler, and the money amount stored on the card is changed to zero.
     * The stored betID’s on the card are also removed. Note: also use the appropiate required methods from the gambling authority API
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    @Parameters(method = "generateMoneyAmount")
    public void returnGamblerCard_WhenHandingInTheCardAtABankTeller_AllBetIDsOnAndTheTotalAmountOfMoneyCreditAreLoggedAndAreChangedToZero(MoneyAmount firstmoney, MoneyAmount secondmoney, MoneyAmount thirdmoney){
        //ARRANGE
        IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class); //dummy object
        IGamblerCard iGamblerCard = mock(IGamblerCard.class); //dummy object

        IGamblerCard iGamblerCard1 = mock(IGamblerCard.class); //dummy object

        Cashier cashier = new Cashier(iBetLoggingAuthority); //SUT
        CardID cardID = mock(CardID.class); //dummy object
        CardID cardID1 = mock(CardID.class); //dummy object
        //ACT
        when(iGamblerCard.getCardID()).thenReturn(cardID); //indirect i/o mock object
        when(iGamblerCard1.getCardID()).thenReturn(cardID1); //indirect i/o mock object
        cashier.RecordCardManually(iGamblerCard1,firstmoney); //direct input with param and mock
        cashier.RecordCardManually(iGamblerCard,secondmoney); //direct input with param and mock
        cashier.returnGamblerCard(iGamblerCard); //direct input
        //ASSERT
        verify(iBetLoggingAuthority).logHandInGamblingCard(iGamblerCard.getCardID(), iGamblerCard.returnBetIDs()); //indirect output with verification
        verify(iGamblerCard).returnBetIDsAndClearCard(); //indirect output with verification
        assertEquals("The total amount of money credit is 0", cashier.returnCardAmount(iGamblerCard), 0); //direct output verification
    }
    /**
     * @verifies add amount to the players card.
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    @Parameters(method = "generateMoneyAmount")
    public void addAmount_shouldAddAmountToThePlayersCard(MoneyAmount firstmoney, MoneyAmount secondmoney, MoneyAmount thirdmoney) throws InvalidAmountException {
        //ARRANGE
        IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class); //dummy object
        IGamblerCard iGamblerCard = mock(IGamblerCard.class); //dummy object
        MoneyAmount moneyAmount = mock(MoneyAmount.class); //dummy object
        IGamblerCard iGamblerCard1 = mock(IGamblerCard.class); //dummy object

        Cashier cashier = new Cashier(iBetLoggingAuthority); //SUT
        CardID cardID = mock(CardID.class); // dummy object
        //ACT
        cashier.RecordCardManually(iGamblerCard, firstmoney); //direct input
        cashier.RecordCardManually(iGamblerCard1,secondmoney); //direct input
        when(iGamblerCard.getCardID()).thenReturn(cardID); //indirect i/o
        cashier.addAmount(iGamblerCard, moneyAmount); //direct input
        long cardAmountTest = cashier.returnCardAmount(iGamblerCard); //direct output
        long moneyAmountTest = firstmoney.getAmountInCents(); //direct output
        //ASSERT
        assertEquals("Amount is added to the card", cardAmountTest , moneyAmountTest); //direct output verification
    }
    /**
     * An exception test.
     * @verifies add invalid amount to the players card.
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test(expected = InvalidAmountException.class)
    @Parameters(method = "invalidMoneyAmount")
    public void addInvalidAmount_shouldThrowInvalidAmountException(MoneyAmount mockmoney) throws InvalidAmountException {
        //ARRANGE
        IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class); //dummy object
        IGamblerCard iGamblerCard = mock(IGamblerCard.class); //dummy object
        MoneyAmount moneyAmount = mock(MoneyAmount.class); //dummy object
        IGamblerCard iGamblerCard1 = mock(IGamblerCard.class); //dummy object
        MoneyAmount moneyAmount1 = mock(MoneyAmount.class); //dummy object
        Cashier cashier = new Cashier(iBetLoggingAuthority); //SUT
        CardID cardID = mock(CardID.class); // dummy object
        //ACT
        cashier.RecordCardManually(iGamblerCard, moneyAmount); //direct input
        cashier.RecordCardManually(iGamblerCard1,moneyAmount1); //direct input
        when(iGamblerCard.getCardID()).thenReturn(cardID); //indirect i/o
        //ASSERT
        cashier.addAmount(iGamblerCard, mockmoney); //direct input with parameter
    }
    /**
     * A parameterized test where different values of moneyamount are given
     * @verifies check if Bet made with the playercard is possible.
     * this is based on the money amount related to the card, and the money amount made in the bet.
     * if the bet is valid, the money amount of the bet is subtracted from the money amount belonging to the card.
     * the bet is invalid if the money amount of the gambler card is smaller than the one in the bet
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    @Parameters(method = "generateMoneyAmount")
    public void checkIfBetIsValid_shouldCheckIfBetMadeWithThePlayercardIsValid_TheMoneyAmountOfTheBetIsSubtractedFromTheMoneyAmountBelongingToTheCard(MoneyAmount firstmoney, MoneyAmount secondmoney, MoneyAmount thirdmoney) throws BetNotExceptedException {
        //ARRANGE
        IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class); //dummy object
        IGamblerCard iGamblerCard = mock(IGamblerCard.class); //dummy object
        Bet bet = mock(Bet.class); //dummy object
        IGamblerCard iGamblerCard1 = mock(IGamblerCard.class); //dummy object
        CardID cardID = mock(CardID.class); // dummy object
        Cashier cashier = new Cashier(iBetLoggingAuthority); //SUT
        //ACT
        cashier.RecordCardManually(iGamblerCard, thirdmoney); //direct input with paramter
        cashier.RecordCardManually(iGamblerCard1,secondmoney); //direct input with parameter
        when(iGamblerCard.getCardID()).thenReturn(cardID); //indirect i/o
        when(bet.getMoneyAmount()).thenReturn(firstmoney); //indirect i/o with parameter
        boolean check = cashier.checkIfBetIsValid(iGamblerCard, bet); //direct output
        //ASSERT
        assertThat(check, is(true)); //direct output verification
    }

    /**
     * An exception test where different values of moneyamount are given
     * @verifies check if Bet made with the playercard is possible.
     * this is based on the money amount related to the card, and the money amount made in the bet.
     * if the bet is valid, the money amount of the bet is subtracted from the money amount belonging to the card.
     * the bet is invalid if the money amount of the gambler card is smaller than the one in the bet
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test(expected = BetNotExceptedException.class)
    @Parameters(method = "generateMoneyAmount")
    public void checkIfBetIsValid_shouldThrowBetNotExceptedException(MoneyAmount firstmoney, MoneyAmount secondmoney, MoneyAmount thirdmoney) throws BetNotExceptedException {
        //ARRANGE
        IBetLoggingAuthority iBetLoggingAuthority = mock(IBetLoggingAuthority.class); //dummy object
        IGamblerCard iGamblerCard = mock(IGamblerCard.class); //dummy object
        Bet bet = mock(Bet.class); //dummy object
        IGamblerCard iGamblerCard1 = mock(IGamblerCard.class); //dummy object
        CardID cardID = mock(CardID.class); // dummy object
        Cashier cashier = new Cashier(iBetLoggingAuthority); //SUT
        //ACT
        cashier.RecordCardManually(iGamblerCard, firstmoney); //direct input with parameter
        cashier.RecordCardManually(iGamblerCard1,secondmoney); //direct input with parameter
        when(iGamblerCard.getCardID()).thenReturn(cardID); //indirect i/o
        when(bet.getMoneyAmount()).thenReturn(thirdmoney); //indirect i/o with parameter
        //ASSERT
        cashier.checkIfBetIsValid(iGamblerCard, bet); //direct output
    }

}