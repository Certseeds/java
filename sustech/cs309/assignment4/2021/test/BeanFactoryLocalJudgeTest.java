import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import testclass.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BeanFactoryLocalJudgeTest {

    private static BeanFactory beanFactory;

    @BeforeAll
    static void setup() {
        beanFactory = new BeanFactoryImpl();
        beanFactory.loadInjectProperties(new File("test/resources/inject.properties"));
        beanFactory.loadValueProperties(new File("test/resources/value.properties"));
    }

    @Test
    public void testResolveWithNoDependency() {
        final A instance = beanFactory.createInstance(A.class);
        assertNotNull(instance);
    }

    @Test
    public void testWithConstructorDependency() {
        final B instance = beanFactory.createInstance(B.class);
        assertNotNull(instance);
        assertNotNull(instance.getCDep());
        assertNotNull(instance.getDDep());
    }

    @Test
    public void testWithAnnotationDependency() {
        final G instance = beanFactory.createInstance(G.class);
        assertNotNull(instance);
        assertNotNull(instance.getCDep());
        assertNotNull(instance.getDDep());
    }

    @Test
    public void testMixedDependencies() {
        final H instance = beanFactory.createInstance(H.class);
        assertNotNull(instance);
        assertNotNull(instance.getCDep());
        assertNotNull(instance.getDDep());
    }

    @Test
    public void testImplTypeForInterface() {
        final E instance = beanFactory.createInstance(E.class);
        assertNotNull(instance);
        assertTrue(instance instanceof EImpl);
    }

    @Test
    public void testImplTypeForAbstractClass() {
        final F instance = beanFactory.createInstance(F.class);
        assertNotNull(instance);
        assertTrue(instance instanceof FEnhanced);
    }

    @Test
    public void testDependencyImpl() {
        final K instance = beanFactory.createInstance(K.class);
        assertNotNull(instance);
        assertNotNull(instance.getEDep());
        assertNotNull(instance.getFDep());
        assertTrue(instance.getEDep() instanceof EImpl);
        assertTrue(instance.getFDep() instanceof FEnhanced);
    }

    @Test
    public void testConstructorInject() {
        final L instance = beanFactory.createInstance(L.class);
        assertNotNull(instance);
        assertNotNull(instance.getBDep());
        assertTrue(instance.isBool());
    }

    @Test
    public void testFieldWithValue() {
        final D instance = beanFactory.createInstance(D.class);
        assertEquals(10, instance.getVal());
    }

    @Test
    public void testPrimitiveArrayValues() {
        final J instance = beanFactory.createInstance(J.class);
        assertNotNull(instance);
        assertTrue(instance instanceof JImpl2);
        assertArrayEquals(new boolean[]{true, false, false, true}, instance.getBools());
    }

    @Test
    public void testWrappedPrimitiveArrayValues() {
        final J instance = beanFactory.createInstance(J.class);
        assertNotNull(instance);
        assertTrue(instance instanceof JImpl2);
        assertArrayEquals(new Integer[]{2, 3, 5, 7, 11}, instance.getIntegers());
    }

    @Test
    public void testStringArrayValues() {
        final J instance = beanFactory.createInstance(J.class);
        assertNotNull(instance);
        assertTrue(instance instanceof JImpl2);
    }

    @Test
    public void testM() {
        final M instance = beanFactory.createInstance(M.class);
        assertNotNull(instance);
        assertTrue(instance.isbool());
        assertFalse(instance.isBool());
    }

    @Test
    public void testConfig() {
        final config instance = beanFactory.createInstance(config.class);
        assertNotNull(instance.getA());
        assertNotNull(instance.getC());
        {
            assertNotNull(instance.getD());
            assertEquals(10, instance.getD().getVal());
        }
        {
            assertNotNull(instance.getB());
            assertNotNull(instance.getB().getCDep());
            assertNotNull(instance.getB().getDDep());
        }
        {
            assertNotNull(instance.getG());
            assertNotNull(instance.getG().getCDep());
            assertNotNull(instance.getG().getDDep());
        }
        {
            assertNotNull(instance.getH());
            assertNotNull(instance.getH().getCDep());
            assertNotNull(instance.getH().getDDep());
        }
        {
            assertNotNull(instance.getE());
            assertTrue(instance.getE() instanceof EImpl);
        }
        {
            assertNotNull(instance.getF());
            assertTrue(instance.getF() instanceof FEnhanced);
        }
        {
            assertNotNull(instance.getK());
            assertNotNull(instance.getK().getEDep());
            assertNotNull(instance.getK().getFDep());
            assertTrue(instance.getK().getEDep() instanceof EImpl);
            assertTrue(instance.getK().getFDep() instanceof FEnhanced);
        }
        {
            assertNotNull(instance.getL());
            assertNotNull(instance.getL().getBDep());
            assertTrue(instance.getL().isBool());
        }
        {
            assertNotNull(instance.getJ());
            assertTrue(instance.getJ() instanceof JImpl2);
            assertArrayEquals(new boolean[]{true, false, false, true}, instance.getJ().getBools());
            assertArrayEquals(new Integer[]{2, 3, 5, 7, 11}, instance.getJ().getIntegers());
            assertTrue(instance.getJ() instanceof JImpl2);
        }
        {
            assertTrue(instance.getM().isbool());
            assertFalse(instance.getM().isBool());
        }
        {
            assertEquals(2200, instance.getShor());
            assertEquals(new Short((short) 2200), instance.getShor2());
        }
        {
            assertEquals(7, instance.getByt());
            assertEquals(instance.getByt(), instance.getByt2());
        }
        assertEquals("test-string", instance.getStr());
        {
            assertEquals(4.2, instance.getDoubl(), 0.001);
            assertEquals(4.2, instance.getDoubl2(), 0.001);
        }
        {
            assertEquals(3.14, instance.getFloa(), 0.001);
            assertEquals(instance.getFloa(), instance.getFloa2(), 0.001);
        }
        {
            assertEquals(4396934, instance.getIn());
            assertEquals(4396934, instance.getIn2());
        }
        {
            assertEquals(21474836481L, instance.getLon());
            assertEquals(21474836481L, instance.getLon2());
        }
        {
            assertTrue(instance.getBoolea2());
            assertTrue(instance.getBoolea());
            assertEquals(instance.getBoolea(), instance.getBoolea2());
        }
        {
            assertEquals('F', instance.getCha());
            assertEquals('F', instance.getCha2());
        }
        {
            assertArrayEquals(new Integer[]{1, 0, 0, 8, 6, 1, 0, 0, 1, 0}, instance.getList_integer());
            assertArrayEquals(new int[]{1, 0, 0, 8, 6, 1, 0, 0, 1, 0}, instance.getList_integer2());
        }
        {
            assertArrayEquals(new double[]{1.0d, 3.0d, 5.2, 42.777}, instance.getList_double_delimiter());
            assertArrayEquals(new Double[]{1.0d, 3.0d, 5.2, 42.777}, instance.getList_double_delimiter2());
        }
        assertArrayEquals(new String[]{"a", "bcd", "efghijk", "1"}, instance.getList_string());
        {
            assertArrayEquals(new Boolean[]{true, true, false, true}, instance.getList_boolean());
            assertArrayEquals(new boolean[]{true, true, false, true}, instance.getList_boolean2());
        }
        {
            assertArrayEquals(new Byte[]{2, 3, 4, 8, 6, 9, 0}, instance.getList_byte());
            assertArrayEquals(new byte[]{2, 3, 4, 8, 6, 9, 0}, instance.getList_byte2());
        }
        {
            assertArrayEquals(new Float[]{1.1f, 2.2f, 3.3f, 4.4f}, instance.getList_float());
            assertArrayEquals(new float[]{1.1f, 2.2f, 3.3f, 4.4f}, instance.getList_float2());
        }
        {
            assertArrayEquals(new Long[]{1122334L, 5566778889L, 21474836482L, -21474836481L}, instance.getList_long());
            assertArrayEquals(new long[]{1122334L, 5566778889L, 21474836482L, -21474836481L}, instance.getList_long2());
        }
        {
            assertArrayEquals(new Short[]{114, 451, 149, 191, 198, 810, 983}, instance.getList_short());
            assertArrayEquals(new short[]{114, 451, 149, 191, 198, 810, 983}, instance.getList_short2());
        }
        {
            assertArrayEquals(new Character[]{'A', 'B', 'C', 'D', 'e', 'f', 'g'}, instance.getList_char());
            assertArrayEquals(new char[]{'A', 'B', 'C', 'D', 'e', 'f', 'g'}, instance.getList_char2());
        }
        {
            assertEquals("sustech-ooad", instance.getField_str());
            assertEquals(42, instance.getField_int());
            assertArrayEquals(new String[]{"sustech", "cs", "ooad"}, instance.getField_list());
        }
    }
}
