package core.basesyntax;

public class Node<T> {
    private Node<T> prev;
    private T value;
    private Node<T> next;

    public Node(Node<T> prev, T value, Node<T> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setPrev(Node<T> node) {
        this.prev = node;
    }

    public void setNext(Node<T> node) {
        this.next = node;
    }
}
