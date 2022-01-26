package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size = 0;
    Node<T> first;
    Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value,null);
        if (size == 0) {
            first = newNode;
            last = newNode;
            size++;
        } else {
            last = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> value = first;
        for (int i = 0; i <= index; i++) {
            value = value.next;
        }
        if (value.item == null) {
            return null;
        }
        return value.item;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index is out of the bound");
        }
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
