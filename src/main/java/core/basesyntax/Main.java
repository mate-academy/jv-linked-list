package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       MyLinkedList<Integer> s = new MyLinkedList<>();
       s.add(666);
       s.add(777);
       s.add(888);
        System.out.println(s.size());
        s.remove(0);
        System.out.println(s.size());


    }

}
