package core.basesyntax;

public class MyLinkedListApp {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(34);
        list.add(120);
        list.add(-10);
        list.add(11);
        list.add(50);
        list.add(100);
        list.add(99);

        System.out.println(list.get(2)); // -10
        System.out.println(list.get(5)); // 100
        System.out.println("Remove obj index = " + list.remove(2));
        System.out.println(list.get(2)); // 11
    }
}
