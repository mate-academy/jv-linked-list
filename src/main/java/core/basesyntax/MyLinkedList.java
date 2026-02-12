package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
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
            linkBefore(value,node(index));
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T comp : list) {
            add(comp);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return (T) node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> rest = node(index);
        T res = rest.value;
        rest.value = value;
        return res;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return (T) unlink(node(index));

    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if (object == null) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            } else {
                if (object.equals(x.value)) {
                    unlink(x);
                    return true;
                }
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

    private T unlink(Node<T> x) {
        final T element = x.value;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

        x.value = null;
        x.next = null;
        x.prev = null;

        size--;
        return element;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    void linkLast(T e) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;

    }

    void linkBefore(T e, Node<T> succ) {
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

    public Node<T> node(int index) {
        if (index < size / 2) {
            Node<T> current = first;
            for (int i = 0;i < index;i++) {
                current = current.next;
            }
            return current;

        } else {
            Node<T> current = last;
            for (int i = size - 1;i > index;i--) {
                current = current.prev;
            }
            return current;

        }

    }

    private class Node<T> {

        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}


