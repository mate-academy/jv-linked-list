package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ARRAYS_ZERO_BORDER = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, null, null);
    }

    @Override
    public void add(T value) {
        if (!addFirstNode(value)) {
            tail.prev = tail.prev.next = new Node<>(tail.prev, value, tail);
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < ARRAYS_ZERO_BORDER) {
            throw new IndexOutOfBoundsException("Index out of bounds array size");
        }
        if (!addFirstNode(value)) {
            Node<T> currentNode = findCurrentNode(index);
            currentNode.prev.next = currentNode.prev
                    = new Node<>(currentNode.prev, value, currentNode);
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
        return findCurrentNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findCurrentNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findCurrentNode(index);
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        currentNode.prev = currentNode.next = null;
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        int countIndex = 0;
        Node<T> currentNode = head.next;
        T nodeValue = currentNode.value;
        while (nodeValue != null && !nodeValue.equals(object)) {
            nodeValue = currentNode.next.value;
            currentNode = currentNode.next;
            countIndex++;
        }
        return countIndex < size && ((object != null && object.equals(remove(countIndex))
                || object == remove(countIndex)));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if ((index >= size && index != ARRAYS_ZERO_BORDER) || index < ARRAYS_ZERO_BORDER) {
            throw new IndexOutOfBoundsException("Index out of bounds array size");
        }
    }

    private Node<T> findCurrentNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i <= index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i >= index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean addFirstNode(T value) {
        if (head.next == null && tail.prev == null) {
            head.next = tail.prev = new Node<>(head, value, tail);
            size++;
            return true;
        }
        return false;
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
