package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size == 0) {
            head = new Node<>(null, value, null);
        }
        if (size == 1) {
            tail = new Node<>(head, value, null);
            head.next = tail;
        }
        if (size > 1) {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index can't be negative or more than size. Index: "
                    + index + " size: " + size);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> onNode = getNodeTail(index);
            size++;
            Node<T> addNode;
            if (onNode.prev == null) {
                addNode = new Node<>(null, value, onNode);
            } else {
                addNode = new Node<>(onNode.prev, value, onNode);
                onNode.prev.next = addNode;
            }
            onNode.prev = addNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T part : list) {
            add(part);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNodeTail(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T changedNode = getNode(index).item;
        getNode(index).item = value;
        return changedNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeNode = head;
        for (int i = 0; i < index; i++) {
            removeNode = removeNode.next;
        }
        T removeItem = removeNode.item;
        unlink(removeNode);
        return removeItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        for (int i = 0; i < size; i++) {
            if (removeNode.item == null && object == null) {
                unlink(removeNode);
                return true;
            }
            if (removeNode.item != null && removeNode.item.equals(object)) {
                unlink(removeNode);
                return true;
            }
            removeNode = removeNode.next;
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index can't be negative, equals size or "
                    + "more than size. Index: " + index + "; size: " + size);
        }
    }

    private void unlink(Node<T> removeNode) {
        final Node<T> next = removeNode.next;
        final Node<T> prev = removeNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removeNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            removeNode.next = null;
        }
        removeNode.item = null;
        size--;
    }

    private Node<T> getNode(int index) {
        Node<T> lookNode = head;
        for (int i = 0; i < index; i++) {
            lookNode = lookNode.next;
        }
        return lookNode;
    }

    private Node<T> getNodeTail(int index) {
        Node<T> findNode = tail;
        for (int i = size - 1; i > index; i--) {
            findNode = findNode.prev;
        }
        return findNode;
    }
}
