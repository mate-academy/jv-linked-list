package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        size++;
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index);
        Node<T> currentNode = findNode(index);
        Node<T> node = new Node<>(currentNode.prev, value, currentNode);
        if (index == 0) {
            currentNode.prev = node;
            head = node;
            size++;
            return;
        }
        currentNode.prev.next = node;
        currentNode.prev = node;
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
        validateIndex(index);
        Node<T> currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> currentNode = findNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> currentNode = findNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == null && object == null
                    || currentNode != null && currentNode.value.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node == head) {
            head = nextNode;
            nextNode.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            tail = prevNode;
            prevNode.next = null;
            size--;
            return;
        }
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is no such index [" + index + "]");
        }
    }

    private Node<T> findNode(int index) {
        if (index < size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        Node<T> currentNode = tail;
        for (int i = 0; i < size - index - 1; i++) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }
}
