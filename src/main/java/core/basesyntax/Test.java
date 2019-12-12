package core.basesyntax;

import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        String FIRST_ITEM = "First";
        String SECOND_ITEM = "Second";
        String THIRD_ITEM = "Third";
        String NEW_ITEM = "NewFirst";
        MyLinkedListInterface<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(FIRST_ITEM);
        myLinkedList.add(SECOND_ITEM);
        myLinkedList.add(THIRD_ITEM);
        myLinkedList.add("Forth");
        myLinkedList.add("Fifth");
        System.out.println(myLinkedList.toString());
        System.out.println(myLinkedList.remove(0));
        System.out.println(myLinkedList.toString());
        System.out.println(myLinkedList.remove(2));
    }
}
