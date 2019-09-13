package core.basesyntax;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MyLinkedListTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static final String FIRST_ITEM = "First";
    private static final String SECOND_ITEM = "Second";
    private static final String THIRD_ITEM = "Third";
    private static final String NULL_ITEM = null;
    private static final String NEW_ITEM = "NewFirst";
    private static final String ANOTHER_NEW_ITEM = "NewSecond";
    private static final Cat FIRST_CAT = new Cat("Fantic", "grey");
    private static final Cat SECOND_CAT = new Cat("Barsik", "black");
    private static final Cat THIRD_CAT = new Cat("Tom", "white");
    private static final Cat THE_SAME_SECOND_CAT = new Cat("Barsik", "black");

    private static final List<String> DEFAULT_LIST = new LinkedList<>(
            Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth"));

    private static MyLinkedListInterface<String> myLinkedList;

    @Before
    public void setUp() {
        myLinkedList = new MyLinkedList<>();
    }

    @Test
    public void testAdd() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(NULL_ITEM);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);
        String actualFourth = myLinkedList.get(3);
        Assert.assertEquals("Test failed! First element should be " + FIRST_ITEM,
                FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Second element should be " + SECOND_ITEM,
                SECOND_ITEM, actualSecond);
        Assert.assertEquals("Test failed! Third element should be " + THIRD_ITEM,
                THIRD_ITEM, actualThird);
        Assert.assertNull("Test failed! Fourth element should be null", actualFourth);
    }

    @Test
    public void testAddManyData() {
        MyLinkedListInterface<String> linkedList = new MyLinkedList<>();
        for (int i = 0; i < 1000; i++) {
            linkedList.add("String" + i);
        }
        Assert.assertEquals("Expected size is incorrect", 1000, linkedList.size());
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals("Test failed! Linked list can't add a lot of value correctly",
                    "String" + i, linkedList.get(i));
        }
    }

    @Test
    public void testAddByIndex() {
        myLinkedList.add(FIRST_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 1);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.add(THIRD_ITEM, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddByNegativeIndex() {
        myLinkedList.add(FIRST_ITEM, -1);
    }

    @Test
    public void testAddAll() {
        myLinkedList.addAll(DEFAULT_LIST);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualLast = myLinkedList.get(DEFAULT_LIST.size() - 1);
        String expectedFirst = DEFAULT_LIST.get(0);
        String expectedSecond = DEFAULT_LIST.get(1);
        String expectedLast = DEFAULT_LIST.get(DEFAULT_LIST.size() - 1);
        Assert.assertEquals("Test failed! First element should be " + expectedFirst,
                expectedFirst, actualFirst);
        Assert.assertEquals("Test failed! Second element should be " + expectedSecond,
                expectedSecond, actualSecond);
        Assert.assertEquals("Test failed! Last element should be " + expectedLast,
                expectedLast, actualLast);
    }

    @Test
    public void testGetByIndex() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        Assert.assertEquals("Test failed! First element should be " + FIRST_ITEM,
                FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Second element should be " + SECOND_ITEM,
                SECOND_ITEM, actualSecond);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.get(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetByNegativeIndex() {
        myLinkedList.get(-1);
    }

    @Test
    public void testSetByIndex() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.set(NEW_ITEM, 0);
        myLinkedList.set(ANOTHER_NEW_ITEM, 1);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        Assert.assertEquals("Test failed! After setting expected element is " + NEW_ITEM,
                NEW_ITEM, actualFirst);
        Assert.assertEquals("Test failed! After setting expected element is "
                        + ANOTHER_NEW_ITEM,
                ANOTHER_NEW_ITEM, actualSecond);
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.set(NEW_ITEM, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetByNegativeIndex() {
        myLinkedList.set(NEW_ITEM, -1);
    }

    @Test
    public void testRemoveByIndex() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(NULL_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(NULL_ITEM);
        String actualFirst = myLinkedList.remove(0);
        String actualSecond = myLinkedList.remove(3);
        String actualThird = myLinkedList.remove(3);
        String actualFourth = myLinkedList.remove(3);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + FIRST_ITEM, FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + SECOND_ITEM, SECOND_ITEM, actualSecond);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + THIRD_ITEM, THIRD_ITEM, actualThird);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + NULL_ITEM, NULL_ITEM, actualFourth);
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.remove(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveByNegativeIndex() {
        myLinkedList.remove(-1);
    }

    @Test
    public void testRemoveByItem() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(NULL_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(NULL_ITEM);
        String actualFirst = myLinkedList.remove(FIRST_ITEM);
        String actualSecond = myLinkedList.remove(SECOND_ITEM);
        String actualThird = myLinkedList.remove(THIRD_ITEM);
        String actualNull = myLinkedList.remove(NULL_ITEM);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + FIRST_ITEM, FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + SECOND_ITEM, SECOND_ITEM, actualSecond);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + THIRD_ITEM, THIRD_ITEM, actualThird);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + NULL_ITEM, NULL_ITEM, actualNull);
        String actualFourth = myLinkedList.remove(NEW_ITEM);
        Assert.assertNull("Test failed! Expected value after removing should be null",
                actualFourth);
    }

    @Test
    public void removeObjectValueByItem() {
        MyLinkedListInterface<Cat> cats = new MyLinkedList<>();
        cats.add(FIRST_CAT);
        cats.add(SECOND_CAT);
        cats.add(THIRD_CAT);
        Assert.assertEquals("Expected size is incorrect", 3, cats.size());
        Cat actualResult = cats.remove(THE_SAME_SECOND_CAT);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + THE_SAME_SECOND_CAT.toString(), THE_SAME_SECOND_CAT, actualResult);
        Assert.assertEquals("Expected size is incorrect", 2, cats.size());
    }

    @Test
    public void testSize() {
        int actualInitialSize = myLinkedList.size();
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals("Expected size is incorrect", 0, actualInitialSize);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
    }

    @Test
    public void testSizeAfterSet() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
        myLinkedList.set(NEW_ITEM, 1);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
    }

    @Test
    public void testSizeAfterRemove() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
        myLinkedList.remove(1);
        Assert.assertEquals("Expected size is incorrect", 2, myLinkedList.size());
    }

    @Test
    public void testIsEmpty() {
        boolean actualInitialEmpty = myLinkedList.isEmpty();
        myLinkedList.add(FIRST_ITEM);
        boolean actualEmpty = myLinkedList.isEmpty();
        Assert.assertTrue("Test failed! isEmpty() should return true if the list is empty",
                actualInitialEmpty);
        Assert.assertFalse("Test failed! isEmpty() should return false if the list isn't empty",
                actualEmpty);
    }

    @Test
    public void testIsEmptyAfterRemove() {
        myLinkedList.add(FIRST_ITEM);
        boolean actualEmpty = myLinkedList.isEmpty();
        myLinkedList.remove(0);
        boolean actualEmptyAfterRemove = myLinkedList.isEmpty();
        Assert.assertFalse("Test failed! isEmpty() should return false if the list isn't empty",
                actualEmpty);
        Assert.assertTrue("Test failed! isEmpty() should return true if the list is empty",
                actualEmptyAfterRemove);
    }
}
