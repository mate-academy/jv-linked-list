package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {

        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> x = node(index);
            Node<T> prev = x.prev;
            Node<T> newNode = new Node(prev, value, x);
            x.prev = newNode;
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }

    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> x = node(index);
        Node<T> prev = x.prev;
        Node<T> next = x.next;
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
        size--;
        return (T) x.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> x = first;
        for (int i = 0; i < size; i++) {
            if (t == x.item || t != null && t.equals((x.item))) {
                remove(i);
                return true;
            }
            x = x.next;
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

    private void checkIndexAdd(int index) {
        if (index >= 0 && index <= size) {
            return;
        } else {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is wrong!");
        }
    }

    private void checkIndex(int index) {
        if (index >= 0 && index < size) {
            return;
        } else {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is wrong!");
        }
    }

    private Node<T> node(int index) {
        Node<T> x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }
}
