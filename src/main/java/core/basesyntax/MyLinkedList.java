package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> header;
    private int size;

    public MyLinkedList() {
        header = new Node<>(null, null, null);
        header.next = header.prev = header;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        add(value, size);
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> entryPoint = index == size ? header : getNode(index);
        Node<T> element = new Node<>(value,entryPoint, entryPoint.prev);
        element.prev.next = element.next.prev = element;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element, size);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = getNode(index);
        T prevValue = element.value;
        element.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> element = getNode(index);
        element.prev.next = element.next;
        element.next.prev = element.prev;
        element.prev = element.next = null;
        T value = element.value;
        element.value = null;
        size--;
        return value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> element = header.next;
        for (int i = 0; i < size; i++) {
            if (t == element.value || t != null && t.equals(element.value)) {
                remove(i);
                return true;
            }
            element = element.next;
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

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Wrong index!");
        }
        Node<T> element = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                element = element.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}

