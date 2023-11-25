package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else {
            linkHead(newNode, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T prevValue = node.item;
        node.item = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        return removeNodeByValue(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        int count = 0;
        Node<T> currentNode = head;
        while (count++ != index) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private boolean removeNodeByValue(T value) {
        Node<T> currentNode = head;
        boolean result = false;
        int count = 0;
        while (count++ != size) {
            if (currentNode.item != null) {
                if (!currentNode.item.equals(value)) {
                    currentNode = currentNode.next;
                } else {
                    return unlink(currentNode);
                }
            } else if (value == null) {
                return unlink(currentNode);
            } else {
                currentNode = currentNode.next;
            }
        }
        return result;
    }

    private boolean unlink(Node<T> node) {
        if (node == null) {
            return true;
        }
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            Node<T> previous = node.prev;
            Node<T> next = node.next;
            previous.next = next;
            next.prev = previous;
            node.prev = null;
            node.next = null;
        }
        size--;
        return true;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        }
        if (size >= 1) {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void linkHead(Node<T> newNode, int index) {
        if (index != 0) {
            Node<T> currentNode = getNodeByIndex(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

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
}
