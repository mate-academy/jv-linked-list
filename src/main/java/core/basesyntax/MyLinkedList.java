package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            firstNode = new Node<>(null, value, null);
            lastNode = firstNode;
        } else {
            Node<T> newNode = new Node<>(lastNode, value, null);
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index);
        Node<T> newNode = new Node<>(null, value, firstNode);
        if (index == 0) {
            firstNode.prev = newNode;
            firstNode = newNode;
        } else {
            Node<T> foundNode = findCurrentNode(index);
            newNode.prev = foundNode.prev;
            newNode.next = foundNode;
            foundNode.prev = newNode;
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T values : list) {
            add(values);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return findCurrentNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = findCurrentNode(index);
        T removedItem = current.item;
        current.item = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> current = findCurrentNode(index);
        T removedItem = current.item;
        if (size == 1) {
            firstNode = null;
            lastNode = null;
        } else if (index == 0) {
            firstNode = firstNode.next;
            firstNode.prev = null;
        } else if (index == size - 1) {
            lastNode = lastNode.prev;
            lastNode.next = null;
        } else {
            unlink(current);
        }
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        int i = 0;
        while (i < size) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                remove(i);
                return true;
            }
            current = current.next;
            i++;
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

    private void unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> findCurrentNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = firstNode;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = lastNode;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
