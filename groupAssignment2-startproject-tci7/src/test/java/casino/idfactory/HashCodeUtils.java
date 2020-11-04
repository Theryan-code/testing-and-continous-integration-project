package casino.idfactory;

import org.junit.Assert;

/**
 * this class contains methods to check specific aspects of hashcode contract
 *
 * @author erik v.d. Schriek
 */
class HashCodeUtils {


    public static boolean testEqualsMeansSameHashcode(Object p1, Object p2) {
        boolean eq1 = p1.equals(p2);
        boolean eq2 = p1.hashCode() == p2.hashCode();
        // return (eq1 == true)  -> (eq2 == true)
        // <boolean logic rules>
        // return !(eq1 == true) || (eq2 == true)
        return !(eq1 == true) || (eq2 == true);
    }

    public static boolean testDifferentHashcodeMeansUnequal(Object p1, Object p2) {
        boolean eq1 = (p1.hashCode() != p2.hashCode());
        boolean eq2 = !(p1.equals(p2));
        // return (eq1 == true)  -> (eq2 == true)
        // <boolean logic rules>
        // return !(eq1 == true) || (eq2 == true)
        return !(eq1 == true) || (eq2 == true);
    }

    public static void objectsConformToHashcodeContract(Object o1, Object o2, Object o3){
        // assert equal objects have equal hashcode
        Assert.assertTrue("object "+o1+" and "+o2+" should have same hashcode",HashCodeUtils.testEqualsMeansSameHashcode(o1,o2));
        Assert.assertTrue("object "+o1+" and "+o3+" should have same hashcode",HashCodeUtils.testEqualsMeansSameHashcode(o1,o2));
        Assert.assertTrue("object "+o2+" and "+o3+" should have same hashcode",HashCodeUtils.testEqualsMeansSameHashcode(o1,o2));
        // assert different hashcode are unequal objects
        Assert.assertTrue("object "+o1+" and "+o2+" should be unequal",HashCodeUtils.testDifferentHashcodeMeansUnequal(o1,o2));
        Assert.assertTrue("object "+o1+" and "+o3+" should be unequal",HashCodeUtils.testDifferentHashcodeMeansUnequal(o1,o3));
        Assert.assertTrue("object "+o2+" and "+o2+" should be unequal",HashCodeUtils.testDifferentHashcodeMeansUnequal(o2,o3));
    }



}
