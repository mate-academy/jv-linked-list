package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public int size = 0;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> toAdd = new Node<>(value, null, tail);
        if (size == 0) {
            head = toAdd;
        } else {
            tail.next = toAdd;
        }
        tail = toAdd;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> now = indexOf(index);
            Node<T> toAdd = new Node<>(value, now, now.previous);
            now.previous.next = toAdd;
            now.previous = toAdd;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return indexOf(index).value;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        indexOf(index).value = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            size--;
            return removeHead();
        }
        if (index == size - 1) {
            size--;
            return removeTail();
        }
        Node<T> toRemove = indexOf(index);
        toRemove.previous.next = toRemove.next;
        toRemove.next.previous = toRemove.previous;
        size--;
        return toRemove.value;
    }

    @Override
    public T remove(T t) {
        Node<T> lookFor = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(t, lookFor.value)) {
                return remove(i);
            }
            lookFor = lookFor.next;
        }
        return null;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> indexOf(int index) {
        checkIndex(index);
        Node<T> toFind = head;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                toFind = toFind.next;
            }
        } else {
            toFind = tail;
            for (int i = size - 1; i > index; i--) {
                toFind = toFind.previous;
            }
        }
        return toFind;
    }

    private T removeHead() {
        T value = head.value;
        head = head.next;
        return value;
    }

    private T removeTail() {
        T value = tail.value;
        tail = tail.previous;
        return value;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> previous;

        Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
