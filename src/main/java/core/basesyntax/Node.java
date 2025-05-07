package core.basesyntax;

public class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> prev;

    Node(Node<T> prev, T element, Node<T> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
