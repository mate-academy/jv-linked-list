package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value);
        if (isEmpty()) {
            head = node;
            tail = head;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && !isEmpty()) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            insert(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> removedNode = head; removedNode != null; removedNode = removedNode.next) {
            if (areValuesEqual(removedNode.value, object)) {
                unlink(removedNode);
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

    private void addFirst(T value) {
        Node<T> insertNode = new Node<>(value, head);
        head.prev = insertNode;
        head = insertNode;
        size++;
    }

    private void insert(T value, int index) {
        Node<T> oldNode = getNode(index);
        Node<T> insertNode = new Node<>(oldNode.prev, value, oldNode);
        oldNode.prev.next = insertNode;
        oldNode.prev = insertNode;
        size++;
    }

    private T unlink(Node<T> removedNode) {
        final T element = removedNode.value;
        final Node<T> next = removedNode.next;
        final Node<T> prev = removedNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removedNode.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            removedNode.next = null;
        }

        removedNode.value = null;
        size--;
        return element;
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
    }

    private Node<T> getNode(int index) {
        checkRange(index);
        return (index <= (size >> 1)) ? searchFromHead(index) : searchFromTail(index);
    }

    private Node<T> searchFromHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; ++i) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; --i) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private boolean areValuesEqual(T first, T second) {
        return first == second || first != null && first.equals(second);
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        private Node(Node<T> prev, T value) {
            this.prev = prev;
            this.value = value;
        }

        private Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
