package casino.idfactory;

import java.time.Instant;
import java.util.UUID;

public class GamingMachineID extends GeneralID implements Comparable<GamingMachineID>{
    public GamingMachineID() {
        super();
    }

    public GamingMachineID(UUID uniqueID, Instant timeStamp) {
        super(uniqueID, timeStamp);
    }

    @Override
    public int compareTo(GamingMachineID o) {
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

        GamingMachineID gamingMachineID = (GamingMachineID) obj;
        return (this.getUniqueID().equals(gamingMachineID.getUniqueID()) && this.getTimeStamp().equals(gamingMachineID.getTimeStamp()));
    }
}
