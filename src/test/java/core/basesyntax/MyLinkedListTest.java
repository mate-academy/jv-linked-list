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
    private static final Cat FOURTH_CAT = new Cat("Leopold", "yellow");
    private static final Cat THE_SAME_SECOND_CAT = new Cat("Barsik", "black");
    private static final Cat THE_SAME_THIRD_CAT = new Cat("Tom", "white");
    private static final Cat THE_SAME_FOURTH_CAT = new Cat("Leopold", "yellow");

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
            Assert.assertEquals("Test failed! Can't add multiple values correctly",
                    "String" + i, linkedList.get(i));
        }
    }

    @Test
    public void testAddByIndexToTheTop() {
        myLinkedList.add(FIRST_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 1);
        myLinkedList.add(THIRD_ITEM, 2);

        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);

        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(SECOND_ITEM, actualSecond);
        Assert.assertEquals(THIRD_ITEM, actualThird);

        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.add(THIRD_ITEM, 4);
    }

    @Test
    public void testAddByIndexToTheBottom() {
        myLinkedList.add(FIRST_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 1);
        myLinkedList.add(THIRD_ITEM, 0);
        myLinkedList.add(SECOND_ITEM, 0);

        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);
        String actualFourth = myLinkedList.get(3);

        Assert.assertEquals(SECOND_ITEM, actualFirst);
        Assert.assertEquals(THIRD_ITEM, actualSecond);
        Assert.assertEquals(FIRST_ITEM, actualThird);
        Assert.assertEquals(SECOND_ITEM, actualFourth);

        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.add(THIRD_ITEM, 5);
    }

    @Test
    public void testAddByIndexAtTheMiddle() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add(FIRST_ITEM, 2);
        myLinkedList.add(THIRD_ITEM, 1);

        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);
        String actualFourth = myLinkedList.get(3);
        String actualFifth = myLinkedList.get(4);

        Assert.assertEquals(FIRST_ITEM, actualFirst);
        Assert.assertEquals(THIRD_ITEM, actualSecond);
        Assert.assertEquals(SECOND_ITEM, actualThird);
        Assert.assertEquals(FIRST_ITEM, actualFourth);
        Assert.assertEquals(THIRD_ITEM, actualFifth);
        Assert.assertEquals(5, myLinkedList.size());
        exception.expect(IndexOutOfBoundsException.class);
        myLinkedList.add(THIRD_ITEM, 7);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddByNegativeIndex() {
        myLinkedList.add(FIRST_ITEM, -1);
    }

    @Test
    public void testAddAll() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.addAll(DEFAULT_LIST);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualIndexFive = myLinkedList.get(5);
        int actualSize = myLinkedList.size();

        String expectedIndexFive = DEFAULT_LIST.get(3);
        int expectedSize = 8;
        Assert.assertEquals("Test failed! First element should be " + FIRST_ITEM,
                FIRST_ITEM, actualFirst);
        Assert.assertEquals("Test failed! Second element should be " + SECOND_ITEM,
                SECOND_ITEM, actualSecond);
        Assert.assertEquals("Test failed! Element on index 5 should be " + expectedIndexFive,
                expectedIndexFive, actualIndexFive);
        Assert.assertEquals("Test failed! Size should be  " + expectedSize,
                expectedSize, actualSize);
    }

    @Test
    public void testGetByIndex() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(2);
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetByIndexEqualsSize() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals(3, myLinkedList.size());
        myLinkedList.get(3);
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
        Assert.assertEquals(FIRST_ITEM, oldFirstItem);
        Assert.assertEquals(SECOND_ITEM, oldSecondItem);
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetByIndexEqualsSize() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals(3, myLinkedList.size());
        myLinkedList.set(FIRST_ITEM, 3);
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveByIndexEqualsSize() {
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        Assert.assertEquals(3, myLinkedList.size());
        myLinkedList.remove(3);

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

        String actualSecondExist = myLinkedList.get(0);
        String actualNullExist = myLinkedList.get(1);
        String actualNextSecondExist = myLinkedList.get(2);
        String actualThirdExist = myLinkedList.get(3);

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
        cats.add(THIRD_CAT);
        cats.add(null);
        cats.add(FOURTH_CAT);
        cats.add(null);
        Assert.assertEquals("Expected size is incorrect", 7, cats.size());

        boolean isSecondCatRemove = cats.remove(THE_SAME_SECOND_CAT);
        Assert.assertTrue("Test failed! Result after removing should be true", isSecondCatRemove);
        Assert.assertEquals("Expected size is incorrect. Expected: 6, but was: " + cats.size(), 6, cats.size());
        Assert.assertEquals("Unexpected element found in list on 0 position. Expected: " + FIRST_CAT +", but was: " + cats.get(0),
                FIRST_CAT, cats.get(0));
        Assert.assertEquals("Unexpected element found in list on 1 position. Expected: " + THIRD_CAT +", but was: " + cats.get(1),
                THIRD_CAT, cats.get(1));
        Assert.assertEquals("Unexpected element found in list on 2 position. Expected: " + THIRD_CAT +", but was: " + cats.get(2),
                THIRD_CAT, cats.get(2));
        Assert.assertEquals("Unexpected element found in list on 3 position. Expected: null, but was: " + cats.get(3),
                null, cats.get(3));

        boolean isThirdCatRemove = cats.remove(THE_SAME_THIRD_CAT);
        Assert.assertTrue("Test failed! Result after removing should be true", isThirdCatRemove);
        Assert.assertEquals("Expected size is incorrect. Expected: 5, but was: " + cats.size(), 5, cats.size());
        Assert.assertEquals("Unexpected element found in list on 0 position. Expected: " + FIRST_CAT +", but was: " + cats.get(0),
                FIRST_CAT, cats.get(0));
        Assert.assertEquals("Unexpected element found in list on 1 position. Expected: " + THIRD_CAT +", but was: " + cats.get(1),
                THIRD_CAT, cats.get(1));
        Assert.assertEquals("Unexpected element found in list on 2 position. Expected: null, but was: " + cats.get(2),
                null, cats.get(2));

        boolean isFourthCatRemove = cats.remove(THE_SAME_FOURTH_CAT);
        Assert.assertTrue("Test failed! Result after removing should be true", isFourthCatRemove);
        Assert.assertEquals("Expected size is incorrect. Expected: 4, but was: " + cats.size(), 4, cats.size());
        Assert.assertEquals("Unexpected element found in list on 0 position. Expected: " + FIRST_CAT +", but was: " + cats.get(0),
                FIRST_CAT, cats.get(0));
        Assert.assertEquals("Unexpected element found in list on 1 position. Expected: " + THIRD_CAT +", but was: " + cats.get(1),
                THIRD_CAT, cats.get(1));
        Assert.assertEquals("Unexpected element found in list on 2 position. Expected: null, but was: " + cats.get(2),
                null, cats.get(2));

        boolean isNullRemove = cats.remove(null);
        Assert.assertTrue("Test failed! Result after removing should be true", isNullRemove);
        Assert.assertEquals("Expected size is incorrect. Expected: 3, but was: " + cats.size(), 3, cats.size());
        Assert.assertEquals("Unexpected element found in list on 0 position. Expected: " + FIRST_CAT +", but was: " + cats.get(0),
                FIRST_CAT, cats.get(0));
        Assert.assertEquals("Unexpected element found in list on 1 position. Expected: " + THIRD_CAT +", but was: " + cats.get(1),
                THIRD_CAT, cats.get(1));
        Assert.assertEquals("Unexpected element found in list on 2 position. Expected: null, but was: " + cats.get(2),
                null, cats.get(2));
    }

    @Test
    public void removeObjectValueByItemNegative() {
        MyLinkedListInterface<Cat> cats = new MyLinkedList<>();
        cats.add(FIRST_CAT);
        cats.add(SECOND_CAT);
        cats.add(THIRD_CAT);
        Assert.assertEquals("Expected size is incorrect", 3, cats.size());

        boolean isFourthCatRemove = cats.remove(FOURTH_CAT);
        Assert.assertFalse("Test failed! Result after removing non existed element should be false", isFourthCatRemove);
        Assert.assertEquals("Expected size is incorrect", 3, cats.size());

        boolean isNullRemove = cats.remove(null);
        Assert.assertFalse("Test failed! Result after removing non existed element should be false", isNullRemove);
        Assert.assertEquals("Expected size is incorrect", 3, cats.size());
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
