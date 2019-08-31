package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> wasLast = last;
        Node<T> newNode = new Node<>(wasLast, value, null);
        last = newNode;
        if (wasLast == null) {
            first = newNode;
        } else {
            wasLast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            insertNode(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T temp : list) {
            add(temp);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public void set(T value, int index) {
        checkElementIndex(index);
        getNode(index).item = value;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return removeNode(getNode(index));
    }

    @Override
    public T remove(T value) {
        for (Node<T> node = first; node != null; node = node.next) {
            if ((value == null && node.item == null) || node.item.equals(value)) {
                return removeNode(node);
            }
        }
        return null;
    }

    private void insertNode(T element, Node<T> currentNode) {
        Node<T> nodeBeforeCurrent = currentNode.prev;
        Node<T> newNode = new Node<>(nodeBeforeCurrent, element, currentNode);
        currentNode.prev = newNode;
        if (nodeBeforeCurrent == null) {
            first = newNode;
        } else {
            nodeBeforeCurrent.next = newNode;
        }
        size++;
    }

    private T removeNode(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be in bounds from 0 to " + (size - 1));
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be in bounds from 0 to " + (size));
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
