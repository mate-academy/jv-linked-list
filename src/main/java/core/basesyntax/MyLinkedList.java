package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> nextNode = tail;
        final Node<T> newNode = new Node<>(nextNode, value, null);
        tail = newNode;
        if (nextNode == null) {
            head = newNode;
        } else {
            nextNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element :list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> swapNode = node(index);
        T oldVal = swapNode.item;
        swapNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unLink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.item == null) {
                    unLink(currentNode);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.item)) {
                    unLink(currentNode);
                    return true;
                }
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

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    void linkBefore(T element, Node<T> currentNode) {
        final Node<T> before = currentNode.prev;
        final Node<T> newNode = new Node<>(before, element, currentNode);
        currentNode.prev = newNode;
        if (before == null) {
            head = newNode;
        } else {
            before.next = newNode;
        }
        size++;
    }

    Node<T> node(int index) {
        Node<T> currentNode;
        if (index < (size >> 1)) {
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

    T unLink(Node<T> currentNode) {
        final T element = currentNode.item;
        final Node<T> prev = currentNode.prev;
        final Node<T> next = currentNode.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            currentNode.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            currentNode.next = null;
        }
        currentNode.item = null;
        size--;
        return element;
    }
}
