package core.basesyntax;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> two = new MyLinkedList<>();

        two.add("One");
        two.add("Two");
        two.add("Three");
        two.add("Four");
        two.remove("On");
        System.out.println(two.get(0));
    }
}
