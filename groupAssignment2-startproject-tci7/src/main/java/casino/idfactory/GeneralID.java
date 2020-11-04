package casino.idfactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * CREATE TESTS FOR THIS CLASS, implement necessary code when needed (after creating tests first)
 *
 */
public abstract class GeneralID {
    private final UUID uniqueID;
    private final Instant timeStamp;

    public GeneralID() {
        this.uniqueID = UUID.randomUUID();
        this.timeStamp = Instant.now();
    }

    public GeneralID(UUID uniqueID, Instant timeStamp){
        this.uniqueID = uniqueID;
        this.timeStamp = timeStamp;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }
    @Override
    public String toString() {
        return "ID: " + this.uniqueID + this.timeStamp.toString();
    }
}
