package core.basesyntax;

class Node<E> {
    E value;
    Node<E> next;
    Node<E> prev;

    Node(E value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}
