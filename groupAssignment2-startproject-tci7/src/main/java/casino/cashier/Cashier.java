package casino.cashier;


import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.CardID;
import gamblingauthoritiy.IBetLoggingAuthority;

import java.util.ArrayList;
import java.util.List;

public class Cashier implements ICashier {
    private IBetLoggingAuthority iBetLoggingAuthority;
    public List<IGamblerCard> cardList = new ArrayList<>();
    public List<MoneyAmount> moneyAmountList = new ArrayList<>();

    public Cashier(IBetLoggingAuthority loggingAuthority) {
        this.iBetLoggingAuthority = loggingAuthority;
    }

    /**
     * @should Cards are distributed by a Cashier to a gambler
     * bankteller keeps track of which cards are handed out.
     * Note: also use the appropiate required methods from the gambling authority API
     *
     */
    @Override
    public IGamblerCard distributeGamblerCard() {
        GamblerCard gamblerCard = new GamblerCard();
        cardList.add(gamblerCard);
        iBetLoggingAuthority.logHandOutGamblingCard(gamblerCard.getCardID());
        return gamblerCard;
    }
    /**
     * @should When handing in the card at a Bank teller, all betID’s on it are logged.
     * The total amount of money credit is physically handed to the gambler,
     * and the amount stored on the card is changed to zero.
     * The stored betID’s on the card are also removed.
     * Note: also use the appropiate required methods from the gambling authority API
     * @param card
     */
    @Override
    public void returnGamblerCard(IGamblerCard card) {
        int index = getIndexByCardId(card.getCardID());
        MoneyAmount zeromoneyamount = new MoneyAmount(0);
        moneyAmountList.set(index,zeromoneyamount);
        iBetLoggingAuthority.logHandInGamblingCard(card.getCardID(),card.returnBetIDs());
        card.returnBetIDsAndClearCard();
    }

    /**
     * @should check if Bet made with the playercard is possible. this is based on the amount related
     * to the card, and the amount made in the bet.
     *
     * if the bet is valid, the amount of the bet is subtracted from the amount belonging to
     * the card.
     *
     * @param card
     * @param betToCheck
     * @return
     * @throws BetNotExceptedException if bet amount is invalid
     */
    @Override
    public boolean checkIfBetIsValid(IGamblerCard card, Bet betToCheck) throws BetNotExceptedException {
        int index = getIndexByCardId(card.getCardID());
        long moneyAmount1 = moneyAmountList.get(index).getAmountInCents();
        Long moneyAmount2 = betToCheck.getMoneyAmount().getAmountInCents();
        if (moneyAmount1 < moneyAmount2){
            throw new BetNotExceptedException("You tried to pay more than you can afford!");
        }
        return true;
    }

    /**
     * @should add amount to the players card.
     *
     * @param card card to add amount to
     * @param amount amount to add to card
     * @throws InvalidAmountException when MoneyAmount contains a negative value or is null
     */
    @Override
    public void addAmount(IGamblerCard card, MoneyAmount amount) throws InvalidAmountException {
        if(amount == null || amount.getAmountInCents() < 0){
            throw new InvalidAmountException("You tried to pay with an invalid amount of money");
        }
        int index = getIndexByCardId(card.getCardID());
        System.out.println(index);
        long oldamount = moneyAmountList.get(index).getAmountInCents();
        long newamount = amount.getAmountInCents();
        long results = oldamount + newamount;
        MoneyAmount moneyresult = new MoneyAmount(results);
        moneyAmountList.set(index,moneyresult);

    }
    /**
     * @should return card amount
     *
     * @param iGamblerCard card to search for it's amount
     */
    @Override
    public long returnCardAmount(IGamblerCard iGamblerCard) {
        int index = getIndexByCardId(iGamblerCard.getCardID());
        long cardAmount = moneyAmountList.get(index).getAmountInCents();
        return cardAmount;
    }

    /**
     * @should add the card , and the money amount to the cashier records
     *
     * @param iGamblerCard to record the card
     * @param moneyAmount to record it's amount
     */
    @Override
    public void RecordCardManually(IGamblerCard iGamblerCard, MoneyAmount moneyAmount){
        cardList.add(iGamblerCard);
        moneyAmountList.add(moneyAmount);
    }

    /**
     * @should should get index array by cardID
     *
     * @param cardID to search for it's index
     */
    @Override
    public int getIndexByCardId(CardID cardID) {
        int betcount = 0;
        int count = 0;
        for (IGamblerCard card:cardList) {
            if (card.getCardID().equals(cardID)) {
                betcount = count;
                return betcount;
            }
            count++;
        }
        return count;
    }
}
