package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OOB = "Index out of bounds: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = node(index);
        T oldValue = targetNode.item;
        targetNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> targetNode = node(index);
        return unlink(targetNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (object == null ? current.item == null : object.equals(current.item)) {
                unlink(current);
                return true;
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

    /* ---------------- private helpers ---------------- */

    private void linkLast(T element) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> successor) {
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, element, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

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

        node.item = null; // help GC
        size--;
        return element;
    }

    private Node<T> node(int index) {
        checkElementIndex(index);
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OOB + index + ", size=" + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_OOB + index + ", size=" + size);
        }
    }

    /* ---------------- inner Node class at end ---------------- */

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
