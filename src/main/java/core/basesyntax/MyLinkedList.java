package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(lastNode, value, null);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, firstNode);
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
            return;
        }
        Node<T> afterNode = getNode(index);
        Node<T> beforeNode = afterNode.prev;
        Node<T> newNode = new Node<>(beforeNode, value, afterNode);
        afterNode.prev = newNode;
        if (beforeNode != null) {
            beforeNode.next = newNode;
        } else {
            firstNode = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> operatedNode = getNode(index);
        T oldValue = operatedNode.item;
        operatedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNode(index);
        T value = removed.item;
        unlink(removed);
        return value;

    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node == null) {
            return false;
        }
        unlink(node);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> toSearch;
        if (index < size / 2) {
            toSearch = firstNode;
            for (int i = 0; i < index; i++) {
                toSearch = toSearch.next;
            }
        } else {
            toSearch = lastNode;
            for (int i = size - 1; i > index; i--) {
                toSearch = toSearch.prev;
            }
        }
        return toSearch;
    }

    private Node<T> getNode(T value) {
        Node<T> node = firstNode;
        for (int i = 0; i < size; i++) {
            if ((node.item == value) || (node.item != null && node.item.equals(value))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (next == null) {
            if (prev != null) {
                prev.next = null;
            }
            lastNode = prev;
        } else if (prev == null) {
            next.prev = null;
            firstNode = next;
        } else {
            next.prev = prev;
            prev.next = next;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Can't operate negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " ot of bounds of size " + size);
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
