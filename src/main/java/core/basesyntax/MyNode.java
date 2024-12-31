package core.basesyntax;

public class MyNode<T> {
    private T value;
    private MyNode<T> prev;
    private MyNode<T> next;

    MyNode(T x) {
        value = x;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public MyNode getPrev() {
        return prev;
    }

    public void setPrev(MyNode prev) {
        this.prev = prev;
    }

    public MyNode getNext() {
        return next;
    }

    public void setNext(MyNode next) {
        this.next = next;
    }
}

