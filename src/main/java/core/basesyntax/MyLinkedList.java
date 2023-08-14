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
            Node<T> beforeNode = head;
            for (int i = 0; i < index - 1; i++) {
                beforeNode = beforeNode.next;
            }
            Node<T> nextNode = tail;
            for (int i = size - 1; i > index; i--) {
                nextNode = nextNode.prev;
            }
            size++;
            Node<T> addNode = new Node<>(beforeNode, value, nextNode);
            beforeNode.next = addNode;
            nextNode.prev = addNode;
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
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        for (int i = 0; i < index; i++) {
            head = head.next;
        }
        get(index);
        Node<T> setNode;
        if (head.prev == null && head.next != null) {
            setNode = new Node<>(null, value, head.next);
            head.next.prev = setNode;
        }
        if (head.prev != null && head.next == null) {
            setNode = new Node<>(head.prev, value, null);
            head.prev.next = setNode;
        }
        if (head.prev != null && head.next != null) {
            setNode = new Node<>(head.prev, value, head.next);
            head.prev.next = setNode;
            head.next.prev = setNode;
        }
        return head.item;
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
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (removeNode.item == null) {
                    unlink(removeNode);
                    return true;
                }
                removeNode = removeNode.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (removeNode.item != null && removeNode.item.equals(object)) {
                    unlink(removeNode);
                    return true;
                }
                removeNode = removeNode.next;
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
}
