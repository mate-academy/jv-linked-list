package core.basesyntax;

class Node<T> {
    private Node next;
    private Node prev;

    private T value;

    Node(T x) {
        value = x;
        this.next = null;
        this.prev = null;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public T getValue() {
        return value;
    }
}
