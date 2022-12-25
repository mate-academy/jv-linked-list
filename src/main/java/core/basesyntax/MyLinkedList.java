package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_SIZE = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == DEFAULT_SIZE) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < DEFAULT_SIZE || index > size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException: index "
                    + index + " with size " + size);
        }
        if (size == index) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currentNode = getNodeByIndex(index);
            if (index == DEFAULT_SIZE) {
                newNode.next = currentNode;
                currentNode.prev = newNode;
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
                newNode.prev = currentNode.prev;
                newNode.next = currentNode;
                currentNode.prev = newNode;
            }
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlinkNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = DEFAULT_SIZE; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                unlinkNode(currentNode);
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
        return size == DEFAULT_SIZE;
    }

    private T unlinkNode(Node<T> currentNode) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
        } else if (currentNode.equals(head)) {
            head = currentNode.next;
            currentNode.next.prev = null;
            currentNode.next = null;
            size--;
        } else if (currentNode.equals(tail)) {
            tail = currentNode.prev;
            currentNode.prev.next = null;
            currentNode.prev = null;
            size--;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
            size--;
        }
        return currentNode.value;
    }

    private void checkIndex(int index) {
        if (index < DEFAULT_SIZE || index >= size) {
            throw new IndexOutOfBoundsException("There's no element at index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index > size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = DEFAULT_SIZE; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
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
