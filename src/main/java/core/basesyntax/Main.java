package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        String first = "First";
        String second = "Second";
        String third = "Third";
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add(first);
        list.add(second);
        list.add(third);
        list.add(null);
        list.add(third);
        list.add(null);
    }
}
