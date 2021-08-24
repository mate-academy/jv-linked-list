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

        private Node() {
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = tail = new Node<>(null, value, null);
            size++;
            return;
        }
        Node<T> insertNode = new Node<>(tail, value, null);
        tail.next = insertNode;
        tail = insertNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            if (isEmpty()) {
                add(value);
                return;
            }
            Node<T> insertNode = new Node<>(null, value, head);
            head = insertNode;
            head.prev = insertNode;
            size++;
            return;
        }
        if (index == size) {
            Node<T> insertNode = new Node<>(tail, value, null);
            tail.next = insertNode;
            tail = insertNode;
            size++;
            return;
        }
        Node<T> node = iterate(index);
        Node<T> insertNode = new Node<>(node.prev, value, node);
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> node = iterate(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> node = iterate(index);
        T changedItem = node.item;
        node.item = value;
        return changedItem;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> node = iterate(index);
        node = unlink(node, index);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                unlink(node,i);
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

    private Node<T> iterate(int index) {
        Node<T> node;
        int middleIndex = size / 2;
        if (index > middleIndex) {
            int counter = size - 1;
            node = tail;
            while (index < counter) {
                node = node.prev;
                counter--;
            }
            return node;
        }
        if (index <= middleIndex) {
            int counter = 0;
            node = head;
            while (counter < index) {
                node = node.next;
                counter++;
            }
            return node;
        }
        return null;
    }

    private Node<T> unlink(Node<T> node, int index) {
        if (index == 0) {
            if (size == 1) {
                size--;
                return new Node<>();
            }
            node.next.prev = null;
            head = node.next;
            size--;
            return node;
        }
        if (index == size - 1) {
            node.prev.next = null;
            tail = node.prev;
            size--;
            return node;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node;
    }
}
