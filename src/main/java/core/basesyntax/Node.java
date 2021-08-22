package core.basesyntax;

public class Node<T> {
    private  Node<T> previous;
    private  T value;
    private Node<T> next;

    public Node(Node<T> previous, T value, Node<T> next){
        this.previous = previous;
        this.value = value;
        this.next = next;
    }
}
