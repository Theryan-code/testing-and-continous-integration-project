package casino.game;

import casino.idfactory.BettingRoundID;
import gamblingauthoritiy.BetToken;
import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

public class BettingRoundAssert extends GenericAssert<BettingRoundAssert, BettingRound>{

    public BettingRoundAssert(BettingRound actual) {
        super(BettingRoundAssert.class, actual);
    }

    public static BettingRoundAssert assertRyan(BettingRound actual) {
        return new BettingRoundAssert(actual);
    }

    public BettingRoundAssert hasCorrectID(BettingRoundID name) {
        isNotNull();
        String errorMessage = String.format("Expected correct id",
                name, actual.getBettingRoundID());
        Assertions.assertThat(actual.getBettingRoundID())
                .overridingErrorMessage(errorMessage)
                .isEqualTo(name);
        return this;
    }

    public BettingRoundAssert hasCorrectBetToken(BetToken name) {
        isNotNull();
        String errorMessage = String.format("Expected correct BetToken",
                name, actual.getBetToken());
        Assertions.assertThat(actual.getBetToken())
                .overridingErrorMessage(errorMessage)
                .isEqualTo(name);
        return this;
    }

    public BettingRoundAssert hasCorrectNumberOfBets(int name) {
        isNotNull();
        String errorMessage = String.format("Expected correct number of bets",
                name, actual.numberOFBetsMade());
        Assertions.assertThat(actual.numberOFBetsMade())
                .overridingErrorMessage(errorMessage)
                .isEqualTo(name);
        return this;
    }
}
