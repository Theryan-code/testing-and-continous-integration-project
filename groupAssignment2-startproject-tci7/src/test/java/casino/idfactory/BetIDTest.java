package casino.idfactory;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class BetIDTest {
    /**
     * method returns objects which all are LOGICALLY equal.
     * return 3 LOGICALLY equal objects
     * @return
     */
    private Object[] equalObjectsProvider() {
        UUID uuid = UUID.randomUUID();
        Instant instant = Instant.now();

        return new Object[]{
                new Object[]{new BetID(uuid, instant), new BetID(uuid, instant), new BetID(uuid, instant)}
        };
    }

    /**
     * method returns objects which are LOGICALLY UNequal.
     * return 3 LOGICALLY unequal objects
     * @return
     */
    private Object[] unequalObjectsProvider() {
        return new Object[]{
                new Object[]{new BetID(), new BetID(), new BetID()},
                new Object[]{new BetID(), new Object(), new BetID()}
        };
    }

    /**
     * method returns Comparable objects
     * return 3 LOGICALLY equal Comparable objects
     *
     * @return
     */
    private Object[] equalComparablesProvider() {
        UUID uuid = UUID.randomUUID();
        Instant instant = Instant.now();

        return new Object[]{
                new Object[]{new BetID(uuid, instant), new BetID(uuid, instant), new BetID(uuid, instant)}
        };
    }


    /**
     * method returns Comparable objects which are
     * sorted according to the natural ordening.
     *
     * @return
     */
    private Object[] sortedComparablesProvider() throws InterruptedException {
        BetID betID1 = new BetID();
        TimeUnit.SECONDS.sleep(1);
        BetID betID2 = new BetID();
        TimeUnit.SECONDS.sleep(1);
        BetID betID3 = new BetID();
        TimeUnit.SECONDS.sleep(1);

        return new Object[]{
                new Object[]{betID1, betID2, betID3}
        };
    }

    /**
     * method returns Comparable objects which all are LOGICALLY UNequal.
     * AND NOT sorted according to the natural ordening.
     *
     * @return
     */
    private Object[] unsortedComparablesProvider() throws InterruptedException {
        BetID betID1 = new BetID();
        TimeUnit.SECONDS.sleep(1);
        BetID betID2 = new BetID();
        TimeUnit.SECONDS.sleep(1);
        BetID betID3 = new BetID();
        TimeUnit.SECONDS.sleep(1);

        return new Object[]{
                new Object[]{betID2, betID1, betID3}
        };
    }

    /**
     * TEST FOR EQUALITY AND UNEQUALITY USING EQUALS() AND HASHCODE() METHOD
     */
    @Test
    @Parameters(method = "equalObjectsProvider")
    public void testIfObjectsAreEqualAccordingToEqualsAndHashcodeMethod(Object o1, Object o2, Object o3){
        // default contract tests
        EqualsUtils.objectsConformToEqualsContract(o1, o2, o3);
        HashCodeUtils.objectsConformToHashcodeContract(o1, o2, o3);
        // test for expected equality
        Assert.assertTrue("All objects should be equal according to equals()",o1.equals(o2) && o1.equals(o3) && o2.equals(o3));
        Assert.assertTrue("All objects should have same hashcode",o1.hashCode() == o2.hashCode() && o1.hashCode()== o3.hashCode() && o2.hashCode() == o3.hashCode());
    }

    @Test
    @Parameters(method = "unequalObjectsProvider")
    public void testIfObjectsAreUnEqualAccordingToEqualsAndHashcodeMethod(Object o1, Object o2, Object o3){
        // default contract tests
        EqualsUtils.objectsConformToEqualsContract(o1, o2, o3);
        HashCodeUtils.objectsConformToHashcodeContract(o1, o2, o3);
        // test for expected unequality
        Assert.assertTrue("All objects should be unequal according to equals()",!o1.equals(o2) && !o1.equals(o3) && !o2.equals(o3));
    }

    @Test
    @Parameters(method = "equalComparablesProvider")
    public void testsForEqualComparables(Object o1, Object o2, Object o3) {
        // default Comparable tests
        CompareToUtils.objectsConformToComparableContract(o1, o2, o3);
        //
        Comparable c1 = (Comparable) o1;
        Comparable c2 = (Comparable) o2;
        Comparable c3 = (Comparable) o3;
        Assert.assertTrue(c1.compareTo(c2) == 0 && c1.compareTo(c3) == 0 && c2.compareTo(c3) == 0);
    }

    @Test
    @Parameters(method = "sortedComparablesProvider")
    public void testsForSortedComparables(Object o1, Object o2, Object o3) {
        // default Comparable tests
        CompareToUtils.objectsConformToComparableContract(o1, o2, o3);
        //
        Comparable c1 = (Comparable) o1;
        Comparable c2 = (Comparable) o2;
        Comparable c3 = (Comparable) o3;
        Assert.assertTrue(c1.compareTo(c2) < 0 && c2.compareTo(c3) < 0 && c1.compareTo(c3) < 0);
    }

    @Test
    @Parameters(method = "unsortedComparablesProvider")
    public void testsForUnsortedComparables(Object o1, Object o2, Object o3) {
        // default Comparable tests
        CompareToUtils.objectsConformToComparableContract(o1, o2, o3);
        //
        Comparable c1 = (Comparable) o1;
        Comparable c2 = (Comparable) o2;
        Comparable c3 = (Comparable) o3;
        Assert.assertTrue(
                !(
                        (c1.compareTo(c2) < 0 && c2.compareTo(c3) < 0) || (c1.compareTo(c2) > 0 && c2.compareTo(c3) > 0)
                                || (c1.compareTo(c2) == 0 && c1.compareTo(c3) == 0 && c2.compareTo(c3) == 0)
                )
        );
    }

}