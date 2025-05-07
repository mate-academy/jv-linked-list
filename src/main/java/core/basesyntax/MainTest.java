package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }
        System.out.println(list);
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.addAll(integerList);
        System.out.println(list);
        System.out.println(list);
        list.remove(Integer.valueOf(0));
        System.out.println(list);
        System.out.println(list.size());
    }
}
