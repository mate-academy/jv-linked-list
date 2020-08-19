package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> header;
    private int size;

    public MyLinkedList() {
        header = new Node<>(null, null, null);
        header.next = header;
        header.prev = header;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        add(value, size);
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> entryPoint = index == size ? header : getNodeByIndex(index);
        Node<T> element = new Node<>(value, entryPoint, entryPoint.prev);
        element.prev.next = element;
        element.next.prev = element;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = getNodeByIndex(index);
        T prevValue = element.item;
        element.item = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> element = getNodeByIndex(index);
        T value = unlink(element);
        return value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> element = header.next;
        for (int i = 0; i < size; i++) {
            if (t == element.item || t != null && t.equals(element.item)) {
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

    private T unlink(Node<T> nodeToRemove) {
        nodeToRemove.prev.next = nodeToRemove.next;
        nodeToRemove.next.prev = nodeToRemove.prev;
        nodeToRemove.next = null;
        nodeToRemove.prev = null;
        T value = nodeToRemove.item;
        nodeToRemove.item = null;
        size--;
        return value;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Wrong index!");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
