package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> currentTailNode = tail;
        Node<T> newNode = new Node<>(currentTailNode, value, null);
        tail = newNode;
        if (size == 0) {
            head = newNode;
        } else {
            currentTailNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T input : list) {
            add(input);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexCorrectness(index);
        Node<T> currentNode = getNode(index);
        T result = currentNode.value;
        currentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndexCorrectness(index);
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> checkNode = head;
        while (checkNode != null) {
            if (checkNode.value == object || object != null && object.equals(checkNode.value)) {
                unlink(checkNode);
                return true;
            }
            checkNode = checkNode.next;
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

    private void checkIndexCorrectness(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is incorrect input index");
        }
    }

    private Node<T> getNode(int index) {
        checkIndexCorrectness(index);
        Node<T> checkNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return checkNode;
            }
            checkNode = checkNode.next;
        }
        return checkNode;
    }

    private void unlink(Node<T> current) {
        if (current == head) {
            head = current.next;
        } else if (current == tail) {
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
            current.prev.next = current.next;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
