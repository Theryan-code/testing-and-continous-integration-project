package casino.idfactory;

import java.time.Instant;
import java.util.UUID;

public class CardID extends GeneralID implements Comparable<CardID>{
    public CardID() {
        super();
    }

    public CardID(UUID uniqueID, Instant timeStamp) {
        super(uniqueID, timeStamp);
    }

    @Override
    public int compareTo(CardID o) {
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

        CardID cardID = (CardID) obj;
        return (this.getUniqueID().equals(cardID.getUniqueID()) && this.getTimeStamp().equals(cardID.getTimeStamp()));
    }
}
