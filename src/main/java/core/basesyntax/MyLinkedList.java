package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
        firstNode = new Node<>(null, null, null);
        lastNode = new Node<>(null, null, null);
        firstNode.next = lastNode;
        lastNode.prev = firstNode;
        size = 0;
    }

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
        validateIndex(index, 1);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, firstNode);
            firstNode.prev = newNode;
            firstNode = newNode;
        } else if (index == size - 1) {
            Node<T> newNode = new Node<>(lastNode.prev, value, lastNode);
            lastNode.prev.next = newNode;
            lastNode.prev = newNode;
        } else {
            Node<T> current = findCurrentNode(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, 0);
        Node<T> current = findCurrentNode(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, 0);
        Node<T> current = findCurrentNode(index);
        T removedItem = current.item;
        current.item = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, 0);
        T item;
        if (index == 0) {
            item = firstNode.item;
            if (size == 1) {
                firstNode = null;
                lastNode = null;
            } else {
                firstNode = firstNode.next;
                firstNode.prev = null;
            }
        } else if (index == size - 1) {
            item = lastNode.item;
            lastNode = lastNode.prev;
            lastNode.next = null;
        } else {
            Node<T> current = findCurrentNode(index);
            item = current.item;
            unlink(current);
        }
        size--;
        return item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        while (current != null) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                if (current == firstNode) {
                    remove(0);
                } else if (current == lastNode) {
                    remove(size - 1);
                } else {
                    unlink(current);
                    size--;
                }
                return true;
            }
            current = current.next;
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

    private void unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void validateIndex(int index, int i) {
        if (index >= size + i || index < 0) {
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
}
