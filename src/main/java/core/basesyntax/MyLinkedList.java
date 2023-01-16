package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

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
        if (tail == currentNode) {
            tail = currentNode.prev;
        } else if (head == currentNode) {
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> checkNode = head;
        while (checkNode != null) {
            if (checkNode.value == object || object != null && object.equals(checkNode.value)) {

                if (checkNode == tail) {
                    tail = checkNode.prev;
                } else if (checkNode == head) {
                    head = checkNode.next;
                } else {
                    checkNode.next.prev = checkNode.prev;
                    checkNode.prev.next = checkNode.next;
                }
                size--;
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
        try {
            Node<T> checkNode = head;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return checkNode;
                }
                checkNode = checkNode.next;
            }
        } catch (RuntimeException e) {
            throw new NoSuchElementException("Value was not found with index - " + index, e);
        }
        return null;
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
