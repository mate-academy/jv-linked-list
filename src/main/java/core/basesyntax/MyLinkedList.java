package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size()) {
            linkTail(value);
        } else if (index == 0) {
            linkHead(value);
        } else {
            insert(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            linkTail(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> checked = head; checked != null; checked = checked.next) {
            if (isElementsEquals(checked.item, object)) {
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
        return size() == 0;
    }

    private void linkHead(T value) {
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

    private void linkTail(T value) {
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

    private void insert(T value, int index) {
        Node<T> next = findNode(index);
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        prev.next = newNode;
        next.prev = newNode;
        size++;
    }

    private T unlink(Node<T> node) {
        final T deletedItem = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
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

    private Node<T> findNode(int index) {
        Node<T> checked;
        if (index <= size() >> 1) {
            checked = head;
            for (int i = 0; i < index; i++) {
                checked = checked.next;
            }
        } else {
            checked = tail;
            for (int i = size() - 1; i > index; i--) {
                checked = checked.prev;
            }
        }
        return checked;
    }

    private boolean isElementsEquals(Object item1, Object item2) {
        return item1 == item2 || item1 != null && item1.equals(item2);
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
