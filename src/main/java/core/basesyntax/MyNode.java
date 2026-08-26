package core.basesyntax;

public class MyNode<T> {
    private MyNode<T> prev;
    private MyNode<T> next;
    private T value;

    public MyNode(MyNode<T> prev, MyNode<T> next, T value) {
        this.prev = prev;
        this.next = next;
        this.value = value;
    }

    public MyNode<T> getPrev() {
        return prev;
    }

    public void setPrev(MyNode<T> prev) {
        this.prev = prev;
    }

    public MyNode<T> getNext() {
        return next;
    }

    public void setNext(MyNode<T> next) {
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
