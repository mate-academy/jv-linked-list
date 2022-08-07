package core.basesyntax;

public class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    public Node( Node<T> prev, T value, Node<T> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }
}
