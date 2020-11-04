package casino.idfactory;

import org.junit.Assert;

/**
 * this class contains methods to check specific aspects of the equals contract
 *
 * @author erik v.d. schriek
 */
class EqualsUtils {

    /**
     * static method to test reflexivity
     * x.equals(x) = true;
     *
     * @param p
     * @return
     */
    public static boolean testReflexitivity(Object p) {
        boolean eq1 = p.equals(p);
        // return eq1 == true
        return (eq1 == true);
    }

    /**
     * static method to test symmetry
     * p.equals(q) == q.equals(p)
     *
     * @param p1 first
     * @param p2 second
     */
    public static boolean testSymmetry(Object p1, Object p2) {
        boolean eq1 = p1.equals(p2);
        boolean eq2 = p2.equals(p1);
        // return (eq1 == true) == (eq2 == true)
        return (eq1 == true) == (eq2 == true);
    }

    /**
     * static method to test transitivity
     * p.equals(q) && q.equals(r) -> p.equals(r)
     *
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static boolean testTransitivity(Object p1, Object p2, Object p3) {
        boolean eq1 = p1.equals(p2);
        boolean eq2 = p2.equals(p3);
        boolean eq3 = p1.equals(p3);
        // return ((eq1 == true) && (eq2 == true)) -> (eq3 == true)
        // <boolean logic rules>
        // return !((eq1 == true) && (eq2 == true)) || (eq3 == true)
        return !((eq1 == true) && (eq2 == true)) || (eq3 == true);
    }

    /**
     * static method to test equaling null object.
     *
     * @param p1
     * @return
     */
    public static boolean testNullIsFalse(Object p1) {
        boolean eq1 = p1.equals(null);
        return (eq1 == false);
    }

    /**
     * methods used for 'general' test conforming to contract.
     */
    public static void objectsConformToEqualsContract(Object o1, Object o2, Object o3) {
        // assert reflexitivity
        Assert.assertTrue("object "+ o1 +" should be reflexitive",EqualsUtils.testReflexitivity(o1));
        Assert.assertTrue("object "+ o2 +" should be reflexitive",EqualsUtils.testReflexitivity(o2));
        Assert.assertTrue("object "+ o3 +" should be reflexitive",EqualsUtils.testReflexitivity(o3));
        // assert symmetry
        Assert.assertTrue("object "+ o1 +" and "+ o2 +" should be symmetrical",EqualsUtils.testSymmetry(o1, o2));
        Assert.assertTrue("object "+ o1 +" and "+ o3 +" should be symmetrical",EqualsUtils.testSymmetry(o1, o3));
        Assert.assertTrue("object "+ o2 +" and "+ o3 +" should be symmetrical",EqualsUtils.testSymmetry(o2, o3));
        // assert Transitivity
        Assert.assertTrue("objects "+ o1 +" and "+ o2 +" and "+ o3 +" should be transitive",EqualsUtils.testTransitivity(o1, o2, o3));
        // assert NULL inequality
        Assert.assertTrue("object "+ o1 +" should be inequal to null",EqualsUtils.testNullIsFalse(o1));
        Assert.assertTrue("object "+ o2 +" should be inequal to null",EqualsUtils.testNullIsFalse(o2));
        Assert.assertTrue("object "+ o3 +" should be inequal to null",EqualsUtils.testNullIsFalse(o3));
    }

}
