package core.basesyntax;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("1", 0);
        list.add("2", 1);
        list.add("3");

        LinkedList<String> likedList = new LinkedList<>();
        likedList.add("4");
        likedList.add("5");
        likedList.add("6");

        list.addAll(likedList);

        for (int i = 0; i < list.size(); i++) {
            System.out.println("before " + i + " - " + list.get(i));
        }
        System.out.println("Size - " + list.size());
        System.out.println("isEmpty = " + list.isEmpty());
        System.out.println("");

        list.remove(-1);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("Size - " + list.size());
        System.out.println("isEmpty = " + list.isEmpty());

    }
}
