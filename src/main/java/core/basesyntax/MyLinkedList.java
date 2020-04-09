package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> header;

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
        Node<T> entryPoint = index == size ? header : getNodeByIndex(index);
        Node<T> element = new Node<>(value, entryPoint, entryPoint.prev);
        element.prev.next = element.next.prev = element;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T one : list) {
            add(one);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return (T) getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setOne = getNodeByIndex(index);
        T oldValue = setOne.value;
        setOne.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removed = getNodeByIndex(index);
        removed.prev.next = removed.next;
        removed.next.prev = removed.prev;
        size--;
        return removed.value;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            Node<T> ckecked = getNodeByIndex(i);
            if (ckecked.value == null && t == null
                    || ckecked.value != null && ckecked.value.equals(t)) {
                remove(i);
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
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> element = header;
        for (int i = 0; i <= index; i++) {
            element = element.next;
        }
        return element;
    }
}
