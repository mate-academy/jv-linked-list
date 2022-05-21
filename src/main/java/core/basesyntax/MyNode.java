package core.basesyntax;

public class MyNode<T> {
    protected T item;
    protected MyNode<T> next;
    protected MyNode<T> prev;

    MyNode(T element) {
        this.item = element;
    }
}
