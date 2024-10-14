package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> node;
    private int size;
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        node = new Node<>(null, value,null);
        if (head == null) {
            head = tail = node;
            size++;
        } else {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        throwIndexOutOfBoundsAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = findNodeByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev = (current.prev != null) ? current.prev.next = newNode : (head = newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            node = new Node<>(null, list.get(i), null);
            tail.next = node;
            node.prev = tail;
            tail = node;
            size++;
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = findNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        throwIndexOutOfBounds(index);
        node = new Node<>(value);
        Node<T> current = findNodeByIndex(index);
        if (index == 0) {
            current.next.prev = node;
            node.prev = null;
            node.next = current.next;
            head = node;
        } else {
            node.next = current.next;
            node.prev = current.prev;
            current.prev.next = node;
            current.next.prev = node;
        }
        return current.item;
    }

    @Override
    public T remove(int index) {
        throwIndexOutOfBounds(index);
        Node<T> current = findNodeByIndex(index);
        if (current.equals(head)) {
            head = head.next;
        } else {
            current.prev.next = current.next;
        }
        if (current.equals(tail)) {
            tail = tail.prev;
        } else {
            current.next.prev = current.prev;
        }
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object || (current.item != null && current.item.equals(object))) {
                if (current == head) {
                    head = current.next;
                }
                if (current == tail) {
                    tail = current.prev;
                }
                if (current.prev != null) {
                    current.prev.next = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
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
        private final T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        public Node(T item) {
            this.item = item;
        }
    }

    private void throwIndexOutOfBoundsAdd(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    private void throwIndexOutOfBounds(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        throwIndexOutOfBounds(index);
        Node<T> node;
        node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
