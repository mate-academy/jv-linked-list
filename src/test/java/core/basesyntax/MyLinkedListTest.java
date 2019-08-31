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
    private static final String NEW_ITEM = "NewFirst";
    private static final String ANOTHER_NEW_ITEM = "NewSecond";

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
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);
        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        Assert.assertEquals(THIRD_ITEM, actualThird);
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
        Assert.assertEquals(expectedFirst, actualFirst);
        Assert.assertEquals(expectedSecond, actualSecond);
        Assert.assertEquals(expectedLast, actualLast);
    }

    @Test
    public void testGetByIndex() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
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
        Assert.assertEquals(NEW_ITEM, actualFirst);
        Assert.assertEquals(ANOTHER_NEW_ITEM, actualSecond);
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
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        String actualFirst = myLinkedList.remove(0);
        String actualSecond = myLinkedList.remove(3);
        String actualThird = myLinkedList.remove(3);
        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        Assert.assertEquals(THIRD_ITEM, actualThird);
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
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        String actualFirst = myLinkedList.remove(FIRST_ITEM);
        String actualSecond = myLinkedList.remove(SECOND_ITEM);
        String actualThird = myLinkedList.remove(THIRD_ITEM);
        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        Assert.assertEquals(THIRD_ITEM, actualThird);
        String actualFourth = myLinkedList.remove(NEW_ITEM);
        Assert.assertNull(actualFourth);
    }

    @Test
    public void testSize() {
        int actualInitialSize = myLinkedList.size();
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals(0, actualInitialSize);
        Assert.assertEquals(3, myLinkedList.size());
    }

    @Test
    public void testSizeAfterSet() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals(3, myLinkedList.size());
        myLinkedList.set(NEW_ITEM, 1);
        Assert.assertEquals(3, myLinkedList.size());
    }

    @Test
    public void testSizeAfterRemove() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals(3, myLinkedList.size());
        myLinkedList.remove(1);
        Assert.assertEquals(2, myLinkedList.size());
    }

    @Test
    public void testIsEmpty() {
        boolean actualInitialEmpty = myLinkedList.isEmpty();
        myLinkedList.add(FIRST_ITEM);
        boolean actualEmpty = myLinkedList.isEmpty();
        Assert.assertTrue(actualInitialEmpty);
        Assert.assertFalse(actualEmpty);
    }

    @Test
    public void testIsEmptyAfterRemove() {
        myLinkedList.add(FIRST_ITEM);
        boolean actualEmpty = myLinkedList.isEmpty();
        myLinkedList.remove(0);
        boolean actualEmptyAfterRemove = myLinkedList.isEmpty();
        Assert.assertFalse(actualEmpty);
        Assert.assertTrue(actualEmptyAfterRemove);
    }
}
