package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = getIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode == null) {
                currentNode.prev = head;
                head = newNode;
                size++;
            }
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
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
        checkIndex(index);
        Node<T> node = getIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getIndex(index);

        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (index == 0) {
            head = node.next;
        } else if (index == size - 1) {
            tail = node.prev;
        }
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object || (node.value != null && node.value.equals(object))) {
                removeNode(node);
                return true;
            }
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

    private Node<T> getIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    public void removeNode(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = tail = null;
        } else if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
