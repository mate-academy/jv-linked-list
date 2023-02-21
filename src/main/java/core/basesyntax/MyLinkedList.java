package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> last;
    private Node<T> first;
    private int size;

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> l = last;
        Node<T> node = new Node<>(l, value, null);
        last = node;
        if (l == null) {
            first = node;
        } else {
            l.next = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index >= size + 1) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> x = getNodeByIndex(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if (x.item == object || (x.item != null && x.item.equals(object))) {
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
        return first == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> x;
        if (index < size / 2) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private T unlink(Node<T> x) {
        final T element = x.item;
        Node<T> next = x.next;
        Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
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
}
