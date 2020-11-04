package casino.bet;

import java.util.Currency;

/**
 * immutable object representing an amount of money.
 * For demo purposes: hardocded USD
 */
public class MoneyAmount {
    private long amountInCents;
    private Currency currency;

    public MoneyAmount(long amountInCents) {
        this.amountInCents = amountInCents;
        this.currency = Currency.getInstance("USD");
    }

    public long getAmountInCents() {
        return amountInCents;
    }
}
