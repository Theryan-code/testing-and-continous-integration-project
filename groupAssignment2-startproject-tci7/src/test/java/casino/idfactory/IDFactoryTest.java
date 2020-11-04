package casino.idfactory;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class IDFactoryTest {
    @Rule
    public Timeout globalTimeout = new Timeout(1000, TimeUnit.MILLISECONDS);

    /**
     * String for all types are given. Correct corresponding ID type is expected to be returned.
     */
    @Test
    public void GenerateID_ShouldReturnCorrectIDTypeAccordingOnString(){
        IDFactory idFactory = new IDFactory();
        GeneralID idGeneratedA = idFactory.generateID("BETID");
        GeneralID idGeneratedB = idFactory.generateID("BETTINGROUNDID");
        GeneralID idGeneratedC = idFactory.generateID("CARDID");
        GeneralID idGeneratedD = idFactory.generateID("GAMINGMACHINEID");

        assertEquals("generateID does not return BetID object when given 'BETID' string as parameter.", idGeneratedA.getClass(), BetID.class);
        assertEquals("generateID does not return BettingRoundID object when given 'BETTINGROUNDID' string as parameter.",idGeneratedB.getClass(), BettingRoundID.class);
        assertEquals("generateID does not return CardID object when given 'CARDID' string as parameter.",idGeneratedC.getClass(), CardID.class);
        assertEquals("generateID does not return GamingMachineID object when given 'GAMINGMACHINEID' string as parameter.",idGeneratedD.getClass(), GamingMachineID.class);
    }

    /**
     * A parameterized test. Different invalid type strings are given, and null is expected to be returned
     *
     * @param idType
     */
    @Test
    @Parameters(method = "generateIncorrectIDTypeStrings")
    public void GenerateID_ShouldReturnNullGivenIncorrectIDTypeString(String idType){
        IDFactory idFactory = new IDFactory();
        GeneralID idGenerated = idFactory.generateID(idType);
        assertNull(idGenerated);
    }

    private static Object[] generateIncorrectIDTypeStrings(){
        return new Object[]{
                new Object[]{"betid"},
                new Object[]{"bettingroundid"},
                new Object[]{"cardid"},
                new Object[]{"gamingmachineid"},
                new Object[]{"generalid"},
                new Object[]{"id"},
                new Object[]{"123"},
                new Object[]{""},
        };
    }
}