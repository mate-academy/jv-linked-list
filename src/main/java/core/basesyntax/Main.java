package core.basesyntax;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> test = new MyLinkedList<>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(11);
        test.add(5);
        test.add(44);
        test.add(12);
        test.add(24);
        test.add(89);
        test.add(46);
        System.out.println(test.get(6));
        System.out.println(test.size());



    }
}
