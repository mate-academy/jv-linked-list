package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    @Override
    public void add(T value) {
        Node<T> insertNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = insertNode;
            size++;
            return;
        }
        tail.next = insertNode;
        tail = insertNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of bounds, Size: " + size);
        }
        if (isEmpty() || index == size) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        Node<T> insertNode = new Node<>(node.prev, value, node);
        if (index == 0) {
            head = insertNode;
            size++;
            return;
        }
        node.prev.next = insertNode;
        node.prev = insertNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T changedItem = node.item;
        node.item = value;
        return changedItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        int counter;
        if (index > size / 2) {
            counter = size - 1;
            node = tail;
            while (index < counter) {
                node = node.prev;
                counter--;
            }
        } else {
            counter = 0;
            node = head;
            while (counter < index) {
                node = node.next;
                counter++;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        if (node.prev == null) {
            if (size == 1) {
                size--;
                return node.item;
            }
            node.next.prev = null;
            head = node.next;
            size--;
            return node.item;
        }
        if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
            size--;
            return node.item;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.item;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of bounds, Size: " + size);
        }
    }
}
