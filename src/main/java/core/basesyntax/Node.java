package core.basesyntax;

public class Node<T> {
    private Node<T> nextElement;
    private Node<T> prevElement;
    private T item;

    public Node(Node<T> nextElement, Node<T> prevElement, T item) {
        this.nextElement = nextElement;
        this.prevElement = prevElement;
        this.item = item;
    }

    public Node<T> getNextElement() {
        return nextElement;
    }

    public Node<T> setNextElement(Node<T> nextElement) {
        this.nextElement = nextElement;
        return this;
    }

    public Node<T> getPrevElement() {
        return prevElement;
    }

    public Node<T> setPrevElement(Node<T> prevElement) {
        this.prevElement = prevElement;
        return this;
    }

    public T getItem() {
        return item;
    }

    public Node<T> setItem(T item) {
        this.item = item;
        return this;
    }
}




