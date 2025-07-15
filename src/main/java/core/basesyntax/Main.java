package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MyLinkedList<String> ml = new MyLinkedList<>();
        ml.add("First");
        ml.add("Second");
        ml.add("Third");
        ml.add("Forth");
        ml.add("Fifth");
        ml.add("Sixth");

        ml.add("Other", 2);

        List<String> listString = new ArrayList<>();
        listString.add("White");
        listString.add("BLack");
        listString.add("Red");
        listString.add("Green");

        ml.addAll(listString);

    }

}
