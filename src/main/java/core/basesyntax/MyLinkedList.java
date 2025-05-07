package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ARRAYS_ZERO_BORDER = 0;
    private static final String INDEX_OUT_OF_BOUNDS = "Index out of bounds array size";
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        if (index > size || index < ARRAYS_ZERO_BORDER) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
        if (size == 0 || index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
            size++;
        } else {
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
        if (currentNode.prev == null) {
            head = currentNode.next;
        } else if (currentNode.next == null) {
            tail = currentNode.prev;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((findCurrentNode(i).value == object)
                    || (object != null && object.equals(findCurrentNode(i).value))) {
                remove(i);
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

    private void checkIndex(int index) {
        if ((index >= size && index != ARRAYS_ZERO_BORDER) || index < ARRAYS_ZERO_BORDER) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
    }

    private Node<T> findCurrentNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private static class Node<T> {
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
