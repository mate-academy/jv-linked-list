package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> stringLL = new MyLinkedList<>();

        stringLL.add("HELLO");
        stringLL.add("MATES");
        stringLL.add("LLLLL");

        stringLL.add("Great", 2);
        stringLL.add("RRRR", 1);

        System.out.println(stringLL.get(4));
    }
}
