package core.basesyntax;

public class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> prev;

    public Node(Node<T> next, T inem, Node<T> prev) {
        this.next = next;
        this.item = inem;
        this.prev = prev;
    }

    public T getItemIndex() {
        return item;
    }

    public Node<T> setItem(T item) {
        this.item = item;
        return this;
    }

    public Node<T> getNextElement() {
        return next;
    }

    public Node<T> setNextElement(Node<T> next) {
        this.next = next;
        return this;
    }

    public Node<T> getPrevElement() {
        return prev;
    }

    public Node<T> setPrevElement(Node<T> prev) {
        this.prev = prev;
        return this;
    }
}
