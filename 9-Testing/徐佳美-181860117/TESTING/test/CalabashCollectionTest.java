package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CalabashCollectionTest {
    ArrayList<Calabash> Cal1 = new ArrayList<Calabash>();

    @BeforeEach
    void setUp(){
        Cal1 = new ArrayList<Calabash>(){
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                add(new Calabash("a", "male"));
                add(new Calabash("f", "female"));
                add(new Calabash("b", "male"));
                add(new Calabash("g", "female"));
                add(new Calabash("c", "male"));
                add(new Calabash("h", "female"));
                add(new Calabash("d", "male"));
                add(new Calabash("i", "female"));
                add(new Calabash("e", "male"));
                add(new Calabash("j", "female"));
            }
        };
    }

    @AfterEach
    public void tearDown(){
        this.Cal1 = null;
    }

    @Test
    
    void incSortTest() {
        Iterator<Calabash> it = Calabash.iterator();
        assertEquals(it.next().name, "a");
        assertEquals(it.next().name, "b");
        assertEquals(it.next().name, "c");
        assertEquals(it.next().name, "d");
        assertEquals(it.next().name, "e");
        assertEquals(it.next().name, "f");
        assertEquals(it.next().name, "h");
        assertEquals(it.next().name, "i");
        assertFalse(it.hasNext());
    }

    @Test
    void desSortTest() {
        Iterator<Calabash> it = Calabash.iterator();
        assertEquals(it.next().name, "i");
        assertEquals(it.next().name, "h");
        assertEquals(it.next().name, "f");
        assertEquals(it.next().name, "e");
        assertEquals(it.next().name, "d");
        assertEquals(it.next().name, "c");
        assertEquals(it.next().name, "b");
        assertEquals(it.next().name, "a");
        assertFalse(it.hasNext());
    }

  
}