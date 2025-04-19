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

    public Node(T value) {
        this.value = value;
        this.prev = null;
        this.next = null;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
