package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexExclusive(index);
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else if (index == size) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else {
            Node<T> currentNode = getNode(index);
            node.next = currentNode;
            node.prev = currentNode.prev;
            currentNode.prev.next = node;
            currentNode.prev = node;
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInclusive(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInclusive(index);
        Node<T> node = getNode(index);
        removeNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.item == object
                    || node.item != null && node.item.equals(object)) {
                return removeNode(node);
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

    private void checkIndexInclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndexInclusive(index);
        Node<T> currentNode;
        if (index <= size() / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size() - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T removeFirst() {
        if (head == null) {
            return null;
        }
        final Node<T> removed = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return removed.item;
    }

    private T removeLast() {
        if (tail == null) {
            return null;
        }
        final Node<T> removed = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        }
        size--;
        return removed.item;
    }

    private boolean removeNode(Node<T> node) {
        if (node == head) {
            return removeFirst() != null;
        } else if (node == tail) {
            return removeLast() != null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            return true;
        }
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
