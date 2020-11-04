package casino.gamingmachine;


import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.*;
import casino.game.IGame;
import casino.game.NoCurrentRoundException;
import casino.idfactory.BetID;
import casino.idfactory.GamingMachineID;

public class GamingMachine implements IGamingMachine {
    private GamingMachineID gamingMachineID;
    private IGamblerCard gamblerCard;
    private Bet openBet;

    public GamingMachine(GamingMachineID gamingMachineID) {
        this.gamingMachineID = gamingMachineID;
    }

    @Override
    public boolean placeBet(long amountInCents, IGame game, ICashier cashier) throws NoPlayerCardException {
        if(gamblerCard == null) throw new NoPlayerCardException();
        else {
            Bet newBet = new Bet(gamblerCard.generateNewBetID(), new MoneyAmount(amountInCents));
            try {
                if (cashier.checkIfBetIsValid(gamblerCard, newBet)) {
                    if (game.acceptBet(newBet, this)) {
                        openBet = newBet;
                        return true;
                    } else return false;
                } else return false;
            } catch (BetNotExceptedException | NoCurrentRoundException e) {
                return false;
            }
        }
    }

    @Override
    public void acceptWinner(BetResult winResult, ICashier cashier) throws InvalidAmountException {
        if(winResult != null) {
            if (winResult.getWinningBet() == openBet) {
                cashier.addAmount(gamblerCard, winResult.getAmountWon());
            }
        }
        openBet = null;
    }

    @Override
    public GamingMachineID getGamingMachineID() {
        return gamingMachineID;
    }

    @Override
    public void connectCard(IGamblerCard card) {
        this.gamblerCard = card;
    }

    @Override
    public void disconnectCard() throws CurrentBetMadeException {
        if(openBet != null) throw new CurrentBetMadeException();
        else gamblerCard = null;
    }

    @Override
    public IGamblerCard getGamblerCardConnected() {
        return gamblerCard;
    }

    @Override
    public Bet getOpenBet() {
        return openBet;
    }
}
