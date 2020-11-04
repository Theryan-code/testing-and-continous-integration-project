package casino.game;


import casino.bet.Bet;
import casino.gamingmachine.IGamingMachine;
import gamblingauthoritiy.BetLoggingAuthority;
import gamblingauthoritiy.BetTokenAuthority;

abstract class AbstractGame implements IGame{
    private BetTokenAuthority betTokenAuthority;
    private BetLoggingAuthority betLoggingAuthority;

 // define only the constructor here
    public AbstractGame(BetTokenAuthority betTokenAuthority, BetLoggingAuthority betLoggingAuthority){
        this.betTokenAuthority = betTokenAuthority;
        this.betLoggingAuthority = betLoggingAuthority;
    }
}
