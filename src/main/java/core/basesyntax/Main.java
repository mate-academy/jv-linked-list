package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyLinkedList <Integer> list = new MyLinkedList<>();
        List <Integer> myList = new ArrayList<>();


        for (int i = 1; i <= 5; i++){
            list.add(i);
            myList.add(i * 10);
        }
        list.addAll(myList);


        //list.add(777, 1);



        list.set(22,9);
        //list.remove(Integer.valueOf(4));
        //list.remove(9);

       //System.out.println("Get: " + list.get(8));
        System.out.println("Size: " + list.size());

        list.printDebug();
    }
}
