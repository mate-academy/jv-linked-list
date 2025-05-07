package core.basesyntax;

public class Node<T> {
    protected T item;
    protected Node<T> next;
    protected Node<T> prev;

    Node(Node<T> prev, T element, Node<T> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
