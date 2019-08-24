package core.basesyntax;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyLinkedListTest {

    private static MyLinkedListInterface<String> myLinkedList = new MyLinkedList<>();
    private static List<String> linkedList = new LinkedList<>();

    private static final List<String> DEFAULT_LIST = new LinkedList<>(
            Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth"));

    @Test
    public void test1Add() {
        myLinkedList.add("First");
        myLinkedList.add("Second");
        myLinkedList.add("Third");
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.add("Third");
        Assert.assertEquals(linkedList.toString(), myLinkedList.toString());
    }

    @Test
    public void test2Add() {
        myLinkedList.add("First", 0);
        myLinkedList.add("Second", 1);
        myLinkedList.add("Third", 5);
        linkedList.add(0, "First");
        linkedList.add(1, "Second");
        linkedList.add(5, "Third");
        Assert.assertEquals(linkedList.toString(), myLinkedList.toString());
    }

    @Test
    public void test3AddAll() {
        myLinkedList.addAll(DEFAULT_LIST);
        linkedList.addAll(DEFAULT_LIST);
        Assert.assertEquals(linkedList.toString(), myLinkedList.toString());
    }

    @Test
    public void test4Get() {
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualThird = myLinkedList.get(5);
        String expectedFirst = linkedList.get(0);
        String expectedSecond = linkedList.get(1);
        String expectedThird = linkedList.get(5);
        Assert.assertEquals(expectedFirst, actualFirst);
        Assert.assertEquals(expectedSecond, actualSecond);
        Assert.assertEquals(expectedThird, actualThird);
    }

    @Test
    public void test5Set() {
        myLinkedList.set("newFirst", 0);
        myLinkedList.set("newSecond", 1);
        myLinkedList.set("newThird", 2);
        linkedList.set(0, "newFirst");
        linkedList.set(1, "newSecond");
        linkedList.set(2, "newThird");
        Assert.assertEquals(linkedList.toString(), myLinkedList.toString());
    }

    @Test
    public void test6Remove() {
        String actualFirst = myLinkedList.remove(0);
        String actualSecond = myLinkedList.remove(2);
        String actualThird = myLinkedList.remove(3);
        String expectedFirst = linkedList.remove(0);
        String expectedSecond = linkedList.remove(2);
        String expectedThird = linkedList.remove(3);
        Assert.assertEquals(expectedFirst, actualFirst);
        Assert.assertEquals(expectedSecond, actualSecond);
        Assert.assertEquals(expectedThird, actualThird);
        Assert.assertEquals(linkedList.toString(), myLinkedList.toString());
    }

    @Test
    public void test7Remove() {
        myLinkedList.remove("First");
        myLinkedList.remove("Fourth");
        myLinkedList.remove("Sixth");
        linkedList.remove("First");
        linkedList.remove("Fourth");
        linkedList.remove("Sixth");
        Assert.assertEquals(linkedList.toString(), myLinkedList.toString());
    }

    @Test
    public void test8Size() {
        int actualSize = myLinkedList.size();
        int expectedSize = linkedList.size();
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void test19IsEmpty() {
        boolean actual = myLinkedList.isEmpty();
        boolean expected = linkedList.isEmpty();
        Assert.assertEquals(expected, actual);
    }
}
