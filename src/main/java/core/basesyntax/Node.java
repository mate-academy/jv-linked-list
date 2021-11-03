package core.basesyntax;

class Node<T> {
    protected T value;
    protected Node<T> prev;
    protected Node<T> next;

    Node(Node<T> prev, T value, Node<T> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }
}
