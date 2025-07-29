package core.basesyntax;


public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList();

        myLinkedList.add(5);
        myLinkedList.add(2);
        myLinkedList.add(1);
        myLinkedList.set(0,0);

        myLinkedList.remove(0);

        System.out.println(myLinkedList.size());

    }
}
