package core.basesyntax;

public class Node<T> {

    private Node prev;
    private T element;
    private Node next;
    private boolean head;
    private boolean tail;

    public Node(Node prev, T element, Node next, boolean head, boolean tail) {
        this.prev = prev;
        this.element = element;
        this.next = next;
        this.head = head;
        this.tail = tail;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    public boolean isTail() {
        return tail;
    }

    public void setTail(boolean tail) {
        this.tail = tail;
    }
}
