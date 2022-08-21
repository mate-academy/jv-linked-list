package core.basesyntax;

public class Node<T> {
    protected T value;
    protected Node<T> prev;
    protected Node<T> next;

    public Node(Node<T> prev, T value, Node<T> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}
