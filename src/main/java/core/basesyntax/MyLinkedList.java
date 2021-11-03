package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        if (size == 0) {
            addIfZeroSize(value);
        } else {
            addToTail(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexIfAdd(index);
        if (size == 0) {
            addIfZeroSize(value);
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        } else if (index == size) {
            addToTail(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> node = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
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
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T tempValue = currentNode.item;
        currentNode.item = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        unlinkNode(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (equalityCheck(currentNode, object)) {
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
        return (size == 0);
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkIndexIfAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev == null && node.next == null) {
            tail = null;
            head = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private boolean equalityCheck(Node<T> node, T object) {
        return (node.item == object
                || object != null && node.item.equals(object));
    }

    private void addIfZeroSize(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = node;
        tail = node;
    }

    private void addToTail(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
    }
}
