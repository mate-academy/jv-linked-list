package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> oldTail = tail;
            tail = new Node<>(oldTail, value, null);
            oldTail.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> oldHead = head;
            head = new Node<>(null, value, oldHead);
            oldHead.previous = head;
        } else {
            final Node<T> current = findNode(index);
            Node<T> newNode = new Node<>(current.previous, value, current);
            current.previous.next = newNode;
            current.previous = newNode;
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
        checkIndexBounds(index);
        final Node<T> current = findNode(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexBounds(index);
        final Node<T> current = findNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        Node<T> nodeToRemove = findNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        Node(final Node<T> previous, final T item, final Node<T> next) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private void checkIndexBounds(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
    }

    private Node<T> findNode(final int index) {
        int middleIndex = size / 2;
        Node<T> current;
        if (middleIndex > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private void unlink(final Node<T> nodeToRemove) {
        if (nodeToRemove == head) {
            head = nodeToRemove.next;
            if (head != null) {
                head.previous = null;
            }
        } else {
            nodeToRemove.previous.next = nodeToRemove.next;
        }
        if (nodeToRemove == tail) {
            tail = nodeToRemove.previous;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            nodeToRemove.next.previous = nodeToRemove.previous;
        }
        size--;
    }
}
