package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T element, Node<T> next, Node<T> prev) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> node = null;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(value, null, null);
            tail = head;
            size++;
            return;
        }
        tail = new Node<>(value, null, tail);
        tail.prev.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(value, head, null);
            head.next.prev = head;
            size++;
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(value, node, node.prev);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public void set(T value, int index) {
        getNode(index).value = value;
    }

    @Override
    public T remove(int index) {
        return remove(getNode(index).value);
    }

    @Override
    public T remove(T t) {
        T removed = null;

        if (Objects.equals(t, head.value)) {
            removed = head.value;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            size--;
            return removed;
        }
        if (Objects.equals(t, tail.value)) {
            removed = tail.value;
            tail = tail.prev;
            tail.next = null;
            size--;
            return removed;
        }
        Node<T> node = head;
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(t, node.value)) {
                removed = node.value;
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node = null;
                size--;
                break;
            }
            node = node.next;
        }
        return removed;
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
