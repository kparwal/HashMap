package fellows.kpcb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Keshav on 10/25/2015.
 */
public class HashMapTest {

    HashMap<Integer> hm;
    @Before
    public void setUp() throws Exception {
        hm = new HashMap<>();
    }

    @After
    public void tearDown() throws Exception {
        hm = null;
        System.gc(); // force memory cleanup
    }

    @Test
    public void testSize() throws Exception {
        addAlphabet(hm);

        assertEquals(hm.size(), 26);
    }

    @Test
    public void testLoad() throws Exception {
        addAlphabet(hm);
        assertTrue(hm.load() < .67);
        hm.delete("a");
        hm.delete("b");
        hm.delete("c");
        hm.delete("d");
        hm.delete("e");

        assertNull(hm.get("a"));
        assertNull(hm.get("b"));
        assertNull(hm.get("c"));
        assertNull(hm.get("d"));
        assertNull(hm.get("e"));
        assertEquals(hm.get("v"), new Integer(22));
        assertEquals(hm.size(), 21);
    }

    @Test
    public void testSet() throws Exception {
        hm.set("a", 0);
        hm.set("b", 1);
        hm.set("c", 2);
        hm.set("d", 10);

        assertEquals(new Integer(0), hm.get("a"));
        assertEquals(new Integer(1), hm.get("b"));
        assertEquals(new Integer(2), hm.get("c"));
        assertEquals(new Integer(10), hm.get("d"));
    }

    @Test
    public void testReplace() throws Exception {
        hm.set("a", 0);
        hm.set("b", 1);
        hm.set("b", 2);
        hm.set("a", 10);

        assertEquals(new Integer(10), hm.get("a"));
        assertEquals(new Integer(2), hm.get("b"));
    }

    @Test
    public void testDelete() throws Exception {
        hm.set("a", 0);
        hm.set("b", 1);
        hm.set("c", 2);
        hm.set("d", 10);
        hm.delete("c");

        assertEquals(new Integer(0), hm.get("a"));
        assertEquals(new Integer(1), hm.get("b"));
        assertNull(hm.get("c"));
        assertEquals(new Integer(10), hm.get("d"));
    }

    @Test
    public void testGet() throws Exception {
        addAlphabet(hm);

        assertEquals(new Integer(1), hm.get("a"));
        assertEquals(new Integer(2), hm.get("b"));
        assertEquals(new Integer(3), hm.get("c"));
        assertEquals(new Integer(4), hm.get("d"));
    }

    private static void addAlphabet (HashMap hm) {
        hm.set("a", 1);
        hm.set("b", 2);
        hm.set("c", 3);
        hm.set("d", 4);
        hm.set("e", 5);
        hm.set("f", 6);
        hm.set("g", 7);
        hm.set("h", 8);
        hm.set("i", 9);
        hm.set("j", 10);
        hm.set("k", 11);
        hm.set("l", 12);
        hm.set("m", 13);
        hm.set("n", 14);
        hm.set("o", 15);
        hm.set("p", 16);
        hm.set("q", 17);
        hm.set("r", 18);
        hm.set("s", 19);
        hm.set("t", 20);
        hm.set("u", 21);
        hm.set("v", 22);
        hm.set("w", 23);
        hm.set("x", 24);
        hm.set("y", 25);
        hm.set("z", 26);
    }
}