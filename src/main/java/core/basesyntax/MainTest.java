package core.basesyntax;

public class MainTest {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(2);
        list.add(3);
        list.add(7);
        list.add(5);
        System.out.println(list.get(3));
        System.out.println(list);
    }
}
