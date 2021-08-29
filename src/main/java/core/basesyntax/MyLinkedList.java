package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> entering;
    private int size;

    public MyLinkedList() {
        entering = new Node<>(null, null, null);
        entering.next = entering.previous = entering;
        size = 0;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        Node<T> tail = index == size ? entering : getNode(index);
        Node<T> element = new Node<>(value, tail, tail.previous);
        element.previous.next = element.next.previous = element;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element, size);
        }
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
        element.previous.next = element.next;
        element.next.previous = element.previous;
        element.previous = element.next = null;
        T value = element.value;
        element.value = null;
        size--;
        return value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> element = entering.next;
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
        Objects.checkIndex(index, size);
        Node<T> element = entering;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                element = element.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                element = element.previous;
            }
        }
        return element;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        private Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
