package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> toAdd = new Node<>(null, value, null);
        tail.next = toAdd;
        toAdd.prev = tail;
        tail = toAdd;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> next = findNode(index);
        Node<T> previous = next.prev;
        Node<T> current = new Node<>(previous, value, next);
        if (previous == null) {
            head = current;
        } else {
            previous.next = current;
        }
        next.prev = current;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        return findNode(index).value;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        findNode(index).value = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        Node<T> toRemove = findNode(index);
        Node<T> previous = toRemove.prev;
        Node<T> next = toRemove.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
        }
        size--;
        return toRemove.value;
    }

    @Override
    public T remove(T t) {
        Node<T> toRemove = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(t, toRemove.value)) {
                return remove(i);
            }
            toRemove = toRemove.next;
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

    private Node<T> findNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        Node<T> toFind;
        if (index < size / 2) {
            toFind = head;
            for (int i = 0; i < index; i++) {
                toFind = toFind.next;
            }
        } else {
            toFind = tail;
            for (int i = size - 1; i > index; i--) {
                toFind = toFind.prev;
            }
        }
        return toFind;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
