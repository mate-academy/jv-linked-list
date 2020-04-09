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
        boolean actualResult = myLinkedList.add(NULL_ITEM);
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
        Assert.assertTrue("Test failed! This method should return true ", actualResult);
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
    public void testAddByIndexToTheTop() {
        myLinkedList.add(FIRST_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 1);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.add(THIRD_ITEM, 3);
    }

    @Test
    public void testAddByIndexAtTheMiddle() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(FIRST_ITEM, 2);

        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);
        String actualFourth = myLinkedList.get(3);

        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        Assert.assertEquals(FIRST_ITEM, actualThird);
        Assert.assertEquals(THIRD_ITEM, actualFourth);
        Assert.assertEquals(4, myLinkedList.size());
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.add(THIRD_ITEM, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddByNegativeIndex() {
        myLinkedList.add(FIRST_ITEM, -1);
    }

    @Test
    public void testAddAll() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        boolean isAddedAll = myLinkedList.addAll(DEFAULT_LIST);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualLast = myLinkedList.get(DEFAULT_LIST.size() - 1);
        int actualSize = myLinkedList.size();

        String expectedLast = DEFAULT_LIST.get(DEFAULT_LIST.size() - 3);
        int expectedSize = 8;
        Assert.assertTrue(isAddedAll);
        Assert.assertEquals("Test failed! First element should be " + FIRST_ITEM,
                FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Second element should be " + SECOND_ITEM,
                SECOND_ITEM, actualSecond);
        Assert.assertEquals("Test failed! Last element should be " + expectedLast,
                expectedLast, actualLast);
        Assert.assertEquals("Test failed! Size should be  " + expectedSize,
                expectedSize, actualSize);
    }

    @Test
    public void testGetByIndex() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        String actualFirst = (String) myLinkedList.get(0);
        String actualSecond = (String) myLinkedList.get(1);
        String actualThird = (String) myLinkedList.get(2);
        Assert.assertEquals("Test failed! First element should be " + FIRST_ITEM,
                FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Second element should be " + SECOND_ITEM,
                SECOND_ITEM, actualSecond);
        Assert.assertEquals("Test failed! Second element should be " + THIRD_ITEM,
                THIRD_ITEM, actualThird);
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
        String oldFirstItem = myLinkedList.set(NEW_ITEM, 0);
        String oldSecondItem = myLinkedList.set(ANOTHER_NEW_ITEM, 1);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        Assert.assertEquals(oldFirstItem, FIRST_ITEM);
        Assert.assertEquals(oldSecondItem, SECOND_ITEM);
        Assert.assertEquals("Test failed! After setting expected element is " + NEW_ITEM,
                NEW_ITEM, actualFirst);
        Assert.assertEquals("Test failed! After setting expected element is "
                + ANOTHER_NEW_ITEM, ANOTHER_NEW_ITEM, actualSecond);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
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
        String actualRemovedFirst = myLinkedList.remove(0);
        String actualRemovedSecond = myLinkedList.remove(3);
        String actualRemovedThird = myLinkedList.remove(3);
        String actualRemovedNull = myLinkedList.remove(3);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + FIRST_ITEM, FIRST_ITEM, actualRemovedFirst);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + SECOND_ITEM, SECOND_ITEM, actualRemovedSecond);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + THIRD_ITEM, THIRD_ITEM, actualRemovedThird);
        Assert.assertEquals("Test failed! Expected value after removing should be "
                + NULL_ITEM, NULL_ITEM, actualRemovedNull);

        String actualSecondExist = myLinkedList.get(0);
        String actualThirdExist = myLinkedList.get(1);
        String actualNullExist = myLinkedList.get(2);
        Assert.assertEquals("Test failed! After removing list should consist value  "
                + SECOND_ITEM, SECOND_ITEM, actualSecondExist);
        Assert.assertEquals("Test failed! After removing list should consist value  "
                + THIRD_ITEM, THIRD_ITEM, actualThirdExist);
        Assert.assertEquals("Test failed! After removing list should consist value  "
                + NULL_ITEM, NULL_ITEM, actualNullExist);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());

        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.remove(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveByNegativeIndex() {
        myLinkedList.remove(-1);
    }

    @Test
    public void testRemoveByItemPositive() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(NULL_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(NULL_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);

        boolean isFirstRemoveActual = myLinkedList.remove(FIRST_ITEM);
        boolean isNullRemoveActual = myLinkedList.remove(NULL_ITEM);
        boolean isThirdRemoveActual = myLinkedList.remove(THIRD_ITEM);
        boolean isNextFirstRemoveActual = myLinkedList.remove(FIRST_ITEM);

        Assert.assertTrue("Test failed! Result after removing should be true",
                isFirstRemoveActual);
        Assert.assertTrue("Test failed! Result after removing should be true",
                isNullRemoveActual);
        Assert.assertTrue("Test failed! Result after removing should be true",
                isThirdRemoveActual);
        Assert.assertTrue("Test failed! Result after removing should be true",
                isNextFirstRemoveActual);

        String actualSecondExist = (String) myLinkedList.get(0);
        String actualNullExist = (String) myLinkedList.get(1);
        String actualNextSecondExist = (String) myLinkedList.get(2);
        String actualThirdExist = (String) myLinkedList.get(3);

        Assert.assertEquals("Test failed! After removing list should consist value  "
                + SECOND_ITEM, SECOND_ITEM, actualSecondExist);
        Assert.assertEquals("Test failed! After removing list should consist value  "
                + NULL_ITEM, NULL_ITEM, actualNullExist);
        Assert.assertEquals("Test failed! After removing list should consist value  "
                + SECOND_ITEM, SECOND_ITEM, actualNextSecondExist);
        Assert.assertEquals("Test failed! After removing list should consist value  "
                + THIRD_ITEM, THIRD_ITEM, actualThirdExist);
        Assert.assertEquals("Expected size is incorrect", 4, myLinkedList.size());

    }

    @Test
    public void testRemoveByItemNegative() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);

        boolean isNewItemRemoveActual = myLinkedList.remove(NEW_ITEM);
        boolean isNullRemoveActual = myLinkedList.remove(NULL_ITEM);

        Assert.assertFalse("Test failed! Result after removing should be false",
                isNewItemRemoveActual);
        Assert.assertFalse("Test failed! Result after removing should be false",
                isNullRemoveActual);
        Assert.assertEquals("Expected size is incorrect", 3, myLinkedList.size());
    }

    @Test
    public void removeObjectValueByItemPositive() {
        MyLinkedListInterface<Cat> cats = new MyLinkedList<>();
        cats.add(FIRST_CAT);
        cats.add(SECOND_CAT);
        cats.add(THIRD_CAT);
        Assert.assertEquals("Expected size is incorrect", 3, cats.size());

        boolean isSecondCatRemove = cats.remove(THE_SAME_SECOND_CAT);
        Assert.assertTrue("Test failed! Result after removing should be true", isSecondCatRemove);
        Assert.assertEquals("Expected size is incorrect", 2, cats.size());
    }

    @Test
    public void removeObjectValueByItemNegative() {
        MyLinkedListInterface<Cat> cats = new MyLinkedList<>();
        cats.add(FIRST_CAT);
        cats.add(SECOND_CAT);
        Assert.assertEquals("Expected size is incorrect", 2, cats.size());

        boolean isThirdCatRemove = cats.remove(THIRD_CAT);
        Assert.assertFalse("Test failed! Result after removing should be true", isThirdCatRemove);
        Assert.assertEquals("Expected size is incorrect", 2, cats.size());
    }

    @Test
    public void addAndRemoveToListTest(){
        myLinkedList.add(FIRST_ITEM);
        int actualSizeAfterAddFirst = myLinkedList.size();
        Assert.assertEquals(1, actualSizeAfterAddFirst);

        boolean firstRemove = myLinkedList.remove(FIRST_ITEM);
        int actualSizeAfterRemoveFirst = myLinkedList.size();
        Assert.assertTrue(firstRemove);
        Assert.assertEquals(0, actualSizeAfterRemoveFirst);

        myLinkedList.add(SECOND_ITEM);
        int actualSizeAfterAddSecond = myLinkedList.size();
        Assert.assertEquals(1, actualSizeAfterAddSecond);

        boolean secondRemove = myLinkedList.remove(SECOND_ITEM);
        int actualSizeAfterRemoveSecond = myLinkedList.size();
        Assert.assertTrue(secondRemove);
        Assert.assertEquals(0, actualSizeAfterRemoveSecond);

        myLinkedList.add(THIRD_ITEM);
        int actualSizeAfterAddThird = myLinkedList.size();
        Assert.assertEquals(1, actualSizeAfterAddThird);

        boolean thirdRemove = myLinkedList.remove(THIRD_ITEM);
        int actualSizeAfterRemoveThird = myLinkedList.size();
        Assert.assertTrue(thirdRemove);
        Assert.assertEquals(0, actualSizeAfterRemoveThird);
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
