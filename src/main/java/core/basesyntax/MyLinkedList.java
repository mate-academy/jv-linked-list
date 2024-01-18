package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int FIRST = 0;
    private static final int EMPTY = 0;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
            return;
        }
        checkBoundsIndex(index);
        if (index == FIRST) {
            linkFirst(value);
            return;
        }
        linkMiddle(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkBoundsIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkBoundsIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkBoundsIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T value) {
        Node<T> node = getNode(value);
        if (node != null) {
            unlink(node);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == EMPTY;
    }

    private Node<T> getNode(T value) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (value != null && value.equals(node.value)) {
                return node;
            }
            if (value == null && node.value == value) {
                return node;
            }
        }
        return null;
    }

    private Node<T> getNode(int index) {
        if (!isInMiddle(index)) {
            return ((index == FIRST) ? head : tail);
        }
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void linkFirst(T value) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        size++;
        if (first == null) {
            tail = newNode;
            return;
        }
        first.prev = newNode;
    }

    private void linkLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        size++;
        if (last == null) {
            head = newNode;
            return;
        }
        last.next = newNode;
    }

    private void linkMiddle(T value, int index) {
        Node<T> oldNode = getNode(index);
        oldNode.prev.next = new Node<>(oldNode.prev, value, oldNode);
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
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
        node.value = null;
        size--;
        return element;
    }

    private void checkBoundsIndex(int index) {
        if (index < FIRST || index >= size) {
            throw new IndexOutOfBoundsException("Index '" + index
                    + "' is out of the bounds. Size is " + size);
        }
    }

    private boolean isInMiddle(int index) {
        return index > FIRST && index < size;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
