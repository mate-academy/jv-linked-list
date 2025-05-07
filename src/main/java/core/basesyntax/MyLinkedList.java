package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }

    }

    @Override
    public void addAll(List<T> list) {

        int numNew = 0;

        Node<T> pred = last;
        Node<T> succ = null;

        for (T e : list) {
            add(e);

            Node<T> newNode = new Node<>(pred, e, null);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> x = node(index);
        T outVal = x.item;
        x.item = value;
        return outVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        final Node<T> x = node(index);
        return unlink(x);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if ((object == null && x.item == null) || (object != null && object.equals(x.item))) {
                unlink(x);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(T e) {
        final Node<T> linkLast = last;
        final Node<T> newNode = new Node<>(linkLast, e, null);
        last = newNode;
        if (linkLast == null) {
            first = newNode;
        } else {
            linkLast.next = newNode;
        }
        size++;
    }

    private void linkBefore(T e, Node<T> succ) {

        final Node<T> pred = succ.prev;
        final Node<T> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;

    }

    private T unlink(Node<T> n) {
        final T x = n.item;
        final Node<T> nextX = n.next;
        final Node<T> prevX = n.prev;

        if (prevX == null) {
            first = nextX;
        } else {
            prevX.next = nextX;
        }

        if (nextX == null) {
            last = prevX;
        } else {
            nextX.prev = prevX;
        }

        size--;
        return x;
    }

    private boolean isElementIndexValid(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndexValid(index)) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private boolean isPositionIndexValid(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndexValid(index)) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;

            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
}
