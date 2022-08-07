package core.basesyntax;

public class Node<T> {
    private T value;
    Node<T> next;
    Node<T> prev;

    public Node( Node<T> prev, T value, Node<T> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
