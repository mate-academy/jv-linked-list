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
        checkPositionIndex(index);
        if (index == size()) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            insert(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            linkLast(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = node(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> checked = head; checked != null; checked = checked.next) {
            if (isElementsEqual(checked.item, object)) {
                unlink(checked);
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

    private void linkFirst(T value) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void insert(T value, int index) {
        Node<T> next = node(index);
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        prev.next = newNode;
        next.prev = newNode;
        size++;
    }

    private T unlink(Node<T> node) {
        final T deletedItem = node.item;
        final Node<T> prev = node.prev;
        final Node<T> next = node.next;
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
        return deletedItem;
    }

    private Node<T> node(int index) {
        Node<T> currentNode;
        if (index <= size() >> 1) {
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

    private boolean isElementsEqual(Object item1, Object item2) {
        return item1 == item2 || item1 != null && item1.equals(item2);
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index #" + index + " is invalid.");
        }
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index #" + index + " is invalid.");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
