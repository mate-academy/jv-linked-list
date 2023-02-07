package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_SIZE = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size = DEFAULT_SIZE;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> prevNode = findByIndex(index);
            Node<T> newNode = new Node<>(prevNode.prev, value, prevNode);
            if (prevNode != head) {
                prevNode.prev.next = newNode;
            } else {
                head = newNode;
            }
            prevNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findByIndex(index);
        T result = node.item;
        node.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Element with index " + index + " not exist!");
        }
        Node<T> result;
        if (size / 2 > index) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            return result;
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
