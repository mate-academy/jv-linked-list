package core.basesyntax;

public class Node<T> {

    private Node<T> prev;
    private T value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
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
