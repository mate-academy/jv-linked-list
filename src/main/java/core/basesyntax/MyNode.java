package core.basesyntax;

public class MyNode<T> {
    T item;
    MyNode<T> next;
    MyNode<T> prev;

    MyNode(MyNode<T> prev, T value, MyNode<T> next) {
        this.item = value;
        this.next = next;
        this.prev = prev;
    }
}
