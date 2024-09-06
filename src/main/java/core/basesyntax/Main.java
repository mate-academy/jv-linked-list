package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> linked = new MyLinkedList<>();

        linked.add("Yes");
        linked.add("No");
        linked.add("I don't no");

        System.out.println(linked.get(1));

        System.out.println(linked.remove("Noo"));
    }
}
