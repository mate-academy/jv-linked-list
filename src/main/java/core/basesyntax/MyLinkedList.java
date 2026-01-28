package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int HALF_DIVISOR = 2;

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
            return;
        }
        linkBefore(value, node(index));
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T value : list) {
            linkLast(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> n = node(index);
        T old = n.item;
        n.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        if (object == null) {
            while (current != null) {
                if (current.item == null) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        while (current != null) {
            if (object.equals(current.item)) {
                unlink(current);
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

    private void linkLast(T value) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, value, null);
        tail = newNode;

        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> successor) {
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, value, successor);
        successor.prev = newNode;

        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> x) {
        Node<T> next = x.next;
        Node<T> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        T element = x.item;
        x.item = null;
        size--;
        return element;
    }

    private Node<T> node(int index) {
        if (index < size / HALF_DIVISOR) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }

        Node<T> x = tail;
        for (int i = size - 1; i > index; i--) {
            x = x.prev;
        }
        return x;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(getOutOfBoundsMessage(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(getOutOfBoundsMessage(index));
        }
    }

    private String getOutOfBoundsMessage(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private static final class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
