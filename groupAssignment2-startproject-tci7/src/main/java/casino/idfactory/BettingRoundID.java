package casino.idfactory;

import java.time.Instant;
import java.util.UUID;

public class BettingRoundID extends GeneralID implements Comparable<BettingRoundID>{
    public BettingRoundID(){super();}

    public BettingRoundID(UUID uniqueId, Instant timestamp){super(uniqueId, timestamp);}

    @Override
    public int compareTo(BettingRoundID o) {
        return this.getTimeStamp().compareTo(o.getTimeStamp());
    }

    @Override
    public int hashCode() {
        return this.getUniqueID().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        else if(obj == null || this.getClass() != obj.getClass()) return false;

        BettingRoundID bettingRoundID = (BettingRoundID) obj;
        return (this.getUniqueID().equals(bettingRoundID.getUniqueID()) && this.getTimeStamp().equals(bettingRoundID.getTimeStamp()));
    }
}
