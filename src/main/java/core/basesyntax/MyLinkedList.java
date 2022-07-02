package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    @Override
    public void add(T value) {
    }

    @Override
    public void add(T value, int index) {
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
