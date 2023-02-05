package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;
    private Node<T> element;


    public MyLinkedList() {
    }

    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> firstNode = new Node<>(null, value, null);
            first = firstNode;
            element = firstNode;
            last = firstNode;
            size++;
            return;
        }
        if (size == 1) {
            Node<T> newNode = new Node<>(first, value, null);
            element.next = newNode;
            element = newNode;
            last = newNode;
            last.prev = element;
            size++;
            return;

        }
        if (size > 1) {
            Node<T> newNode = new Node<>(element, value, null);
            element.next = newNode;
            element = newNode;
            last = newNode;
            last.prev = element;
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
        if (index == 0) {
            return first.value;
        }
        Node<T> target = first;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target.value;
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
}
