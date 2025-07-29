package core.basesyntax;


public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList();

        myLinkedList.add(5);
        myLinkedList.add(2);
        myLinkedList.add(1);
        myLinkedList.add(7, 1);
        myLinkedList.set(0,0);

        System.out.println(myLinkedList.size());
        System.out.println(myLinkedList.get(1));
        System.out.println(myLinkedList.get(0));
    }
}
