package core.basesyntax;

public class main {
    public static void main(String[] args) {
        MyLinkedList<Integer> list =
                new MyLinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list.get(1));
        System.out.println(list.get(1));
        list.set(7,1);
        System.out.println(list.get(1));
    }
}
