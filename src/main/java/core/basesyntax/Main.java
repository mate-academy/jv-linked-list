package core.basesyntax;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final List<String> DEFAULT_LIST = new LinkedList<>(
            Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth"));
    private static final String FIRST_ITEM = "First";
    private static final String SECOND_ITEM = "Second";
    private static final String THIRD_ITEM = "Third";
    private static final String NULL_ITEM = null;
    private static final String NEW_ITEM = "NewFirst";
    private static final String ANOTHER_NEW_ITEM = "NewSecond";
    private static MyLinkedListInterface<String> myLinkedList;

    public static void main(String[] args) {
        myLinkedList = new MyLinkedList<>();
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.addAll(DEFAULT_LIST);
        String actualFirst = myLinkedList.get(0);
        String actualSecond = myLinkedList.get(1);
        String actualIndexFive = myLinkedList.get(5);
        int actualSize = myLinkedList.size();
        System.out.println(actualFirst);
        System.out.println(actualSecond);
        System.out.println(actualIndexFive);
        System.out.println(actualSize);
    }
}
