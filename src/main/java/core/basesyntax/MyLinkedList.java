package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            firstNode = node;
        } else {
            lastNode.next = node;
            node.prev = lastNode;
        }
        lastNode = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexOutOfBoundCheck(index);
        if (size == 0 || index == size) {
            add(value);
        } else {
            Node<T> node = getNodeByIndex(index);
            Node<T> newNode = new Node<>(null, value, null);
            if (index == 0) {
                newNode.next = node;
                node.prev = newNode;
                firstNode = newNode;
            } else {
                newNode.prev = node.prev;
                newNode.next = node;
                node.prev.next = newNode;
                node.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            System.out.println(value.toString());
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        unlink(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = firstNode;
        for (int i = 0; i < size; i++) {
            if (isItemEquals(node.item, object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            firstNode = null;
        } else {
            if (node.equals(firstNode)) {
                node.next.prev = null;
                firstNode = node.next;
            } else if (node.equals(lastNode)) {
                node.prev.next = null;
                lastNode = node.prev;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        indexCheck(index);
        if (index == 0) {
            return firstNode;
        }
        if (index == size - 1) {
            return lastNode;
        }
        return (index <= size >> 1) ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> node = firstNode;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> node = lastNode;
        for (int i = index; i < size - 1; i++) {
            node = node.prev;
        }
        return node;
    }

    private boolean isItemEquals(T firstItem, T secondItem) {
        return (firstItem == secondItem) || (firstItem != null && firstItem.equals(secondItem));
    }

    private void indexOutOfBoundCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
