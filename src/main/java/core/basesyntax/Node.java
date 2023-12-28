package core.basesyntax;

public class Node<T> {
    T item;
    Node<T> next;
    Node<T> prev;

    Node(Node<T> prev, T item, Node<T> next) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }
}
