package casino.idfactory;

import casino.bet.Bet;

/**
 * Factory for creation of GeneralID objects.
 * creation of the right object is done by specifying the type to create as a string
 * the specified type is case insensitive
 *
 * possible types:
 * betID
 * bettingroundID
 * cardID
 * gamingMachineID
 *
 * when the type is not present, null is returned.
 */
public class IDFactory {
  //

    /**
     * generate the right generalID instance by specifying the type as a string
     * @param idType is name of the type in capitals.
     * @return an instance of the correct GeneralID object type, or null otherwise.
     */
    public static GeneralID generateID(String idType){
        if(idType == "BETID"){
            return new BetID();
        }
        else if(idType == "BETTINGROUNDID"){
            return new BettingRoundID();
        }
        else if(idType == "CARDID"){
            return new CardID();
        }
        else if(idType == "GAMINGMACHINEID"){
            return new GamingMachineID();
        }
        else return null;
    };
}
