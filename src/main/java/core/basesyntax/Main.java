package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("xxx");
        list.add(null);
        list.add("ooo");
        list.add("bbb");
        list.add(null);
        list.remove(null);
        list.remove(null);

        System.out.println(list.size());
        System.out.println(list);

    }
}
