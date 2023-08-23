package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedListMain {
    public static void main(String[] args) {
        MyLinkedListInterface<String> strings = new MyLinkedList<>();
        List<String> strings2 = new LinkedList<>();
//        strings2.add();

        strings.add("He");
        strings.add("to");
        strings.add("eat");

        strings.add("bla",0);


//        strings.addAll(strings2);

//        strings.remove("want");
        System.out.print(strings.get(0));
        for (int i = 1; i < strings.size(); i++) {
            System.out.print(" " + strings.get(i));
        }
        System.out.println(System.lineSeparator() + strings.isEmpty());

        System.out.println();
    }
}
