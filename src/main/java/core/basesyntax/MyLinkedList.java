package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (size == 0) {
            head = current;
        } else {
            tail.next = current;
            current.prev = tail;
        }
        tail = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        verifyIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (head == currentNode) {
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
            }
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
        verifyIndexEqualsSize(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        verifyIndex(index);
        verifyIndexEqualsSize(index);

        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode.next);

        if (head == currentNode) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.next.prev = newNode;

        return currentNode.value;
    }

    @Override
    public T remove(int index) {
        verifyIndexEqualsSize(index);
        Node<T> node = getNodeByIndex(index);
        T result = node.value;
        unlink(node);
        return result;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> node = getNodeByIndex(i);
            if (object == node.value
                    || object != null && object.equals(node.value)) {
                unlink(node);
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

    private Node<T> getNodeByIndex(int index) {
        verifyIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }
        size--;
    }

    private void verifyIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("invalid index");
        }
    }

    private void verifyIndexEqualsSize(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("invalid index");
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private final T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
