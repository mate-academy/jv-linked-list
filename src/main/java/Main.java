import core.basesyntax.MyLinkedList;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> firstLl = new ArrayList<>();

        firstLl.add(1);
        firstLl.add(2);

        MyLinkedList<Integer> ll = new MyLinkedList<>();

        ll.addAll(firstLl);

        ll.add(0,0);

        ll.remove(Integer.valueOf(0));
        ll.remove(Integer.valueOf(1));
    }
}
