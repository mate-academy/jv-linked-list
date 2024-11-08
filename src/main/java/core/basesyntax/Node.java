package core.basesyntax;

public class Node<T> {
    public T item;
    public Node<T> prev;
    public Node<T> next;

    public Node(T item, Node<T> prev, Node<T> next) {
        this.item = item;
        this.prev = prev;
        this.next = next;
    }
}

