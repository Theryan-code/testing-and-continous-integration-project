package casino.idfactory;

import org.junit.Assert;

/**
 * this class contains methods to check specific aspects of Comparable contract, together with equals and hashcode
 *
 * @author erik v.d. Schriek
 */
class CompareToUtils {

    /* test
    The implementor must ensure sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y.
    (This implies that x.compareTo(y) must throw an exception iff y.compareTo(x) throws an exception.)
    */
    public static boolean signOfComparisonChangesWhenOrderOfComparisonChanges(Comparable c1, Comparable c2) {
        int signum1 = Integer.signum(c1.compareTo(c2));
        int signum2 = Integer.signum(c2.compareTo(c1));
        return (signum1 == -signum2);
    }

    /*
    The implementor must also ensure that the relation is transitive: (x.compareTo(y) > 0 && y.compareTo(z) > 0)
    implies x.compareTo(z) > 0.
     */
    public static boolean testRelationIsTransitive(Comparable c1, Comparable c2, Comparable c3) {
        int signum1 = Integer.signum(c1.compareTo(c2));
        int signum2 = Integer.signum(c2.compareTo(c3));
        int signum3 = Integer.signum(c1.compareTo(c3));
        //
        boolean eq1 = (signum1 == signum2);
        boolean eq2 = (signum1 == signum3);

        // return (eq1 == true)  -> (eq2 == true)
        // <boolean logic rules>
        // return !(eq1 == true) || (eq2 == true)
        return !(eq1 == true) || (eq2 == true);
    }

    /*
    Finally, the implementor must ensure that x.compareTo(y)==0 implies
    that sgn(x.compareTo(z)) == sgn(y.compareTo(z)), for all z.
     */
    public static boolean testSignumIsEqualBetweenComparisonsWithEqualComparables(Comparable c1, Comparable c2, Comparable c3) {
        int signum1 = Integer.signum(c1.compareTo(c2));
        int signum2 = Integer.signum(c2.compareTo(c3));
        int signum3 = Integer.signum(c1.compareTo(c3));
        //
        boolean eq1 = (signum1 == 0);
        boolean eq2 = (signum2 == signum3);
        return !eq1 || eq2;
    }

    /**
     * strongly recommended behaviour. (not required though)
     */
    public static boolean testComparableIsConsistentWithEquals(Comparable c1, Comparable c2) {
        boolean eq1 = (c1.compareTo(c2) == 0);
        boolean eq2 = (c1.equals(c2));
        //
        return (eq1 == eq2);
    }

    public static boolean testCompareToNullThrowsNullPointerException(Comparable c1) throws NullPointerException {
        try {
            c1.compareTo(null);
            return false;
        } catch (NullPointerException npe) {
            return true;
        }


    }

    public static void objectsConformToComparableContract(Object o1, Object o2, Object o3){
        // assert objects are Comparable type
        Assert.assertTrue("Object "+o1+" should be Comparable",o1 instanceof Comparable);
        Assert.assertTrue("Object "+o2+" should be Comparable",o2 instanceof Comparable);
        Assert.assertTrue("Object "+o3+" should be Comparable",o3 instanceof Comparable);
        Comparable c1 = (Comparable) o1;        
        Comparable c2 = (Comparable) o2;
        Comparable c3 = (Comparable) o3;


        // assert compareTo == 0 is consistent with equals
        Assert.assertTrue("relation "+c1+" and "+c2+" should be consistent with equals",CompareToUtils.testComparableIsConsistentWithEquals(c1,c2));
        Assert.assertTrue("relation "+c1+" and "+c3+" should be consistent with equals",CompareToUtils.testComparableIsConsistentWithEquals(c1,c3));
        Assert.assertTrue("relation "+c2+" and "+c3+" should be consistent with equals",CompareToUtils.testComparableIsConsistentWithEquals(c2,c3));


        // assert signum is correct
        Assert.assertTrue("relation "+c1+" and "+c2+" should have correct signum calculations",CompareToUtils.signOfComparisonChangesWhenOrderOfComparisonChanges(c1,c2));
        Assert.assertTrue("relation "+c1+" and "+c3+" should have correct signum calculations",CompareToUtils.signOfComparisonChangesWhenOrderOfComparisonChanges(c1,c3));
        Assert.assertTrue("relation "+c2+" and "+c3+" should have correct signum calculations",CompareToUtils.signOfComparisonChangesWhenOrderOfComparisonChanges(c2,c3));

        // assert compareto is transitive
        Assert.assertTrue("relation "+c1+" and "+c2+" and "+c3+" should be transitive",CompareToUtils.testRelationIsTransitive(c1,c2,c3));
        Assert.assertTrue("relation "+c1+" and "+c3+" and "+c2+" should be transitive",CompareToUtils.testRelationIsTransitive(c1,c3,c2));

        // assert equal objects compare the same
        Assert.assertTrue("relation "+c1+" and "+c2+" and "+c3+" should compare the same",CompareToUtils.testSignumIsEqualBetweenComparisonsWithEqualComparables(c1,c2,c3));
        Assert.assertTrue("relation "+c1+" and "+c3+" and "+c2+" should compare the same",CompareToUtils.testSignumIsEqualBetweenComparisonsWithEqualComparables(c1,c3,c2));

        // assert nullpointer is thrown

        Assert.assertTrue("Nullpointer exception should be thrown while comparing null to "+c1,CompareToUtils.testCompareToNullThrowsNullPointerException(c1));
        Assert.assertTrue("Nullpointer exception should be thrown while comparing null to "+c2,CompareToUtils.testCompareToNullThrowsNullPointerException(c2));
        Assert.assertTrue("Nullpointer exception should be thrown while comparing null to "+c3,CompareToUtils.testCompareToNullThrowsNullPointerException(c3));

    }


}
