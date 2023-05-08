package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = first; current != null; current = current.next) {
            if (areEqual(object, current.item)) {
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

    private void linkBefore(T value, Node<T> successor) {
        Node<T> prev = successor.prev;
        Node<T> newNode = new Node<>(prev, value, successor);
        successor.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> target) {
        final T element = target.item;
        final Node<T> prev = target.prev;
        final Node<T> next = target.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            target.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            target.next = null;
        }
        target.item = null;
        size--;
        return element;
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private boolean areEqual(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index + ", Size: " + size);
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index + ", Size: " + size);
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        Node(Node<T> prev, T item) {
            this.prev = prev;
            this.item = item;
            this.next = null;
        }
    }
}
