package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        list.add(10);
        list.addFirst(5);
        list.addLast(20);
        System.out.println("List: " + list);  // Expected output: 5 <-> 10 <-> 20 <-> null

        list.remove(10);
        System.out.println("After removing 10: " + list);  // Expected output: 5 <-> 20 <-> null

        System.out.println("First: " + list.getFirst());  // Expected output: 5
        System.out.println("Last: " + list.getLast());    // Expected output: 20
        System.out.println("Size: " + list.size());       // Expected output: 2
    }
}