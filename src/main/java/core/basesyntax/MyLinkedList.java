package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        checkIndex(index);
        Node<T> requiredNode = getNode(index);
        Node<T> newNode = new Node<>(requiredNode.prev, value, requiredNode);
        requiredNode.prev.next = newNode;
        requiredNode.next = newNode.next.next;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return currentNode.item;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> requiredNode = getNode(index);
        T item = requiredNode.item;
        requiredNode.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> requiredNode = getNode(index);
        unlink(requiredNode);
        return requiredNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item || (object != null && object.equals(currentNode.item))) {
                unlink(currentNode);
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
        return size == 0;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + "for size - " + size);
        }
    }

    private void linkLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkFirst(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        boolean isInFirstHalf = size / 2 >= index;
        Node<T> currentNode = isInFirstHalf ? head : tail;
        int countOfIterations = isInFirstHalf ? index : size - index - 1;

        for (int i = 0; i < countOfIterations; i++) {
            currentNode = isInFirstHalf ? currentNode.next : currentNode.prev;
        }
        return currentNode;
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
