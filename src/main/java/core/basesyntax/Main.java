package core.basesyntax;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList a = new MyLinkedList();
        a.add("Hello");
        a.add("world");
        a.add("mates");
        a.add("earth", 0);

        System.out.println(a.get(4));
    }
}
