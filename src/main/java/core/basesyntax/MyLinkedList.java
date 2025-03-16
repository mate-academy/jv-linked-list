package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(head, null, null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail.prev, value, tail);
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 && index == 0) {
            add(value);
        } else if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        } else {
            Node<T> node = head.next;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev = newNode;
            newNode.prev.next = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head.next;
        for (int i = 0; i < size; i++) {
            if ((object == null && node.value == null)
                    || (object != null && object.equals(node.value))) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> current;
        if (index < size / 2) {
            current = head.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail.prev;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
