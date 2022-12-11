package core.basesyntax;

public class Node<T> {
     T value;
     Node<T> prev;
     Node<T> next;

    public Node(Node<T> prev, T value, Node<T> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }
}
