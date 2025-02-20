package core.basesyntax;

public class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    public Node(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}
