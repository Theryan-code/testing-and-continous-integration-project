package casino.idfactory;

import java.time.Instant;
import java.util.UUID;

public class BetID extends GeneralID implements Comparable<BetID>{
    public BetID() {
        super();
    }

    public BetID(UUID uniqueID, Instant timeStamp) {
        super(uniqueID, timeStamp);
    }

    @Override
    public int compareTo(BetID o) {
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

        BetID betID = (BetID) obj;
        return (this.getUniqueID().equals(betID.getUniqueID()) && this.getTimeStamp().equals(betID.getTimeStamp()));
    }
}
